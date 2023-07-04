package com.roc.blog.agent;

import com.roc.blog.agent.proto.EchoGrpc;
import com.roc.blog.agent.proto.EchoOuterClass.EchoRequest;
import com.roc.blog.agent.proto.EchoOuterClass.EchoResponse;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class GrpcServer {

    private static final Logger log = Logger.getLogger(GrpcServer.class.getName());

    private io.grpc.Server server;

    public void initGrpcServer() {
        try {
            final GrpcServer server = new GrpcServer();
            server.start();
            server.server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new EchoImpl()).build().start();

        log.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.warning("Shutting down gRPC server");
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
    }

    static class EchoImpl extends EchoGrpc.EchoImplBase {

        @Override
        public void unaryEcho(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
            log.info("Got request from client: " + request);

            MethodCostTime.message = request.getMessage();

            EchoResponse reply = EchoResponse.newBuilder()
                    .setMessage("Server says: ok")
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }


        @Override
        public void serverStreamingEcho(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
            log.info("Got request from client: " + request);
            for (int i = 0; i < 3; i++) {
                EchoResponse reply = EchoResponse.newBuilder()
                        .setMessage("Server says: " + i)
                        .build();
                responseObserver.onNext(reply);
            }
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<EchoRequest> clientStreamingEcho(StreamObserver<EchoResponse> responseObserver) {
            return new StreamObserver<>() {
                int echoTimes = 0;

                @Override
                public void onNext(EchoRequest echoRequest) {
                    //each time what client send to you
                    MethodCostTime.message = echoRequest.getMessage();
                    echoTimes++;
                    log.info(echoRequest.getMessage());
                }

                @Override
                public void onError(Throwable throwable) {
                    log.warning("Error while reading echo stream; " + throwable);
                }

                @Override
                public void onCompleted() {
                    //you can do some work here and response to client before complete the stream
                    EchoResponse reply = EchoResponse.newBuilder()
                            .setMessage("Server says receive echo times: " + echoTimes)
                            .build();
                    responseObserver.onNext(reply);
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<EchoRequest> bidirectionalStreamingEcho(StreamObserver<EchoResponse> responseObserver) {
            return new StreamObserver<>() {
                int echoTimes = 0;

                @Override
                public void onNext(EchoRequest echoRequest) {
                    //each time what client send to you, and do some work response to client
                    echoTimes++;
                    EchoResponse reply = EchoResponse.newBuilder()
                            .setMessage("Server says receive echo times: " + echoTimes)
                            .build();
                    responseObserver.onNext(reply);
                    log.info(echoRequest.getMessage());
                }

                @Override
                public void onError(Throwable throwable) {
                    log.warning("Error while reading echo stream; " + throwable);
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }
}
