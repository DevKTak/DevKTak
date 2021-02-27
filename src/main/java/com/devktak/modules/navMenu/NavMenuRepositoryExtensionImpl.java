package com.devktak.modules.navMenu;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class NavMenuRepositoryExtensionImpl extends QuerydslRepositorySupport implements NavMenuRepositoryExtension {

    public NavMenuRepositoryExtensionImpl() {
        super(BodyLog.class);
    }

    @Override
    public Page<BodyLog> findByKeyword(String keyword, Pageable pageable) {
        QBodyLog bodyLog = QBodyLog.bodyLog;
        JPQLQuery<BodyLog> query = from(bodyLog)
                .where(bodyLog.title.containsIgnoreCase(keyword));
        JPQLQuery<BodyLog> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<BodyLog> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }
}
