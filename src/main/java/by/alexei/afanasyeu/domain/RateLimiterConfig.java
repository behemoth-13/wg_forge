package by.alexei.afanasyeu.domain;

public class RateLimiterConfig{
    private int requests;
    private long interval;
    private String rateLimiterType;

    public RateLimiterConfig(int requests, long interval, String rateLimiterType) {
        this.requests = requests;
        this.interval = interval;
        this.rateLimiterType = rateLimiterType;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getRateLimiterType() {
        return rateLimiterType;
    }

    public void setRateLimiterType(String rateLimiterType) {
        this.rateLimiterType = rateLimiterType;
    }
}
