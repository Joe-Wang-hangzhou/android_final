package com.example.hospital.repository;

import com.example.hospital.entity.WchNotice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhmNoticeRepository extends JpaRepository<WchNotice, Long> {
    List<WchNotice> findAllByOrderByPublishTimeDesc();
}
