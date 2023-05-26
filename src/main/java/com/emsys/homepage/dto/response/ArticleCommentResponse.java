package com.emsys.homepage.dto.response;

import com.emsys.homepage.dto.ArticleCommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentResponse(
        // Java 16부터 사용 가능한 record로 ArticleCommentResponse를 선언합니다. 이는 불변 객체로 자동으로 생성자와 Getter 등이 생성됩니다.
        Long id, // 기사 댓글의 id
        String content, // 댓글의 내용
        LocalDateTime createdAt, // 댓글이 작성된 시간
        String email, // 작성자의 이메일
        String nickname, // 작성자의 닉네임
        String userId // 작성자의 사용자 id
) {

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) { // 팩토리 메소드입니다. 이 메소드를 통해 ArticleCommentResponse 객체를 생성할 수 있습니다.
        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId); // ArticleCommentResponse 인스턴스를 생성합니다.
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) { // ArticleCommentDto를 받아 ArticleCommentResponse로 변환하는 메소드입니다.
        String nickname = dto.userAccountDto().nickname(); // 사용자 계정의 닉네임을 가져옵니다.
        if (nickname == null || nickname.isBlank()) { // 닉네임이 null이거나 비어 있으면
            nickname = dto.userAccountDto().userId(); // 사용자 ID를 닉네임으로 사용합니다.
        }

        return new ArticleCommentResponse( // ArticleCommentResponse 인스턴스를 생성합니다.
                dto.id(), // 댓글 id
                dto.content(), // 댓글 내용
                dto.createdAt(), // 댓글 생성 시간
                dto.userAccountDto().email(), // 사용자 이메일
                nickname, // 사용자 닉네임
                dto.userAccountDto().userId() // 사용자 ID
        );
    }

}



