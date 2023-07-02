package com.roc.blog.server.grpc.demo.blockingStubEcho;

import com.roc.blog.server.grpc.demo.proto.EchoGrpc;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoRequest;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class NonBlockingClient {
    private static final Logger log = Logger.getLogger(NonBlockingClient.class.getName());
    private final EchoGrpc.EchoStub stub;

    public NonBlockingClient(Channel channel) {
        stub = EchoGrpc.newStub(channel);
    }

    boolean isCompleted = false;

    public void nonBlockingClientStreamingEcho(String message) {

        StreamObserver<EchoResponse> responseStreamObserver = new StreamObserver<EchoResponse>() {
            @Override
            public void onNext(EchoResponse echoResponse) {
                log.info("Server say: " + echoResponse.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                log.warning("Error: " + throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Server say: Finished");
                isCompleted = true;
            }
        };

        StreamObserver<EchoRequest> requestStreamObserver = stub.clientStreamingEcho(responseStreamObserver);
        EchoRequest request;
        for (int i = 0; i < 3; i++) {
            try {
                request = EchoRequest.newBuilder().setMessage("Hi " + i).build();
                requestStreamObserver.onNext(request);
                Thread.sleep(1000);
            } catch (StatusRuntimeException e) {
                log.warning("RPC failed: " + e.getStatus());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        requestStreamObserver.onCompleted();

        //waiting for server response，then shutdown channel
        while (!isCompleted) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            NonBlockingClient client = new NonBlockingClient(channel);
            System.out.println("========= Client streaming test ===========");
            client.nonBlockingClientStreamingEcho("client streaming test");

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}