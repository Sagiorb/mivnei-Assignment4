import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;


public class MyDataStructure {
    /*
     * You may add any members that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items expected in the DS.
     */
	private HashTable<Integer, Integer> hashT;
	private IndexableSkipList skipL;
	private HashFactory<?> hashFactory;
	


    public MyDataStructure(int N) {
    	int k=(int)(Math.log(N)/Math.log(2));
    	this.hashFactory=new ModularHash();
    	this.hashT=new ProbingHashTable<Integer, Integer>((HashFactory<Integer>) hashFactory, k, 0.5);;
    	this.skipL=new IndexableSkipList(0.33);
    }

    public boolean insert(int value) {
        if(contains(value)) {
        	return false;
        }
    	this.hashT.insert(value, 1);
    	this.skipL.insert(value);
    	return true;
    }

    public boolean delete(int value) {
    	if(!contains(value)) {
        	return false;
        }
    	AbstractSkipList.Node toDel=this.skipL.search(value); 
    	this.hashT.delete(value);
    	this.skipL.delete(toDel);
    	return true;
    }

    public boolean contains(int value) {
    	if(hashT.search(value)!=null) {
        	return true;
        } else {
        	return false;
        }
    }

    public int rank(int value) {
        return this.skipL.rank(value);
    }

    public int select(int index) {
        return this.skipL.select(index);
    }

    public List<Integer> range(int low, int high) {
        if(!contains(low)) {
        	return null;
        }
        List<Integer> res = new LinkedList<>();        
        AbstractSkipList.Node toAdd=this.skipL.search(low); 
        res.add(toAdd.key());
        while(toAdd.key()<=high) {
        	int toAddInt=this.skipL.successor(toAdd);
        	res.add(toAddInt);
        }
        return res;
    }
}
