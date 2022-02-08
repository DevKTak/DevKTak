package com.devktak.modules.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {

}
