package dk.dtu;


import org.jspace.*;

import java.util.Arrays;

public class Host {
    public static void main(String[] args) {
        int port = 21509;
        String uri = "tcp://localhost:" + port + "/?conn";

        SpaceRepository repository = new SpaceRepository();
        repository.addGate(uri);
        Space space = new SequentialSpace();
        repository.add("space", space);



        try {

            // We place the array to be sorted in the tuple space
            int[] arr = new int[]{7, 6, 5, 4, 3, 2, 1};
            space.put("sort", arr, arr.length);

            // We add a lock for coordinating the merger workers
            space.put("lock");


            // Here we wait for our result
            Object[] results = space.query(new ActualField("result"), new FormalField(Object.class));
            int[] result = (int[])results[1];
            System.out.println("RESULT: " + Arrays.toString(result));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
