package by.alexei.afanasyeu.domain;

import java.util.List;

public class MeanMedianMode {
    private double mean;
    private double median;
    private List<Integer> mode;

    public MeanMedianMode(double mean, double median, List<Integer> mode) {
        this.mean = mean;
        this.median = median;
        this.mode = mode;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public List<Integer> getMode() {
        return mode;
    }

    public void setMode(List<Integer> mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "MeanMedianMode{" +
                "mean=" + mean +
                ", median=" + median +
                ", mode=" + mode +
                '}';
    }
}
