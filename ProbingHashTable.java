import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private int count;
    private Pair<K,V>[] arrH;


    /*
     * You should add additional private members as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
	public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.count=0;
        this.arrH=(Pair<K, V>[]) new Pair[capacity]; // Unchecked cast
    }

    public V search(K key) {
    	int toSearch=hashFunc.hash(key);
    	V res=null;
    	boolean found=false;
    	for(int i=0;i<this.capacity && !found;i++) {
    		if(arrH[toSearch].first() == key) {
    			res=arrH[toSearch].second();
    			found=true;
    			toSearch=HashingUtils.mod(toSearch+1, this.capacity);
    		}
    	}
    	return res;
        
    }

    public void insert(K key, V value) {
        if (this.count/this.capacity >= 1) {
        	reHash();
        }
        Pair<K,V> p=new Pair<>(key, value);
        int toInsert=hashFunc.hash(key);
        boolean found=false;
        int i=0;
        while(i<this.capacity && !found) {
        	if(arrH[toInsert] == null) {
        		arrH[toInsert]= p;
        		found=true;
        		count++;
        	} else {
        		i++;
        		toInsert=HashingUtils.mod(toInsert+1, this.capacity);
        	}
        }
    }

    public boolean delete(K key) {
    	int toDelete=hashFunc.hash(key);
    	boolean found=false;
    	for(int i=0;i<this.capacity && !found;i++) {
    		if(arrH[toDelete]==null) {
    			found=true; // Cell is empty, key not found
    		} else if (arrH[toDelete].first()==key){
    			arrH[toDelete]=new Pair<>(null,arrH[toDelete].second());// Mark the cell as deleted 
    			found=true;
    			count--;
    		} else {
    			toDelete=HashingUtils.mod(toDelete+1, this.capacity);
    		}
    	}
    	return found;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    
    @SuppressWarnings("unchecked")
	private void reHash() {
    	Pair<K, V>[] oldArr=this.arrH;
    	this.arrH = (Pair<K, V>[]) new Pair[capacity*2]; 
    	this.capacity=this.capacity*2;
    	for(int i=0;i<this.capacity;i++) {
    		if(oldArr[i]==null || oldArr[i].first()==null)
    			insert(oldArr[i].first(), oldArr[i].second());
    	}
    }
    
}
