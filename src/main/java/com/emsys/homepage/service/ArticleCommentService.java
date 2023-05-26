package com.emsys.homepage.service;

import com.emsys.homepage.domain.Article;
import com.emsys.homepage.domain.ArticleComment;
import com.emsys.homepage.domain.UserAccount;
import com.emsys.homepage.dto.ArticleCommentDto;
import com.emsys.homepage.repository.ArticleCommentRepository;
import com.emsys.homepage.repository.ArticleRepository;
import com.emsys.homepage.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j // 이 클래스의 로그를 찍기 위한 어노테이션입니다.
@RequiredArgsConstructor   // final 또는 @NonNull 필드 값만 파라미터로 받는 생성자를 생성해줍니다. 이를 통해 레포지터리를 주입받습니다.
@Transactional            // 이 클래스에 있는 메소드들은 기본적으로 DB 트랜잭션 범위에서 실행됩니다.
@Service                  // 이 클래스는 스프링의 서비스 컴포넌트임을 나타냅니다.
public class ArticleCommentService {

    private final ArticleRepository articleRepository;          // ArticleRepository를 주입받습니다. 이를 통해 게시글 관련 데이터 처리를 할 수 있습니다.
    private final ArticleCommentRepository articleCommentRepository; // ArticleCommentRepository를 주입받습니다. 이를 통해 게시글 댓글 관련 데이터 처리를 할 수 있습니다.
    private final UserAccountRepository userAccountRepository;


    @Transactional(readOnly = true)  // 이 메소드는 읽기 전용 트랜잭션 범위에서 실행됩니다. 즉, 데이터 변경을 할 수 없습니다.
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {  // 특정 게시글의 모든 댓글을 검색합니다.
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    public void saveArticleComment(ArticleCommentDto dto) { // 새로운 게시글 댓글을 저장합니다.
        try{
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 -{}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            if (dto.content() != null) { articleComment.setContent(dto.content()); }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }
    public void deleteArticleComment(Long articleCommentId) {  // 기존의 게시글 댓글을 삭제합니다.
        articleCommentRepository.deleteById(articleCommentId);
    }

}
