package com.nanoit.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanoit.agent.hexagonal.transport.dto.NanoitResponse;
import com.nanoit.agent.hexagonal.transport.dto.NanoitSms;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 1. HttpClient 생성
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            // 2. 전송할 JSON 데이터
            String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(NanoitSms.builder()
                    .accountType("M")
                    .id("kyu462755@gmail.com")
                    .apiKey("7a1819fe914ab907c304b3583fc69a69")
                    .msg("테스트")
                    .callNumber("01057069284")
                    .recvData(List.of(NanoitSms.RecvData.builder()
                                    .recvId("1")
                                    .recvNumber("01065158430")
                                    .build()))
                            //NanoitSms.RecvData.builder()
                                    //.recvId("2")
                                    //.recvNumber("01065158430")
                                    //.build()))
                    .build());

            System.out.println(body);

            // 3. HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.funsms.kr/v3/biz/sms"))
                    .header("Content-Type", "application/json;charset=utf-8")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            // 4. 요청 보내고 응답 받기
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. 결과 출력
            System.out.println(response.statusCode());
            System.out.println(response.body());
            if (response.statusCode() == 200) {
                NanoitResponse nanoitResponse = objectMapper.readValue(response.body(), NanoitResponse.class);
                if (nanoitResponse.code() != null && nanoitResponse.code().equals("0")) {
                    System.out.println(nanoitResponse);
                    System.out.println("성공");
                }
                System.out.println("실패");
            } else {
                System.out.println("실패");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
            System.out.println("실패");
        }
    }
}