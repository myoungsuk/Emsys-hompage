package com.emsys.homepage.controller;

import com.emsys.homepage.dto.UserAccountDto;
import com.emsys.homepage.dto.request.ArticleCommentRequest;
import com.emsys.homepage.dto.security.BoardPrincipal;
import com.emsys.homepage.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor // 필수 인자를 가지는 생성자를 자동으로 생성해주는 어노테이션입니다.
@RequestMapping("/comments") // 컨트롤러의 기본 경로를 설정합니다.
@Controller // 스프링 MVC에서 컨트롤러 역할을 하는 클래스임을 나타내는 어노테이션입니다.
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService; // ArticleCommentService 객체를 주입받습니다.

    @PostMapping("/new") // POST 방식의 "/comments/new" 경로에 대한 요청을 처리하는 메서드입니다.
    public String postNewArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal, // 현재 사용자의 정보를 주입받습니다.
            ArticleCommentRequest articleCommentRequest // ArticleCommentRequest 객체를 주입받습니다.
    ) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto())); // articleCommentService를 사용하여 게시글 댓글을 저장합니다.

        return "redirect:/articles/" + articleCommentRequest.articleId(); // 작업 완료 후 해당 게시글로 리다이렉트합니다.
    }

    @PostMapping("/{commentId}/delete") // POST 방식의 "/comments/{commentId}/delete" 경로에 대한 요청을 처리하는 메서드입니다.
    public String deleteArticleComment(
            @PathVariable Long commentId, // 경로 변수인 commentId를 주입받습니다.
            @AuthenticationPrincipal BoardPrincipal boardPrincipal, // 현재 사용자의 정보를 주입받습니다.
            Long articleId // articleId를 주입받습니다.
    ) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername()); // articleCommentService를 사용하여 게시글 댓글을 삭제합니다.

        return "redirect:/articles/" + articleId; // 작업 완료 후 해당 게시글로 리다이렉트합니다.
    }
}