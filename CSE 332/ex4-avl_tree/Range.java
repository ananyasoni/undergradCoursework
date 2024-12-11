public class Range{
    public double start;
    public double end;
    public String name;

    public Range(double start, double end, String name){
        if(start >= end){
            throw new IllegalArgumentException();
        }
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public boolean equals(Range other){
        return this.start == other.start && this.end == other.end && this.name.equals(other.name);
    }

    public String toString(){
        return name;
    }
}
