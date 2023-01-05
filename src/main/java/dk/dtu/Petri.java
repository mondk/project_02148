package dk.dtu;

import org.jspace.ActualField;
import org.jspace.SequentialSpace;
import org.jspace.Space;

public class Petri {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			Space q1 = new SequentialSpace();
			Space q2 = new SequentialSpace();
			Space q3 = new SequentialSpace();
			q1.put("token");
			q2.put("token");
			
			new Thread(new A(q1)).start();
			new Thread(new B(q1,q2,q3)).start();
			new Thread(new C(q2)).start();
			new Thread(new D(q1,q2,q3)).start();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}

class A implements Runnable{
	
	private Space q1;
	
	public A(Space q1) {
		this.q1=q1;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				q1.get(new ActualField("token"));
				System.out.println("A is working...");
				q1.put("token");
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
class B implements Runnable{
	
	private Space q3;
	private Space q2;
	private Space q1;
	
	public B(Space q1, Space q2,Space q3) {
		this.q1=q1;
		this.q2=q2;
		this.q3=q3;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Object[] t =  q2.getp(new ActualField("token"));
				Object[] t2 =  q2.getp(new ActualField("token"));
				
				if(t!=null||t2!=null) {
					if(t!=null&&t2==null) {
						System.out.println("B is working...");
						q3.put("token");
					}
					else if (t2!=null&&t==null) {
						System.out.println("B is working...");
						q3.put("token");
					}
					else {
						System.out.println("B is working...");
						q3.put("token");
						q3.put("token");
					}
				}
				
				
				q1.get(new ActualField("token"));
				System.out.println("B is working...");
				q3.put("token");
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
class C implements Runnable{
	
	private Space q2;
	
	public C(Space q) {
		this.q2=q;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				q2.get(new ActualField("token"));
				System.out.println("C is working...");
				q2.put("token");
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
class D implements Runnable{
	
	private Space q3;
	private Space q2;
	private Space q1;
	
	public D(Space q1,Space q2,Space q3) {
		this.q3=q3;
		this.q2=q2;
		this.q1=q1;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				q3.get(new ActualField("token"));
				System.out.println("D is working...");
				q1.put("token");
				q2.put("token");

				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}