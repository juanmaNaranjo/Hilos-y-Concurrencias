package com.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        int[] data = new int[1024 * 1024 * 128];

        for (int i = 0; i < data.length; i++) {
            data[i] = ThreadLocalRandom.current().nextInt();
        }
        ForkJoinPool pool = new ForkJoinPool();
        RandomArrayAction action = new RandomArrayAction(data, 0, data.length -1, data.length/16);
        pool.invoke(action);
        FindMaxTask task = new FindMaxTask(data,0,data.length -1,data.length/16);
        Integer result = pool.invoke(task);
        System.out.printf("Max value found: %,d %n", result);
        
    }
}
