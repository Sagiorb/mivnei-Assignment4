public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
        Node curr
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
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public int select(int index) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }
}
