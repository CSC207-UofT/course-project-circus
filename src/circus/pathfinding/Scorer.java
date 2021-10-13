package circus;

public interface Scorer<T extends  Item> {
    public double computeCost(T from, T to);
}
