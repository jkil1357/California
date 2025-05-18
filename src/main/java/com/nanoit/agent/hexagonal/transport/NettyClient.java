package com.nanoit.agent.hexagonal.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public void send(String host, int port, byte[] data) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            System.out.println("1 Netty 부트스트랩 시작");

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            System.out.println("2 채널 초기화됨");
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                    System.out.println("서버로부터 응답 수신");
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    System.err.println(" 예외 발생: " + cause.getMessage());
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    });

            System.out.println("3 서버 연결 시도");
            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("4 서버 연결 성공");

            ByteBuf buffer = Unpooled.wrappedBuffer(data);
            future.channel().writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
            System.out.println("5.데이터 전송 및 채널 닫기 요청");

            future.channel().closeFuture().sync();
            System.out.println("6.채널 종료 완료");

        } catch (Exception e) {
            System.err.println("전체 예외 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("7.이벤트 루프 종료");
            group.shutdownGracefully();
        }
    }}
