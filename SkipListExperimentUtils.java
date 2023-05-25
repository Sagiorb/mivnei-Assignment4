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
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
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
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        
        System.out.println("");
        x=10;
        p=0.5;
        System.out.println("p=0.5");
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + arr[i]-(1/p);//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
        System.out.println("p=0.75");
        x=10;
        p=0.75;
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + arr[i]-(1/p);//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
        x=10;
        p=0.9;
        System.out.println("p=0.9");
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;//measure the level for each x in specific p
	        	System.out.println("ℓ"+ i + "=" + arr[i]);
	        	delta=delta + arr[i]-(1/p);//adding all the ℓ for calculating the delta in the end of each X
	        }
	        delta=0.2*delta;
	        System.out.println("the delta is: " + delta);
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
        
    }
}
