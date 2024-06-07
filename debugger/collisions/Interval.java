package debugger.collisions;

public final class Interval {
    public Double min;
    public Double max;
    public Interval(Double min, Double max){
        this.min = min;
        this.max = max;
    }
    public boolean overlap (Interval other) {
        return this.min <= other.max && other.min <= this.max;
    }

    public double clamp(double d){
        if (d < this.min){
            return this.min;
        }

        else if (d > this.max){
            return this.max;
        }

        return d;
    }

}
