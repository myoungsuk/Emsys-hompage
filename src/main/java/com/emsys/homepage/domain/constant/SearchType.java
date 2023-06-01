package com.emsys.homepage.domain.constant;

import lombok.Getter;

public enum SearchType {
    TITLE("제목"), // 제목으로 검색하는 상수입니다. "제목"이라는 설명을 가지고 있습니다.
    CONTENT("본문"), // 본문으로 검색하는 상수입니다. "본문"이라는 설명을 가지고 있습니다.
    ID("유저 ID"), // 유저 ID로 검색하는 상수입니다. "유저 ID"라는 설명을 가지고 있습니다.
    NICKNAME("닉네임"), // 닉네임으로 검색하는 상수입니다. "닉네임"이라는 설명을 가지고 있습니다.
    HASHTAG("해시태그"); // 해시태그로 검색하는 상수입니다. "해시태그"라는 설명을 가지고 있습니다.

    @Getter
    private final String description; // 상태에 대한 설명을 담는 변수입니다.

    SearchType(String description) {
        this.description = description;
    }
}
