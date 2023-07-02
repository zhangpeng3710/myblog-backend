package com.roc.blog.server.grpc.demo.blockingStubEcho;

import com.roc.blog.server.grpc.demo.proto.EchoGrpc;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoRequest;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private final EchoGrpc.EchoBlockingStub blockingStub;

    public Client(Channel channel) {
        blockingStub = EchoGrpc.newBlockingStub(channel);
    }
    public void blockingUnaryEcho(String message) {
        EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        log.info("Sending to server: " + request);
        EchoResponse response;
        try {
            response = blockingStub.unaryEcho(request);
            log.info("Got following from the server: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            log.warning("RPC failed: "+ e.getStatus());
        }

    }
    public void blockingServerStreamingEcho(String message) {
        EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        log.info("Sending to server: " + request);

        Iterator<EchoResponse> response = blockingStub.serverStreamingEcho(request);
        while (response.hasNext()) {
            try {
                log.info("Got following from the server: " + response.next().getMessage());
            } catch (StatusRuntimeException e) {
                log.warning("RPC failed: " + e.getStatus());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Client start");
        String serverAddress = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        try {
            Client client = new Client(channel);
            System.out.println("========= unaryEcho test ===========");
            client.blockingUnaryEcho("unaryEcho test");

            System.out.println("========= Server streaming test ===========");
            client.blockingServerStreamingEcho("Server streaming test");


        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}