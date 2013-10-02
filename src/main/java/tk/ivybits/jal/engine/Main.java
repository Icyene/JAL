package tk.ivybits.jal.engine;

import tk.ivybits.jal.engine.model.MemoryModel;
import tk.ivybits.jal.engine.model.MemoryModelFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        String user = System.getProperty("user.name");
        MemoryModel mem = MemoryModelFactory.fromDir(new File("C:/Users/Tudor/Desktop/data"));

        String input;
        System.out.printf("%s: ", user);
        while ((input = new BufferedReader(new InputStreamReader(System.in)).readLine()) != null) {
            input = input.toUpperCase();
            if (input.contains("BYE")) {
                System.out.println("Goodbye!");
                break;
            }
            String[] responses = mem.search(input.toUpperCase());
            System.out.print("JAL: ");
            if (responses.length == 0) {
                System.out.println("Sorry, my brain exploded.");
            } else {
                System.out.println(responses[new Random().nextInt(responses.length)]);
            }

            System.out.printf("\n%s: ", user);
        }
    }
}
