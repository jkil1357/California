package com.nanoit.agent.hexagonal.adapter.data.service;

import com.nanoit.agent.hexagonal.adapter.data.entity.ShortMessageService;
import com.nanoit.agent.hexagonal.adapter.data.repository.ShortMessageServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortMessageServiceServiceImpl implements ShortMessageServiceService {

    private final ShortMessageServiceRepository shortMessageServiceRepository;

    public ShortMessageServiceServiceImpl(ShortMessageServiceRepository shortMessageServiceRepository) {
        this.shortMessageServiceRepository = shortMessageServiceRepository;
    }

    /**
     * 테이블 내 대기중인 전송 메시지 데이터를 조회하고 상태값을 변경한 후 조회된 데이터를 리턴한다.
     */
    @Override
    public List<ShortMessageService> findAllByStatusIsWaitAndUpdate() {
        return shortMessageServiceRepository.findAll();
    }
}
