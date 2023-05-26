package com.emsys.homepage.dto.request;

import com.emsys.homepage.dto.ArticleCommentDto;
import com.emsys.homepage.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(articleId,
                userAccountDto,
                content);
    }

}
