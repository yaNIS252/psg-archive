package com.yanis.psgarchive.repository;

import com.yanis.psgarchive.entity.Nationalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Long> {
}