import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    private Random random;
	
	public ModularHash() {
    	this.random = new Random();
        
    }

    @Override
    public HashFunctor<Integer> pickHash(int k) {//implementing carter wegman
        int m=(int)Math.pow(2, k);
        int a= random.nextInt(Integer.MAX_VALUE - 1) + 1;
        int b=random.nextInt(Integer.MAX_VALUE);
        long minLong = (long)(Integer.MAX_VALUE + 1);
        long maxLong = Long.MAX_VALUE;
        HashingUtils hs= new HashingUtils();
        long p = hs.genLong(minLong, maxLong);
        boolean found=false;
        while (!found) {
        	found=hs.runMillerRabinTest(p, 50);
        	p = hs.genLong(minLong, maxLong);
        }
        return new Functor(a, b, p, m);
        
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;
        
        public Functor(int a, int b, long p, int m) {
            this.a = a;
            this.b = b;
            this.p = p;
            this.m = m;
        }
        @Override
        public int hash(Integer key) {
        	return HashingUtils.mod((int)HashingUtils.mod((long)((a * key) + b), p), m);
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
