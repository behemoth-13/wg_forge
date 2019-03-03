package by.alexei.afanasyeu.domain;

import java.util.Arrays;
import java.util.List;

public class StatInfo {
    private double tailLengthMean;
    private double tailLengthMedian;
    private List<Integer> tailLengthMode;
    private double whiskersLengthMean;
    private double whiskersLengthMedian;
    private List<Integer> whiskersLengthMode;

    public double getTailLengthMean() {
        return tailLengthMean;
    }

    public void setTailLengthMean(double tailLengthMean) {
        this.tailLengthMean = tailLengthMean;
    }

    public double getTailLengthMedian() {
        return tailLengthMedian;
    }

    public void setTailLengthMedian(double tailLengthMedian) {
        this.tailLengthMedian = tailLengthMedian;
    }

    public List<Integer> getTailLengthMode() {
        return tailLengthMode;
    }

    public void setTailLengthMode(List<Integer> tailLengthMode) {
        this.tailLengthMode = tailLengthMode;
    }

    public double getWhiskersLengthMean() {
        return whiskersLengthMean;
    }

    public void setWhiskersLengthMean(double whiskersLengthMean) {
        this.whiskersLengthMean = whiskersLengthMean;
    }

    public double getWhiskersLengthMedian() {
        return whiskersLengthMedian;
    }

    public void setWhiskersLengthMedian(double whiskersLengthMedian) {
        this.whiskersLengthMedian = whiskersLengthMedian;
    }

    public List<Integer> getWhiskersLengthMode() {
        return whiskersLengthMode;
    }

    public void setWhiskersLengthMode(List<Integer> whiskersLengthMode) {
        this.whiskersLengthMode = whiskersLengthMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatInfo statInfo = (StatInfo) o;

        if (Double.compare(statInfo.tailLengthMean, tailLengthMean) != 0) return false;
        if (Double.compare(statInfo.tailLengthMedian, tailLengthMedian) != 0) return false;
        if (Double.compare(statInfo.whiskersLengthMean, whiskersLengthMean) != 0) return false;
        if (Double.compare(statInfo.whiskersLengthMedian, whiskersLengthMedian) != 0) return false;
        if (tailLengthMode != null ? !tailLengthMode.equals(statInfo.tailLengthMode) : statInfo.tailLengthMode != null)
            return false;
        return whiskersLengthMode != null ? whiskersLengthMode.equals(statInfo.whiskersLengthMode) : statInfo.whiskersLengthMode == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(tailLengthMean);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(tailLengthMedian);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (tailLengthMode != null ? tailLengthMode.hashCode() : 0);
        temp = Double.doubleToLongBits(whiskersLengthMean);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(whiskersLengthMedian);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (whiskersLengthMode != null ? whiskersLengthMode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatInfo{" +
                "tailLengthMean=" + tailLengthMean +
                ", tailLengthMedian=" + tailLengthMedian +
                ", tailLengthMode=" + tailLengthMode +
                ", whiskersLengthMean=" + whiskersLengthMean +
                ", whiskersLengthMedian=" + whiskersLengthMedian +
                ", whiskersLengthMode=" + whiskersLengthMode +
                '}';
    }
}
