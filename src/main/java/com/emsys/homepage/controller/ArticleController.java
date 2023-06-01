package com.emsys.homepage.controller;


import com.emsys.homepage.domain.constant.FormStatus;
import com.emsys.homepage.dto.UserAccountDto;
import com.emsys.homepage.dto.request.ArticleRequest;
import com.emsys.homepage.dto.response.ArticleWithCommentsResponse;
import com.emsys.homepage.dto.security.BoardPrincipal;
import com.emsys.homepage.service.ArticleService;
import com.emsys.homepage.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.emsys.homepage.domain.constant.SearchType;
import com.emsys.homepage.dto.response.ArticleResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;


@RequiredArgsConstructor // 필수 인자를 가지는 생성자를 자동으로 생성해주는 어노테이션입니다.
@RequestMapping("/articles") // 컨트롤러의 기본 경로를 "/articles"로 설정합니다.
@Controller // 스프링 MVC에서 컨트롤러 역할을 하는 클래스임을 나타내는 어노테이션입니다.
public class ArticleController {

    private final ArticleService articleService; // ArticleService 객체를 주입받습니다.
    private final PaginationService paginationService; // PaginationService 객체를 주입받습니다.

    @GetMapping // GET 방식의 "/articles" 경로에 대한 요청을 처리하는 메서드입니다.
    public String articles(
            @RequestParam(required = false) SearchType searchType, // 선택적인 요청 매개변수인 searchType을 받습니다.
            @RequestParam(required = false) String searchValue, // 선택적인 요청 매개변수인 searchValue를 받습니다.
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 페이지 정보를 받습니다. 기본값으로는 10개씩, 생성일 기준으로 내림차순으로 정렬합니다.
            ModelMap map // 모델 정보를 담는 ModelMap 객체를 받습니다.
    ) {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from); // articleService를 사용하여 게시글을 검색하고, ArticleResponse로 변환합니다.
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages()); // 페이지 번호를 기준으로 페이징 바의 번호 리스트를 가져옵니다.

        map.addAttribute("articles", articles); // articles를 모델에 추가합니다.
        map.addAttribute("paginationBarNumbers", barNumbers); // paginationBarNumbers를 모델에 추가합니다.
        map.addAttribute("searchTypes", SearchType.values()); // 검색 타입 목록을 모델에 추가합니다.

        return "articles/index"; // articles/index 템플릿을 렌더링합니다.
    }

    @GetMapping("/{articleId}") // GET 방식의 "/articles/{articleId}" 경로에 대한 요청을 처리하는 메서드입니다.
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId)); // articleService를 사용하여 게시글과 댓글 정보를 가져옵니다.

        map.addAttribute("article", article); // article을 모델에 추가합니다.
        map.addAttribute("articleComments", article.articleCommentsResponse()); // article의 댓글 목록을 모델에 추가합니다.
        map.addAttribute("totalCount", articleService.getArticleCount()); // 게시글 총 개수를 모델에 추가합니다.

        return "articles/detail"; // articles/detail 템플릿을 렌더링합니다.
    }

    @GetMapping("/search-hashtag") // GET 방식의 "/articles/search-hashtag" 경로에 대한 요청을 처리하는 메서드입니다.
    public String searchArticleHashtag(
            @RequestParam(required = false) String searchValue, // 선택적인 요청 매개변수인 searchValue를 받습니다.
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 페이지 정보를 받습니다. 기본값으로는 10개씩, 생성일 기준으로 내림차순으로 정렬합니다.
            ModelMap map // 모델 정보를 담는 ModelMap 객체를 받습니다.
    ) {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from); // articleService를 사용하여 해시태그를 기준으로 게시글을 검색하고, ArticleResponse로 변환합니다.
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages()); // 페이지 번호를 기준으로 페이징 바의 번호 리스트를 가져옵니다.
        List<String> hashtags = articleService.getHashtags(); // 모든 해시태그를 가져옵니다.

        map.addAttribute("articles", articles); // articles를 모델에 추가합니다.
        map.addAttribute("hashtags", hashtags); // hashtags를 모델에 추가합니다.
        map.addAttribute("paginationBarNumbers", barNumbers); // paginationBarNumbers를 모델에 추가합니다.
        map.addAttribute("searchType", SearchType.HASHTAG); // 검색 타입을 모델에 추가합니다.

        return "articles/search-hashtag"; // articles/search-hashtag 템플릿을 렌더링합니다.
    }

    @GetMapping("/form") // GET 방식의 "/articles/form" 경로에 대한 요청을 처리하는 메서드입니다.
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE); // 폼 상태를 생성(Create) 상태로 설정하고 모델에 추가합니다.

        return "articles/form"; // articles/form 템플릿을 렌더링합니다.
    }

    @PostMapping("/form") // POST 방식의 "/articles/form" 경로에 대한 요청을 처리하는 메서드입니다.
    public String postNewArticle(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                                 ArticleRequest articleRequest
    ) {
        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto())); // articleService를 사용하여 게시글을 저장합니다.

        return "redirect:/articles"; // 작업 완료 후 "/articles"로 리다이렉트합니다.
    }

    @GetMapping("/{articleId}/form") // GET 방식의 "/articles/{articleId}/form" 경로에 대한 요청을 처리하는 메서드입니다.
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId)); // articleService를 사용하여 게시글을 가져와 ArticleResponse로 변환합니다.

        map.addAttribute("article", article); // article을 모델에 추가합니다.
        map.addAttribute("formStatus", FormStatus.UPDATE); // 폼 상태를 업데이트(Update) 상태로 설정하고 모델에 추가합니다.

        return "articles/form"; // articles/form 템플릿을 렌더링합니다.
    }

    @PostMapping("/{articleId}/form") // POST 방식의 "/articles/{articleId}/form" 경로에 대한 요청을 처리하는 메서드입니다.
    public String updateArticle(
            @PathVariable Long articleId, // 경로 변수인 articleId를 받습니다.
            @AuthenticationPrincipal BoardPrincipal boardPrincipal, // 현재 사용자의 정보를 주입받습니다.
            ArticleRequest articleRequest // ArticleRequest 객체를 주입받습니다.
    ) {
        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto())); // articleService를 사용하여 게시글을 업데이트합니다.
        return "redirect:/articles/" + articleId; // 작업 완료 후 해당 게시글로 리다이렉트합니다.
    }

    @PostMapping("/{articleId}/delete") // POST 방식의 "/articles/{articleId}/delete" 경로에 대한 요청을 처리하는 메서드입니다.
    public String deleteArticle(
            @PathVariable Long articleId, // 경로 변수인 articleId를 받습니다.
            @AuthenticationPrincipal BoardPrincipal boardPrincipal // 현재 사용자의 정보를 주입받습니다.
    ) {
        articleService.deleteArticle(articleId, boardPrincipal.getUsername()); // articleService를 사용하여 게시글을 삭제합니다.

        return "redirect:/articles"; // 작업 완료 후 "/articles"로 리다이렉트합니다.
    }
}