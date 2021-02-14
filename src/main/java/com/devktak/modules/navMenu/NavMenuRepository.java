package com.devktak.modules.navMenu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface NavMenuRepository extends JpaRepository<BodyLog, Long> {
}
