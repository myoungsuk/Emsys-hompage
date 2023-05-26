package com.emsys.homepage.domain.constant;

import lombok.Getter;

// Enum (열거형) 클래스로 폼의 상태를 나타냅니다.
public enum FormStatus {
    // CREATE 상태를 나타내는 Enum 상수. 설명과 수정 가능 여부를 가집니다.
    CREATE("저장", false),
    // UPDATE 상태를 나타내는 Enum 상수. 설명과 수정 가능 여부를 가집니다.
    UPDATE("수정", true);

    // Getter를 자동으로 생성해주는 Lombok 어노테이션입니다. 이 어노테이션을 통해 description 필드의 값을 가져오는 메소드를 자동으로 생성합니다.
    @Getter
    private final String description;
    // Getter를 자동으로 생성해주는 Lombok 어노테이션입니다. 이 어노테이션을 통해 update 필드의 값을 가져오는 메소드를 자동으로 생성합니다.
    @Getter
    private final Boolean update;

    // Enum 상수의 생성자입니다. 상수가 생성될 때 설명과 수정 가능 여부를 초기화합니다.
    FormStatus(String description, Boolean update) {
        this.description = description;
        this.update = update;
    }
}
