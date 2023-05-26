package com.emsys.homepage.domain.type;

import lombok.Getter;

// 검색 유형을 나타내는 Enum 클래스입니다.
public enum SearchType {
    // 'TITLE' 검색 유형을 나타내는 Enum 상수입니다. 검색 유형의 설명을 나타냅니다.
    TITLE("제목"),
    // 'CONTENT' 검색 유형을 나타내는 Enum 상수입니다. 검색 유형의 설명을 나타냅니다.
    CONTENT("본문"),
    // 'ID' 검색 유형을 나타내는 Enum 상수입니다. 검색 유형의 설명을 나타냅니다.
    ID("유저 ID"),
    // 'NICKNAME' 검색 유형을 나타내는 Enum 상수입니다. 검색 유형의 설명을 나타냅니다.
    NICKNAME("닉네임"),
    // 'HASHTAG' 검색 유형을 나타내는 Enum 상수입니다. 검색 유형의 설명을 나타냅니다.
    HASHTAG("해시태그");

    // Getter를 자동으로 생성해주는 Lombok 어노테이션입니다. 이 어노테이션을 통해 description 필드의 값을 가져오는 메소드를 자동으로 생성합니다.
    @Getter
    private final String description;

    // Enum 상수의 생성자입니다. 상수가 생성될 때 검색 유형의 설명을 초기화합니다.
    SearchType(String description) {
        this.description = description;
    }
}