import java.util.Random;

public class StringHash implements HashFactory<String> {

		
		public StringHash() {
	    }

    @Override
    public HashFunctor<String> pickHash(int k) {
    	 long minLong = (long)((Integer.MAX_VALUE/2) + 1);
         long maxLong = Integer.MAX_VALUE + 1; 
         HashingUtils hs= new HashingUtils();
         long q = hs.genLong(minLong, maxLong);
         boolean found=false;
         while (!found) {
         	found=hs.runMillerRabinTest(q, 50);
         	q = hs.genLong(minLong, maxLong);
         }
         int c = (int)hs.genLong(2, q);
         ModularHash mh=new ModularHash();
         return new Functor(c,(int)q, mh.pickHash(k));
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;
        
        public Functor(int c, int q, HashFunctor<Integer> cw) {
        	this.carterWegmanHash = cw;
			this.c=c;
        	this.q=q;
        }

        @Override
        public int hash(String key) {
            long res=0;
            int curr=0;
            int k = key.length();
            for(int i=1;i<=k;i++) {
            	char ch=key.charAt(i-1);
            	curr=HashingUtils.mod(ch*(HashingUtils.mod((int)Math.pow(c, k-i), q)), q);
            	res=res+curr;
            }
            res=HashingUtils.mod(res, q);
            return carterWegmanHash.hash((int)res);
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}
