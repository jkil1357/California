package com.nanoit.agent.hexagonal.data.common.service;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;

import java.util.List;

public interface ShortMessageService {

    List<ShortMessageServiceEntity> findAllByStatusIsWaitAndUpdate();

    void update(ShortMessageServiceEntity shortMessageServiceEntity);
}
