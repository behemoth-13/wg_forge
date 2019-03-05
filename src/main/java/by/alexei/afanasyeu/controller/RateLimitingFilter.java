package by.alexei.afanasyeu.controller;

import org.apache.commons.collections4.QueueUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Provider
public class RateLimitingFilter implements ContainerRequestFilter {
    private static int interval = 0;
    private static Queue<Long> queue = null;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        Long now = System.currentTimeMillis();
        synchronized (this) {
            Long last = queue.peek();
            if (now - last < interval) {
                ctx.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).build());
            }
        }
    }

    public static void setLimitParams(int requestCount, int timeUnitCount, TimeUnit unit) {
        queue = QueueUtils.synchronizedQueue(new CircularFifoQueue<>(requestCount));
        for (int i = 0; i < requestCount; i++) {
            queue.add(0L);
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