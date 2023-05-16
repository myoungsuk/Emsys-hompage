package com.emsys.homepage.dto;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.UserAccount;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(

        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy

) implements Serializable {

    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String hashtag, String content, String title) {
        return new ArticleDto(title, content, hashtag, createdAt, createdBy);
    }

}
