package dk.dtu;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import org.jspace.Space;

import java.util.Arrays;

public class SplitterC {
    public static void main(String[] args) {
        try {
            int port = 21509;
            String uri = "tcp://localhost:" + port + "/space?conn";
            Space space = new RemoteSpace(uri);

            while (true) {
                Object[] parts = space.get(new ActualField("sort"), new FormalField(Object.class), new FormalField(Integer.class));
                int[] arr = (int[])parts[1];
                int resultLength = (int)parts[2];
                System.out.println("Splitter got " + Arrays.toString(arr) + "...");

                if (arr.length == 0) { // This should not happen

                    continue;

                } else if (arr.length == 1) {

                    space.put("sorted", arr, resultLength);

                } else {

                    int len = arr.length;
                    int[] a = Arrays.copyOfRange(arr, 0, len/2);
                    int[] b = Arrays.copyOfRange(arr, (len/2), len);
                    space.put("sort", a, resultLength);
                    space.put("sort", b, resultLength);

                }
         }

        } catch (Exception e) {}
    }
}

