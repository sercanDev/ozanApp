package com.example.ozanapp.repository;

import com.example.ozanapp.entity.Convert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConvertRepository extends JpaRepository<Convert, UUID> {
    Optional<Page<Convert>> findByTransactionIdOrTransactionDate(UUID transactionId, Date transactionDate, Pageable pageable);
}
