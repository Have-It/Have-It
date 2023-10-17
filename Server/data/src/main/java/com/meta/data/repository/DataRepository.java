package com.meta.data.repository;

import com.meta.data.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
    Optional<Data> findById(Long memberId);
    List<Data> findAllByOrderByTotalSuccessDesc();
}
