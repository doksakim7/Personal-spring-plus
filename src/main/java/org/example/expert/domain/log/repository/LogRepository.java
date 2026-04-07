package org.example.expert.domain.log.repository;

import org.example.expert.domain.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

// ✅ 레벨3 11번 Log 리포지토리 생성
public interface LogRepository extends JpaRepository<Log, Long> {
}
