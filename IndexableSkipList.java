public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
       Node res = head;
       for (int i=res.height(); i>=0;i--) {
    	   while(res.getNext(i)!=null & res.getNext(i).key() <= val) {
    		   res=res.getNext(i);
    	   }
       }
       return res; 
    }

    @Override
    public int generateHeight() {
        int height = 0;
        double randNum=Math.random();
        randNum = randNum + Double.MIN_VALUE;
        while(randNum>probability) {
        	randNum=Math.random();
            randNum = randNum + Double.MIN_VALUE;
            height++;
        }
        return height;
    }

    public int rank(int val) {
        Node res = head;
        int count=0;
        for (int i=res.height(); i>=0;i--) {
     	   while(res.getNext(i)!=null && res.getNext(i).key() <= val) {
     		   res=res.getNext(i);
     		   count+=res.getCountArr()[i];
     	   }
        }
        //what to do if val don't exist?
        return count; 
    }

    public int select(int index) {
        Node res = head;
        int count=0;
        for (int i=res.height(); i>=0 & count!=index;i--) {
     	   while(res.getNext(i)!=null && res.getNext(i)!=tail && count+(res.getNext(i).getCountArr()[i]) <= index) {
     		   res=res.getNext(i);
     		   count+=res.getCountArr()[i];
     	   }
        }
        return res.key(); 
    }
}
