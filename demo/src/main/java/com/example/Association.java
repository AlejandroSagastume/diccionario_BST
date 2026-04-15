public class Association<M J> implements Comparable<Association<M, J>> {
    private M key;
    private J value;

    public Association(M key, J value) {
        this.key = key;
        this.value = value;
    }

    public M getKey() {
        return key;
    }

    public J getValue() {
        return value;
    }

    @Override
    public int compareTo(Association<M, J> other) {
        String thisKey = ((String) this.key).toLowerCase();
        String otherKey = ((String) other.key).toLowerCase();
        return thisKey.compareTo(otherKey);
    }
    
    @Override
    public String toString() {
        return "(" + key + ": " + value + ")";
    }
}