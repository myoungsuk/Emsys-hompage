package com.emsys.homepage.domain.constant;

import lombok.Getter;

public enum FormStatus {
    CREATE("저장", false), // 생성(Create) 상태를 나타내는 상수입니다. "저장"이라는 설명과 업데이트 여부를 false로 설정합니다.
    UPDATE("수정", true); // 업데이트(Update) 상태를 나타내는 상수입니다. "수정"이라는 설명과 업데이트 여부를 true로 설정합니다.

    @Getter private final String description; // 상태에 대한 설명을 담는 변수입니다.
    @Getter private final Boolean update; // 업데이트 여부를 나타내는 변수입니다.

    FormStatus(String description, Boolean update) {
        this.description = description;
        this.update = update;
    }
}
