package com.devktak.modules.navMenu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface NavMenuRepositoryExtension {

    Page<BodyLog> findByKeyword(String keyword, Pageable pageable);
}
