package com.roc.blog.server.grpc.demo.echo;

import com.roc.blog.server.grpc.demo.proto.EchoGrpc;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoRequest;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class BlockingClient {
    private static final Logger log = Logger.getLogger(BlockingClient.class.getName());
    private final EchoGrpc.EchoBlockingStub blockingStub;

    public BlockingClient(Channel channel) {
        blockingStub = EchoGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Client start");
        String serverAddress = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        try {
            BlockingClient client = new BlockingClient(channel);
            System.out.println("========= blocking unaryEcho test ===========");
            client.blockingUnaryEcho("blocking unaryEcho test");

            System.out.println("========= blocking Server streaming test ===========");
            client.blockingServerStreamingEcho("blocking Server streaming test");


        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public void blockingUnaryEcho(String message) {
        EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        log.info("Sending to server: " + request);
        EchoResponse response;
        try {
            response = blockingStub.unaryEcho(request);
            log.info("Got following from the server: " + response.getMessage());
        } catch (StatusRuntimeException e) {
            log.warning("RPC failed: " + e.getStatus());
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
}