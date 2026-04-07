package org.example.expert.domain.log.service;


import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// ✅ 레벨3 11번 Log 서비 생성
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    // REQUIRES_NEW 옵션 사용으로 매니저 등록과 로그 기록이 각각 독립적으로 처리될 수 있게 설정  → 매니저 등록 실패해도 로그는 반드시 저장
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(String content) {
        logRepository.save(new Log(content));
    }
}
