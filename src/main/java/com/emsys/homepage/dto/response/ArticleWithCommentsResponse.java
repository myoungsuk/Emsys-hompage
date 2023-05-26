package com.emsys.homepage.dto.response;

import com.emsys.homepage.dto.ArticleWithCommentsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

// 게시글과 댓글을 함께 처리하는 응답 클래스를 정의한 레코드입니다.
public record ArticleWithCommentsResponse(
        // 각 필드에 대한 주석을 다음과 같이 달 수 있습니다.
        Long id,   // 게시글의 ID
        String title,  // 게시글의 제목
        String content,  // 게시글의 내용
        String hashtag,  // 게시글의 해시태그
        LocalDateTime createdAt,  // 게시글의 생성 시간
        String email,  // 사용자의 이메일
        String nickname,  // 사용자의 닉네임
        Set<ArticleCommentResponse> articleCommentsResponse  // 게시글에 달린 댓글들의 정보
) implements Serializable {  // Serializable 인터페이스를 구현하여 객체를 직렬화할 수 있습니다.

    // 게시글과 댓글 정보를 받아 객체를 생성하는 정적 메서드입니다.
    public static ArticleWithCommentsResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentsResponse(id, title, content, hashtag, createdAt, email, nickname, articleCommentResponses);
    }

    // ArticleWithCommentsDto 객체를 이용해 ArticleWithCommentsResponse 객체를 생성하는 메서드입니다.
    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        // 사용자의 닉네임이 null 이거나 비어있는 경우, 사용자 ID를 닉네임으로 사용합니다.
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        // dto에서 필요한 정보를 추출하여 ArticleWithCommentsResponse 객체를 생성합니다.
        // dto에 저장된 댓글 정보들을 Stream을 이용하여 변환 후 새로운 Set으로 만들어 저장합니다.
        return new ArticleWithCommentsResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.articleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
