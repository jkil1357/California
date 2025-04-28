package com.nanoit.agent.hexagonal.data.common.service;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import com.nanoit.agent.hexagonal.data.common.repository.ShortMessageServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortMessageServiceImpl implements ShortMessageService {

    private final ShortMessageServiceRepository shortMessageServiceRepository;

    public ShortMessageServiceImpl(ShortMessageServiceRepository shortMessageServiceRepository) {
        this.shortMessageServiceRepository = shortMessageServiceRepository;
    }

    /**
     * 테이블 내 대기중인 전송 메시지 데이터를 조회하고 상태값을 변경한 후 조회된 데이터를 리턴한다.
     */
    @Override
    public List<ShortMessageServiceEntity> findAllByStatusIsWaitAndUpdate() {
        // SELECT
        List<ShortMessageServiceEntity> waitList = shortMessageServiceRepository.findAllByStatus("WAIT");
        // OBJECT 객체 상태 변경
        List<ShortMessageServiceEntity> selected = waitList.stream()
                .peek(entity -> entity.setStatus("SELECTED"))
                .toList();
        // 실제 UPDATE 쿼리 실행
        shortMessageServiceRepository.saveAll(selected);
        // 데이터 RETURN
        return selected;
    }

    @Override
    public void update(ShortMessageServiceEntity shortMessageServiceEntity) {

    }
}
