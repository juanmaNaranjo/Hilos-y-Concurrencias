package com.example.client;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.util.concurrent.*;

public class NetworkClientMain {

    public static void main(String[] args) {
        
        /**
        String host = "localhost";
        for (int port = 10000; port < 10010; port++) {
            RequestResponse lookup = new RequestResponse(host, port);
            try (Socket sock = new Socket(lookup.host, lookup.port);
                    Scanner scanner = new Scanner(sock.getInputStream());) {
                lookup.response = scanner.next();
                System.out.println(lookup.host + ":" + lookup.port + " " + lookup.response);
            } catch (NoSuchElementException | IOException ex) {
                System.out.println("Error talking to " + host + ":" + port);
            }
        }
        **/

        ExecutorService executorService = Executors.newCachedThreadPool();
        Map<RequestResponse, Future<RequestResponse>> callables = new HashMap<>();

        String host = "localhost";
        for (int port = 10000; port < 10010; port++) {
            RequestResponse lookup = new RequestResponse(host, port);
            NetworkClientCallable callable = new NetworkClientCallable(lookup);
            Future<RequestResponse> future = executorService.submit(callable);
            callables.put(lookup, future);
        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<RequestResponse, Future<RequestResponse>> entry : callables.entrySet()) {
            RequestResponse lookup = entry.getKey();
            Future<RequestResponse> future = entry.getValue();
            try {
                RequestResponse response = future.get();
                System.out.println(lookup.host + ":" + lookup.port + " " + response.response);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error talking to " + lookup.host + ":" + lookup.port);
                e.printStackTrace();
            }
        }
    }
}
       

