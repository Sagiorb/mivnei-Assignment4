import org.w3c.dom.Node;

public class SkipListExperimentUtils {
    public static double measureLevels(double p, int x) {
    	double count=0;
    	IndexableSkipList sl=new IndexableSkipList(p);
    	for(int i=0; i<x; i++) {
    	  count=count+sl.generateHeight();
      }
    	return count/x;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
      public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        IndexableSkipList sl = new IndexableSkipList(p);
        int[] arr = chooseRandInsertions(size);
        double t_s = System.nanoTime();
        for (int i = 0; i < arr.length; i++) {
            sl.insert(arr[i]);
        }
        double t_f = System.nanoTime();
        double diff = t_f - t_s;
        return new Pair<>(sl, diff);
    }

    private static int[] chooseRandInsertions(int size) {
        int[] arr = new int[size];
        int index = 0;
        while (index < size) {
            double ranN = Math.ceil(Math.random() * (2 * size));
            if (ranN % 2 == 0 && !contains(arr, (int) ranN, index)) {
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

    public static double measureSearch(AbstractSkipList skipList, int size) {
    	int[]arr=chooseRandSerach(size*2);
    	double t_s=System.nanoTime();
    	for(int i=0;i<arr.length;i++) {	
    	    skipList.search(arr[i]); 
    	}
    	double t_f=System.nanoTime();
    	double diff=t_f-t_s;
    	return diff;
    }
    
    private static int[] chooseRandSerach(int size) {
        int[] arr = new int[size];
        int index = 0;
        while (index < size) {
            double ranN = Math.ceil(Math.random() * (size));
            if (!contains(arr, (int) ranN, index)) {
                arr[index] = (int) ranN;
                index++;
            }
        }
        return arr;
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
    	int[]arr=chooseRandInsertions(size);
    	AbstractSkipList.Node[] arrDelete=insertRandomDelToArr(arr,skipList);
    	double t_s=System.nanoTime();
    	for(int i=0;i<arr.length;i++) {	
    	    skipList.delete(arrDelete[i]); 
    	}
    	double t_f=System.nanoTime();
    	double diff=t_f-t_s;
    	return diff;
    }
    private static AbstractSkipList.Node[] insertRandomDelToArr(int [] arr, AbstractSkipList skipList) {//creating an array to of the nodes in the skiplist so we can delete it randomly
    	AbstractSkipList.Node[] res=new AbstractSkipList.Node[arr.length];
    	for(int i=0;i<arr.length;i++) {
    		res[i]=skipList.search(arr[i]);
    	}
    	return res;
    }

    public static void main(String[] args) {
        int x=10;
        double p=0.33;
        System.out.println("p=0.33");
        double[] arr=new double[6];
        double delta=0;
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + arr[i]-(1/p);//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        delta=0;
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        
        System.out.println("");
        x=10;
        p=0.5;
        System.out.println("p=0.5");
        delta=0;
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + arr[i]-(1/p);//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        delta=0;
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
        System.out.println("p=0.75");
        x=10;
        p=0.75;
        delta=0;
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + arr[i]-(1/p);//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        delta=0;
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
        x=10;
        p=0.9;
        delta=0;
        System.out.println("p=0.9");
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + (arr[i] - (1/p));//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        delta=0;
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
    
	
    	double[]arrP={0.33, 0.5, 0.75, 0.9};
    	int[]arrX={1000,2500,5000,10000,15000,20000,50000};   	
    	for(int i=0;i<arrP.length;i++) {
    		for(int j=0; j<arrX.length; j++) {
    			double countInsert=0;
    			double countSearch=0;
    			double countDeletion=0;
    			for(int k=0;k<30;k++) {
    				Pair<AbstractSkipList, Double> pair=measureInsertions(arrP[i], arrX[j]);
    				countInsert+=countInsert+pair.second();
    				countSearch+=measureSearch(pair.first(), arrX[i]);
    				countDeletion+=measureDeletions(pair.first(), arrX[j]);
    			}
    			countInsert=countInsert/30;
    			countSearch=countSearch/30;	
    			countDeletion=countDeletion/30;	
    			System.out.println("the average time of insertion with p=" + arrP[i]+ " and x=" +arrX[j]+ " is: " + countInsert);
    			System.out.println("the average time of search with p=" + arrP[i]+ " and x=" +arrX[j]+ " is: " + countSearch);
    			System.out.println("the average time of deletion with p=" + arrP[i]+ " and x=" +arrX[j]+ " is: " + countDeletion);
    		}	
    	}	
    }
}
