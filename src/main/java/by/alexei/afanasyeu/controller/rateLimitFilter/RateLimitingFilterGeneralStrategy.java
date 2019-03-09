package by.alexei.afanasyeu.controller.rateLimitFilter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

@Provider
public class RateLimitingFilterGeneralStrategy implements ContainerRequestFilter {
    private static final AtomicLong count = new AtomicLong(0);
    private static int requestCount = 0;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        if (count.incrementAndGet() > requestCount) {
            ctx.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).build());
        }
    }

    public static void setLimitParams(int requestCount, long interval) {
        RateLimitingFilterGeneralStrategy.requestCount = requestCount;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count.set(0L);
            }
        }, interval, interval);
    }
}
