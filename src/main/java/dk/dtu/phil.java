package dk.dtu;

import java.io.IOException;
import java.net.UnknownHostException;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.Space;

public class phil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        try {
        	int port = 21509;
            String uri = "tcp://localhost:" + port + "/space?conn";
			Space space = new RemoteSpace(uri);
			System.out.println("phil has joined the table");
			space.put(1);
			Object[] t = space.get(new FormalField(Integer.class),new ActualField("phil"));
			Object[] n = space.get(new FormalField(Integer.class),new ActualField("phils"));
			int me =(int) t[0];
			int left=me;
			int right= (me+1)% (int) n[0];
			while(true) {
				
				Object[] ns = space.getp(new FormalField(Integer.class),new ActualField("phils"));
				
				if(ns!=null) {
					right= (me+1)% (int) ns[0];
				}
				 space.get(new ActualField("ticket"));
			
				 space.get(new ActualField("fork"), new ActualField(left));
	                System.out.println("Philosopher " + me + " got left fork");

	                // Wait until the right fork is ready (get the corresponding tuple).
	                space.get(new ActualField("fork"), new ActualField(right));
	                System.out.println("Philosopher " + me + " got right fork");

	                // Lunch time.
	                System.out.println("Philosopher " + me + " is eating...");

	                // Return the forks (put the corresponding tuples).
	                space.put("fork", left);
	                space.put("fork", right);
	                space.put("ticket");
	                System.out.println("Philosopher " + me + " put both forks and a ticket on the table");

				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
