package dk.dtu;

import java.util.Arrays;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

class mergeSort {
	
	public static void main(String[] args) {
		
		int splitter = 2;
		int mergers =2;
		
		Space space = new SequentialSpace();
		
		int[] arr = {9,4,8,7,1,3};
		
		
		try {
			space.put("sort",arr,arr.length);
			space.put("lock");
			
			//splitters
			
			for(int i =0;i<splitter;i++) {
				new Thread(new Splitter(space,i)).start();
			}
			
			for(int i =0;i<mergers;i++) {
				new Thread(new Merger(space,i)).start();
			}
			
			Object[] results = space.query(new ActualField("result"), new FormalField(Object.class));
			int[] result = (int[])results[1];
	        System.out.println("RESULT: " + Arrays.toString(result));

			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Splitter implements Runnable{
	private Space space;
	int no;
	
	public Splitter(Space space, int no) {
		this.space=space;
		this.no=no;
	}
	@Override
	public void run() {
		
		while(true) {
			try {
				Object[] part= space.get(new ActualField("sort"),new FormalField(Object.class),new FormalField(Integer.class));
				
				int[] arr = (int[]) part[1];
				int arrLength = (int) part[2];
				
				System.out.println("Splitter "+this.no+" got" +Arrays.toString(arr)+" ...");
				if(arr.length==0) {
					System.out.println("WTF");
				}
				if(arr.length==1) {
					space.put("sorted",arr,arrLength);
				}
				else {
					int len = arr.length;
                    int[] a = Arrays.copyOfRange(arr, 0, len/2);
                    int[] b = Arrays.copyOfRange(arr, (len/2), len);
                    space.put("sort", a, arrLength);
                    space.put("sort", b, arrLength);

				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}

class Merger implements Runnable{
	private Space space;
	int no;
	
	public Merger(Space space, int no) {
		this.space=space;
		this.no=no;
	}
	@Override
	public void run() {
		try {
			Object lock = space.get(new ActualField("lock"));
			Object[] part = space.get(new ActualField("sorted"),new FormalField(Object.class),new FormalField(Integer.class));
			
			
			
			int[] arr = (int[]) part[1];
			int arrLength = (int) part[2];
			
			  System.out.println("Merger " + no + " got as a" + Arrays.toString(arr) + "...");
			  
			if(arr.length==arrLength) {
				System.out.println("done");
				space.put("result",arr);
				space.put("lock");
			}
			else {
				Object[] part2 = space.get(new ActualField("sorted"), new FormalField(Object.class),new FormalField(Integer.class));
				int[] b = (int[]) part2[1];
				System.out.println("Merger " + no + " got as b" + Arrays.toString(b) + "...");
				int[] c = merge(arr,b);
				space.put("sorted",c, arrLength);
				space.put("lock");
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i, j, k;
        i = j = k = 0;

        for (;;) {
            if (i == a.length) {
                for (; j < b.length; j ++) {
                    c[k] = b[j];
                    k ++;
                }
                break;
            }
            if (j == b.length) {
                for (; i < a.length; i ++) {
                    c[k] = a[i];
                    k++;
                }
                break;
            }
            if (a[i] <= b[j]) {
                c[k] = a[i];
                i ++;
            } else {
                c[k] = b[j];
                j ++;
            }
            k ++;
        }

        return c;
    }

	
}