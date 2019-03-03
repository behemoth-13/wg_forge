package by.alexei.afanasyeu.controller;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Provider
public class RateLimitingFilter implements ContainerRequestFilter {
    private static int indexLastElement = 0;
    private static int interval = 0;
    private static CircularFifoQueue<Long> queue = null;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        Long last = queue.get(indexLastElement);
        Long now = System.currentTimeMillis();
        if (now - last < interval) {
            ctx.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).build());
        }
    }

    public static void setLimitParams(int requestCount, int timeUnitCount, TimeUnit unit) {
        indexLastElement = requestCount - 1;
        queue = new CircularFifoQueue<>(requestCount);
        for (int i = 0; i < requestCount; i++) {
            queue.add(Long.MAX_VALUE);
        }
        switch(unit) {
            case SECONDS:
                interval = 1000*timeUnitCount;
                break;
            case MINUTES:
                interval = 1000*60*timeUnitCount;
                break;
            case HOURS:
                interval = 1000*60*60*timeUnitCount;
                break;
            case DAYS:
                interval = 1000*60*60*24*timeUnitCount;
                break;
        }
    }
}