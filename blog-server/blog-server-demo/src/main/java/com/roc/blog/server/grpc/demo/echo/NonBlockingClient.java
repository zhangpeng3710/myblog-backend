package com.roc.blog.server.grpc.demo.echo;

import com.roc.blog.server.grpc.demo.proto.EchoGrpc;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoRequest;
import com.roc.blog.server.grpc.demo.proto.EchoOuterClass.EchoResponse;
import io.grpc.*;
import io.grpc.Context.CancellableContext;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class NonBlockingClient {
    private static final Logger log = Logger.getLogger(NonBlockingClient.class.getName());
    private final EchoGrpc.EchoStub stub;
    static boolean isCompleted = false;
    private CancellableContext withCancellation;
    StreamObserver<EchoRequest> streamClientSender;

    public NonBlockingClient(Channel channel) {
        stub = EchoGrpc.newStub(channel);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Client start");
        String serverAddress = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        try {
            NonBlockingClient client = new NonBlockingClient(channel);


            System.out.println("========= Non Blocking Unary Echo test ===========");
            client.nonBlockingUnaryEcho("Non Blocking Unary Echo test");

            System.out.println("========= Non Blocking Server Stream test ===========");
            client.nonBlockingServerStreamingEcho("Non Blocking Server Stream test");

            System.out.println("========= Client streaming test ===========");
            client.clientStreaming();

            System.out.println("========= Non Blocking Server Bidirectional Streaming test ===========");
            client.bidirectionalStreaming();

            //waiting for server last response，then shutdown channel
            while (!isCompleted) {
                Thread.sleep(500);
            }

        } catch (Exception e) {
            log.warning("RPC failed: " + e);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    private void clientStreaming() {
        String message;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Type message ....");
            message = scanner.nextLine();

            if (message.equals("over")) {
                streamClientSender.onCompleted();
                streamClientSender = null;
                break;
            }

            log.info("Sending to server: " + message);
            EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();

            if (streamClientSender == null) {
                streamClientSender = stub.withDeadlineAfter(2, TimeUnit.SECONDS).clientStreamingEcho(getResponseStreamObserver());
            }

            try {
                streamClientSender.onNext(request);
            } catch (StatusRuntimeException e) {
                log.warning("RPC failed: " + e.getStatus());
            }
        }
    }

    private void bidirectionalStreaming() {
        String message;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Type message ....");
            message = scanner.nextLine();

            if (message.equals("over")) {
                streamClientSender.onCompleted();
                streamClientSender = null;
                break;
            }
            if(message.equals("cancel")) {
                withCancellation.cancel(new Throwable("Cancelled by user"));
                withCancellation = null;
                break;
            }

            log.info("Sending to server: " + message);
            EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();

            if (streamClientSender == null) {
                withCancellation = Context.current().withCancellation();
                streamClientSender = stub.bidirectionalStreamingEcho(getResponseStreamObserver());
            }

            try {
                streamClientSender.onNext(request);
            } catch (StatusRuntimeException e) {
                log.warning("RPC failed: " + e.getStatus());
            }
        }
    }

    private StreamObserver<EchoResponse> getResponseStreamObserver() {
        return new StreamObserver<>() {
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
    }


    public void nonBlockingUnaryEcho(String message) {
        EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        log.info("Sending to server: " + request);
        try {
            stub.unaryEcho(request, getResponseStreamObserver());
        } catch (StatusRuntimeException e) {
            log.warning("RPC failed: " + e.getStatus());
        }
    }

    public void nonBlockingServerStreamingEcho(String message) {
        EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        log.info("Sending to server: " + request);

        stub.serverStreamingEcho(request, getResponseStreamObserver());
    }


}