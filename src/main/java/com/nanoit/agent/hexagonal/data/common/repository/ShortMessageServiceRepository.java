package com.nanoit.agent.hexagonal.data.common.repository;

import com.nanoit.agent.hexagonal.data.common.entity.ShortMessageServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortMessageServiceRepository extends JpaRepository<ShortMessageServiceEntity, String> {

    List<ShortMessageServiceEntity> findAllByStatus(String status);
}
