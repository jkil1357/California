package com.nanoit.agent.hexagonal.transport;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NettyTestServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9001); // 포트 변경됨
            System.out.println("✅ Netty 테스트 서버 실행 중 (포트 9001)");

            while (true) {
                Socket client = serverSocket.accept();
                InputStream in = client.getInputStream();
                byte[] buffer = new byte[1024];
                int len = in.read(buffer);
                System.out.println("수신 메시지: " + new String(buffer, 0, len, StandardCharsets.UTF_8));
            }

        } catch (Exception e) {
            System.err.println("❌ NettyTestServer 실행 중 에러 발생: " + e.getMessage());
            e.printStackTrace(); // ✅ 반드시 이거 포함
        }
    }
}
