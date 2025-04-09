package com.gfg.thread.notes2_challenges;

import java.util.Arrays;
import java.util.Random;

public class Challenge14 {

    public static void main(String[] args) {
//        int[] num = {1,2,3,6,60,72};
//        int number = Arrays.stream(num).parallel().filter(n -> n%6==0).findFirst().orElse(-1);
//        System.out.println(number);

        //Is parallelStreams always faster than sequential streams ?

        //HINT : Find the first even number in an array of one million items .
       // int[] arr={1,2,3,4,5,6,78,90,44,33,45,67,23,95,12,456,90,7,0,7,8,9,144,67,88,44};

        // Generate an array of one million random integers
        int[] arr = new Random().ints(1_000_000, 0, 100_000).toArray();

        // Measure time for parallel stream
        long startTimeParallel = System.nanoTime();
        int firstEvenParallel=Arrays.stream(arr).parallel().filter(x->x%2==0).findFirst().orElse(-1);
        long endTimeParallel = System.nanoTime();
        long durationParallel = endTimeParallel - startTimeParallel;

        // Print results for parallel stream
        System.out.println("First even number (parallel): " + firstEvenParallel);
        System.out.println("Time taken (parallel): " + durationParallel + " nanoseconds");

        // Measure time for sequential stream
        long startTimeSequential = System.nanoTime();
        int firstEvenSequential = Arrays.stream(arr)
                .filter(x -> x % 2 == 0)
                .findFirst()
                .orElse(-1);
        long endTimeSequential = System.nanoTime();
        long durationSequential = endTimeSequential - startTimeSequential;

        // Print results for sequential stream
        System.out.println("First even number (sequential): " + firstEvenSequential);
        System.out.println("Time taken (sequential): " + durationSequential + " nanoseconds");

    }

}
