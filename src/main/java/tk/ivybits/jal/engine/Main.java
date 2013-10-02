package tk.ivybits.jal.engine;

import tk.ivybits.jal.engine.model.MemoryModel;
import tk.ivybits.jal.engine.model.MemoryModelFactory;
import tk.ivybits.profiler.Profiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Tudor
 * Date: 01/10/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MemoryModel mem = MemoryModelFactory.fromDir(new File("C:/Users/Tudor/Desktop/data"));

        String input;
        System.out.print("Tudor: ");
        while ((input = new BufferedReader(new InputStreamReader(System.in)).readLine()) != null) {
            input = input.toUpperCase();
            if (input.contains("BYE")) {
                System.out.println("Goodbye!");
                break;
            }
            String[] responses = mem.search(input.toUpperCase());
            System.out.print("HAL: ");
            if (responses.length == 0) {
                System.out.println("Sorry, my brain exploded.");
            } else {
                System.out.println(responses[new Random().nextInt(responses.length)]);
            }

            System.out.print("\nTudor: ");
            if (!Profiler.isRunning()) {
                Profiler.start();
            }
        }
        Profiler.stop();
    }
}
