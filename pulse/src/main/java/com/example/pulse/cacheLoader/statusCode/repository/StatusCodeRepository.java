package com.example.pulse.cacheLoader.statusCode.repository;

import com.example.pulse.cacheLoader.statusCode.entity.StatusCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(path = "statusCodes")
public interface StatusCodeRepository extends JpaRepository<StatusCodeEntity,Integer> {
    Optional<StatusCodeEntity> findByCode(Integer code);
}
