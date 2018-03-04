package vectorcomparator;

public abstract class Vector{
    double weight;
    
    double transform;
    int rotate;
    
    abstract double CompareTo(Vector vector);
}