package com.nanoit.agent.hexagonal.data.common.service;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import com.nanoit.agent.hexagonal.data.common.repository.ShortMessageServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
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
        List<ShortMessageServiceEntity> waitList = shortMessageServiceRepository.findAllByStatus("WAIT");
        List<ShortMessageServiceEntity> selected = waitList.stream()
                .peek(entity -> {
                    entity.setStatus("SELECTED");
                    entity.setModifiedDateTime(LocalDateTime.now());
                })
                .toList();
        return shortMessageServiceRepository.saveAll(selected);
    }

    @Override
    public void update(ShortMessageServiceEntity shortMessageServiceEntity) {
        shortMessageServiceRepository.save(shortMessageServiceEntity);
    }

    @Override
    public ShortMessageServiceEntity create(ShortMessageServiceEntity entity) {
        entity.setStatus("WAIT");
        entity.setCreatedDateTime(LocalDateTime.now());
        entity.setModifiedDateTime(LocalDateTime.now());
        return shortMessageServiceRepository.save(entity);
    }
}
