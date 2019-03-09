package by.alexei.afanasyeu.controller.rateLimitFilter;

import com.revinate.guava.util.concurrent.RateLimiter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class RateLimitingFilterGeneralStrategy implements ContainerRequestFilter {
    private static RateLimiter limiter = null;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        if (!limiter.tryAcquire()) {
            ctx.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).build());
        }
    }

    public static void setLimitParams(int requestCount, long interval) {
        double requestPerSecond = requestCount/(interval/1000.0d);
        limiter = RateLimiter.create(requestPerSecond);
    }
}
