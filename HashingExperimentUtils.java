import java.util.Collections; // can be useful
import java.util.List;
import java.util.ListIterator;

public class HashingExperimentUtils<K> {
    final private static int k = 16;
    private static HashFactory<?> hashFactory;
   // @SuppressWarnings("unchecked")
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
    	hashFactory=new ModularHash();
    	ChainedHashTable<Integer, Integer> cht = new ChainedHashTable<Integer, Integer>((HashFactory<Integer>) hashFactory, k, maxLoadFactor);
    	int size=(int)(cht.capacity() * maxLoadFactor);
    	int[]arr = chooseRandInsertions(size);
    	double t_s_i = System.nanoTime();
    	for(int i=0;i<arr.length;i++) {
    		cht.insert(arr[i],1); //keep 1?
    	}
    	double t_f_i = System.nanoTime();
    	double diffI = t_f_i - t_s_i;
    	int[]arrToSearch=createArrToSearch(size ,arr);
    	double t_s_s = System.nanoTime();
    	for(int i=0;i<arrToSearch.length;i++) {
    		cht.search(arrToSearch[i]);
    	}
    	double t_f_s = System.nanoTime();
    	double diffS = t_f_s - t_s_s;
        return new Pair<>(diffI, diffS);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
    	hashFactory=new ModularHash();
     	ProbingHashTable pht = new ProbingHashTable(hashFactory, k, maxLoadFactor);//the problem is here
    	int size=(int)(pht.capacity() * maxLoadFactor);
    	int[]arr = chooseRandInsertions(size);
    	double t_s_i = System.nanoTime();
    	for(int i=0;i<arr.length;i++) {
    		pht.insert(arr[i], 1); //keep 1?
    	}
    	double t_f_i = System.nanoTime();
    	double diffI = t_f_i - t_s_i;
    	int[]arrToSearch=createArrToSearch(size ,arr);
    	double t_s_s = System.nanoTime();
    	for(int i=0;i<arrToSearch.length;i++) {
    		pht.search(arrToSearch[i]);
    	}
    	double t_f_s = System.nanoTime();
    	double diffS = t_f_s - t_s_s;
        return new Pair<>(diffI, diffS);
    }
    
    private static int[] chooseRandInsertions(int size) {
        int[] arr = new int[size];
        int index = 0;
        while (index < size) {
            double ranN = Math.ceil(Math.random() * (Integer.MAX_VALUE));
            if (!contains(arr, (int) ranN, index)) {
                arr[index] = (int) ranN;
                index++;
            }
        }
        return arr;
    }
    
    private static boolean contains(int[] arr, int num, int endIndex) {
        for (int i = 0; i < endIndex; i++) {
            if (arr[i] == num) {
                return true;
            }
        }
        return false;
    }
    
    private static int[] createArrToSearch(int size, int[]arr) {
    	int[]res=new int[size*2];
    	for(int i=0;i<(int)Math.ceil((double)res.length/2);i++){
    		res[i]=arr[i];
    	}
    	int index=(int)(Math.ceil((double)res.length/2)+1);
        while (index < res.length) {
            double ranN = Math.ceil(Math.random() * (Integer.MAX_VALUE));
            if (!contains(arr, (int) ranN, size)) {
                res[index] = (int) ranN;
                index++;
            }
        }
    	return res;
    }

    public static Pair<Double, Double> measureLongOperations() {
    	hashFactory=new MultiplicativeShiftingHash();
    	ChainedHashTable<Long, Long> cht = new ChainedHashTable<Long, Long>((HashFactory<Long>) hashFactory, k, 1);
    	int size=(int)(cht.capacity());
    	HashingUtils hashingUtils = new HashingUtils();
    	Long [] arr = hashingUtils.genUniqueLong(size);
    	double t_s_i = System.nanoTime();
    	for(int i=0;i<arr.length;i++) {
    		cht.insert(arr[i],(long) 1); //keep 1?
    	}
    	double t_f_i = System.nanoTime();
    	double diffI = t_f_i - t_s_i;
    	Long[]arrToSearch=createArrToSearchLong(size ,arr);
    	double t_s_s = System.nanoTime();
    	for(int i=0;i<arrToSearch.length;i++) {
    		cht.search(arrToSearch[i]);
    	}
    	double t_f_s = System.nanoTime();
    	double diffS = t_f_s - t_s_s;
        return new Pair<>(diffI, diffS);
    }
    	
    

    private static Long[] createArrToSearchLong(int size, Long[] arr) {
        Long[] res = new Long[size * 2];
        HashingUtils hashingUtils = new HashingUtils();
        int index = 0;
        for (int i = 0; i < Math.ceil((double) res.length / 2); i++) {
            res[i] = arr[i];
            index++;
        }
        while (index < res.length) {
            Long genLong = hashingUtils.genLong(Long.MIN_VALUE, Long.MAX_VALUE);
            if (!containsLong(res, genLong, index)) {
                res[index] = genLong;
                index++;
            }
        }
        return res;
    }


    private static boolean containsLong(Long[] arr, Long num, int endIndex) {
        for (int i = 0; i < endIndex; i++) {
            if (arr[i] != null && arr[i].equals(num)) {
                return true;
            }
        }
        return false;
    }


	public static Pair<Double, Double> measureStringOperations() {
		hashFactory=new StringHash();
		ChainedHashTable<String, String> cht = new ChainedHashTable<String, String>((HashFactory<String>) hashFactory, k, 1);
    	int size=(int)(cht.capacity());
    	HashingUtils hashingUtils = new HashingUtils();
    	List<String> list=hashingUtils.genUniqueStrings(size, 10, 20);
    	ListIterator<String> it = list.listIterator();
    	double t_s_i = System.nanoTime();
    	while(it.hasNext()) {
    		cht.insert(it.next(), "ONE");
    	}
    	double t_f_i = System.nanoTime();
    	double diffI = t_f_i - t_s_i;
    	String[]arrToSearch=createArrToSearchString(size ,list);
    	double t_s_s = System.nanoTime();
    	for(int i=0;i<arrToSearch.length;i++) {
    		cht.search(arrToSearch[i]);
    	}
    	double t_f_s = System.nanoTime();
    	double diffS = t_f_s - t_s_s;
        return new Pair<>(diffI, diffS);
    }
	
	private static String[] createArrToSearchString(int size, List<String> list) {
		String []res = new String[size*2];
        HashingUtils hashingUtils = new HashingUtils();
        int index = 0;
        ListIterator<String> it = list.listIterator();
        while(it.hasNext()) {
        	res[index]=it.next();
        	index++;
        }
        while (index < res.length) {
            List<String> genString = hashingUtils.genUniqueStrings(size, 10, 20);
            ListIterator<String> it2 = genString.listIterator();
            while(it2.hasNext()) {
            	String temp=it2.next();
            	if (!containsString(res, temp, index)) {
            		res[index] = temp;
                	index++;
            	}
            }
        }
        return res;
    }


   private static boolean containsString(String[] arr, String st, int endIndex) {
        for (int i = 0; i < endIndex; i++) {
            if (arr[i] != null && arr[i].equals(st)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
    	double[] mlf = {2.0 / 4, 3.0 / 4, 7.0 / 8, 15.0 / 16};
        for(int i=0;i<mlf.length;i++) {
        	double avgInsert=0;
            double avgSearch=0;
        	for(int j=0;j<30;j++) {
        		Pair<Double, Double> res=new Pair(0, 0);
        		res=measureOperationsProbing((double)mlf[i]);
        		avgInsert=avgInsert+res.first();
        		avgSearch=avgSearch+res.second();
        	}
        	System.out.println("The avg insertion time with linear probing with max load factor of "+mlf[i]+" is: "+(avgInsert/30));
        	System.out.println("The avg search time with linear probing with max load factor of "+mlf[i]+" is: "+(avgSearch/30));
        }

    	double[] rlf ={0.5, 0.75, 1.0, 1.5, 2.0};
        for(int i=0;i<rlf.length;i++) {
        	double avgInsert=0;
            double avgSearch=0;
        	for(int j=0;j<30;j++) {
        		Pair<Double, Double> res=new Pair(0, 0);
        		res=measureOperationsChained((double)rlf[i]);
        		avgInsert=avgInsert+res.first();
        		avgSearch=avgSearch+res.second();
        	}
        	System.out.println("The avg insertion time with chained hashing with max load factor of "+rlf[i]+" is: "+(avgInsert/30));
        	System.out.println("The avg search time with chained hashing with max load factor of "+rlf[i]+" is: "+(avgSearch/30));
        } 
        
        double avgInsert1=0;
        double avgSearch1=0;
        for(int i=0;i<10;i++) {
        	Pair<Double, Double> res=new Pair(0, 0);
        	res=measureLongOperations();
        	avgInsert1=avgInsert1+res.first();
        	avgSearch1=avgSearch1+res.second();     
        }
        System.out.println("The avg insertion time of Long to hash table with max load factor of 1 is: "+(avgInsert1/10));
        System.out.println("The avg search time of Long in hash table with max load factor of 1 is: "+(avgSearch1/10));
        
        double avgInsert2=0;
        double avgSearch2=0;
        for(int i=0;i<10;i++) {
        	Pair<Double, Double> res=new Pair(0, 0);
        	res=measureStringOperations();
        	avgInsert2=avgInsert2+res.first();
        	avgSearch2=avgSearch2+res.second();     
        }
        System.out.println("The avg insertion time of Long to hash table with max load factor of 1 is: "+(avgInsert2/10));
        System.out.println("The avg search time of Long in hash table with max load factor of 1 is: "+(avgSearch2/10));
    }
}
