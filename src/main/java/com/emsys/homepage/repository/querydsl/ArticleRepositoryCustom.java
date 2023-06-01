package com.emsys.homepage.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<String> findAllDistinctHashtags();

    //findAllDistinctHashtags() 메소드는 모든 고유한 해시태그를 조회하는 메소드입니다.
    // 이를 통해 Article 엔티티에서 사용된 모든 해시태그를 중복 없이 가져올 수 있습니다.
}