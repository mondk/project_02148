package dk.dtu;

import org.jspace.SequentialSpace;
import org.jspace.*;
import org.jspace.SpaceRepository;

public class table {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  int port = 21509;
	      String uri = "tcp://localhost:" + port + "/?conn";
	
	      SpaceRepository repository = new SpaceRepository();
	      repository.addGate(uri);
	      Space space = new SequentialSpace();
	      repository.add("space", space);
	      int numberOfPhils =0;
	      
	      for(int i =0;i<2;i++) {
	    	  try {
				space.put("fork",i);
				System.out.println("Waiter is adding a fork");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      try {
			space.put("ticket");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      
	      while(true) {
	    	  try {
				Object[] c = space.getp(new FormalField(Integer.class));
				if(c!=null) {
					space.put("fork", numberOfPhils+2);
					space.put("ticket");
					System.out.println("waiter adds fork");
					numberOfPhils++;
					space.put(numberOfPhils,"phil");
					for(int i=0;i<numberOfPhils;i++) {
						space.put(numberOfPhils,"phils");
					}
				}
				
				
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }

	}

}
