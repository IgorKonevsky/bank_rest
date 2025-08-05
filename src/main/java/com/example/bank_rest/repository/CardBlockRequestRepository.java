package com.example.bank_rest.repository;

import com.example.bank_rest.entity.CardBlockRequest;
import com.example.bank_rest.entity.enums.BlockRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardBlockRequestRepository extends JpaRepository<CardBlockRequest, UUID> {
    List<CardBlockRequest> findAllByStatus(BlockRequestStatus status);
}

