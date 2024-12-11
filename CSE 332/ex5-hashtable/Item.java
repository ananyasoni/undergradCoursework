public class Item <K,V> {
    public final K key;
    public V value;

    public Item(K key, V value){
        this.key = key;
        this.value = value;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }
        if(getClass() != other.getClass()){
            return false;
        }
        Item<K,V> otherItem = (Item<K,V>) other;
        return this.key.equals(otherItem.key);
    }

    public String toString(){
        return "(" + key + "," + value + ")";
    }
}
