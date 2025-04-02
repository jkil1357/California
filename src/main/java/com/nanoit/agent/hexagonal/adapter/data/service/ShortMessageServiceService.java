package com.nanoit.agent.hexagonal.adapter.data.service;

import com.nanoit.agent.hexagonal.adapter.data.entity.ShortMessageService;

import java.util.List;

public interface ShortMessageServiceService {

    List<ShortMessageService> findAllByStatusIsWaitAndUpdate();
}
