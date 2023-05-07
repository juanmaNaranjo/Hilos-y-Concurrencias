package com.example.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class NetworkClientCallable implements Callable<RequestResponse> {
    RequestResponse lookup;
    public NetworkClientCallable(RequestResponse lookup) {
        this.lookup = lookup;
    }
    @Override
    public RequestResponse call() throws RuntimeException {
        try (Socket socket = new Socket(lookup.host, lookup.port);
             Scanner scanner = new Scanner(socket.getInputStream())) {
            this.lookup.response = scanner.next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.lookup;
    }
}
