        int x=10;
        double p=0.33;
        System.out.println("p=0.33");
        double[] arr=new double[6];
        for(int j=0;j<4;j++) {
        	System.out.println("x="+x);
	        for(int i=1;i<=5;i++) {
	        	arr[i]=measureLevels(p,x)+1;
	        	System.out.println("l"+ i + "=" + arr[i]);
	        }
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
	        	arr[i]=measureLevels(p,x)+1;
	        	System.out.println("l"+ i + "=" + arr[i]);
	        }
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
	        	arr[i]=measureLevels(p,x)+1;
	        	System.out.println("l"+ i + "=" + arr[i]);
	        	}
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
	        	arr[i]=measureLevels(p,x)+1;
	        	System.out.println("l"+ i + "=" + arr[i]);
	        }
	        x*=10;
        }
        System.out.println("E[ℓ] =" + 1/p);
        System.out.println("");
