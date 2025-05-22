package com.nanoit.agent.config;

import com.nanoit.agent.application.ClientAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AdapterConfig {

    @Bean
    public Map<String, ClientAdapter> clientAdapters(List<ClientAdapter> adapters) {
        return adapters.stream()
                .collect(Collectors.toMap(
                        adapter -> adapter.getClass().getAnnotation(Component.class).value().toUpperCase(),
                        Function.identity()));
    }
}
//고객사 이름("KT" 또는 "NANOIT")을 키로 하여 어댑터를 쉽게 찾기 위해 Map으로 구성합니다
