package com.emsys.homepage.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleUpdateDto(

        String title,
        String content,
        String hashtag

) {

    public static ArticleUpdateDto of(String hashtag, String content, String title) {
        return new ArticleUpdateDto(title, content, hashtag);
    }

}
