package com.devktak.modules.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByUserId(String userId);

    Member findByEmail(String email);

    Member findByUserId(String userIdOrEmail);
}
