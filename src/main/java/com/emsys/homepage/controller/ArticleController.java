// 패키지를 선언하여 이 파일이 어느 디렉토리에 위치해 있는지 정의합니다.
package com.emsys.homepage.controller;

// 필요한 라이브러리와 클래스들을 임포트합니다.
import com.emsys.homepage.domain.constant.FormStatus;
import com.emsys.homepage.dto.UserAccountDto;
import com.emsys.homepage.dto.request.ArticleRequest;
import com.emsys.homepage.dto.response.ArticleWithCommentsResponse;
import com.emsys.homepage.service.ArticleService;
import com.emsys.homepage.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.emsys.homepage.domain.type.SearchType;
import com.emsys.homepage.dto.response.ArticleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import java.util.List;

// 필수 생성자를 자동으로 만드는 Lombok 어노테이션입니다.
@RequiredArgsConstructor

// 이 클래스가 처리할 요청 URL의 기본 경로를 지정하는 어노테이션입니다.
@RequestMapping("/articles")

// 이 클래스가 MVC 패턴의 컨트롤러 역할을 하는 클래스임을 나타내는 어노테이션입니다.
@Controller
public class ArticleController {

    // 빈으로 등록된 ArticleService와 PaginationService를 자동으로 주입받습니다.
    private final ArticleService articleService;
    private final PaginationService paginationService;

    // 기본 경로에 대한 GET 요청을 처리하는 메소드입니다.
    @GetMapping
    public String articles(
            // 필요한 요청 파라미터를 받아옵니다.
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        // 주입받은 articleService를 통해 해당 검색조건에 맞는 게시글들을 페이징하여 가져옵니다.
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        // 페이지 번호를 가져와서 리스트에 담습니다.
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        // ModelMap에 정보들을 담아 view에 전달합니다.
        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        // 뷰의 이름을 반환하여 뷰 리졸버가 해당 뷰를 찾을 수 있도록 합니다.
        return "articles/index";
    }

    // 기본 경로에 articleId를 붙인 경로에 대한 GET 요청을 처리하는 메소드입니다.
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        // articleService를 이용하여 articleId에 해당하는 게시글을 가져옵니다.
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));

        // ModelMap에 정보들을 담아 view에 전달합니다.
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());
        map.addAttribute("totalCount", articleService.getArticleCount());

        // 뷰의 이름을 반환하여 뷰 리졸버가 해당 뷰를 찾을 수 있도록 합니다.
        return "articles/detail";
    }

    // '/search-hashtag' 경로에 대한 GET 요청을 처리하는 메소드입니다.
    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        // articleService를 이용하여 해시태그를 검색하여 해당하는 게시글을 가져옵니다.
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        // 페이지 번호를 가져와서 리스트에 담습니다.
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        // 사용 가능한 모든 해시태그를 가져옵니다.
        List<String> hashtags = articleService.getHashtags();

        // ModelMap에 정보들을 담아 view에 전달합니다.
        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchType", SearchType.HASHTAG);

        // 뷰의 이름을 반환하여 뷰 리졸버가 해당 뷰를 찾을 수 있도록 합니다.
        return "articles/search-hashtag";
    }

    // '/form' 경로에 대한 GET 요청을 처리하는 메소드입니다.
    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        // 폼의 상태를 생성 상태로 설정합니다.
        map.addAttribute("formStatus", FormStatus.CREATE);

        // 뷰의 이름을 반환하여 뷰 리졸버가 해당 뷰를 찾을 수 있도록 합니다.
        return "articles/form";
    }

    // '/form' 경로에 대한 POST 요청을 처리하는 메소드입니다.
    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        // articleService를 통해 새로운 게시글을 저장합니다.
        articleService.saveArticle(articleRequest.toDto(UserAccountDto.of(
                "nana", "asdf1234", "nana@mail.com", "nana", "memo"
        )));

        // 게시글 목록 페이지로 리다이렉트합니다.
        return "redirect:/articles";
    }

    // 기본 경로에 articleId와 '/form'을 붙인 경로에 대한 GET 요청을 처리하는 메소드입니다.
    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        // articleService를 통해 articleId에 해당하는 게시글을 가져옵니다.
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        // ModelMap에 정보를 담아 view에 전달합니다.
        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        // 뷰의 이름을 반환하여 뷰 리졸버가 해당 뷰를 찾을 수 있도록 합니다.
        return "articles/form";
    }

    // 기본 경로에 articleId와 '/form'을 붙인 경로에 대한 POST 요청을 처리하는 메소드입니다.
    @PostMapping ("/{articleId}/form")
    public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        // articleService를 통해 해당 게시글을 업데이트합니다.
        articleService.updateArticle(articleId, articleRequest.toDto(UserAccountDto.of(
                "nana", "asdf1234", "nana@mail.com", "nana", "memo"
        )));

        // 해당 게시글의 상세 페이지로 리다이렉트합니다.
        return "redirect:/articles/" + articleId;
    }

    // 기본 경로에 articleId와 '/delete'을 붙인 경로에 대한 POST 요청을 처리하는 메소드입니다.
    @PostMapping ("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId) {
        // TODO: 인증 정보를 넣어줘야 한다.
        // articleService를 통해 해당 게시글을 삭제합니다.
        articleService.deleteArticle(articleId);

        // 게시글 목록 페이지로 리다이렉트합니다.
        return "redirect:/articles";
    }

}
