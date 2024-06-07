package engine.shapes;

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

    public boolean containsNum(double num){
        return this.min <= num && this.max >= num;
    }


    public double clamp(double d){

        if (d < min){
            return min;
        }

        else if (d > max){
            return max;
        }

        return d;
    }

}
