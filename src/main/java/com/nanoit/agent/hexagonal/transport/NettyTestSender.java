package com.nanoit.agent.hexagonal.transport;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NettyTestSender {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("✉️ 전송할 메시지를 입력하세요 (exit 입력 시 종료):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("👋 종료합니다.");
                break;
            }

            byte[] data = input.getBytes(StandardCharsets.UTF_8);
            new NettyClient().send("127.0.0.1", 9001, data);  // ← 포트 꼭 맞춰줘
        }

        scanner.close();
    }
}
