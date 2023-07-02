package com.roc.blog.server.grpc.demo.simple;

import com.roc.blog.server.grpc.demo.proto.GreeterGrpc;
import com.roc.blog.server.grpc.demo.proto.Greeting.ClientInput;
import com.roc.blog.server.grpc.demo.proto.Greeting.ServerOutput;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreetClient {
    private static final Logger logger = Logger.getLogger(GreetClient.class.getName());
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public GreetClient(Channel channel) {
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }
    public void makeGreeting(String greeting, String username) {
        logger.info("Sending greeting to server: " + greeting + " for name: " + username);
        ClientInput request = ClientInput.newBuilder().setName(username).setGreeting(greeting).build();
        logger.info("Sending to server: " + request);
        ServerOutput response;
        try {
            response = blockingStub.greet(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Got following from the server: " + response.getMessage());
    }

    public static void main(String[] args) throws Exception {
        String greeting = "hello";
        String username = "peng";
        String serverAddress = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        try {
            GreetClient client = new GreetClient(channel);
            client.makeGreeting(greeting, username);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}