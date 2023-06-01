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

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    /**
     * 게시글에 대한 댓글들을 조회합니다.
     * @param articleId 게시글 ID
     * @return 댓글 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    /**
     * 댓글을 저장합니다.
     * @param dto 댓글 DTO
     */
    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    /**
     * 댓글을 업데이트합니다.
     * @param dto 댓글 DTO
     */
    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
            if (dto.content() != null) {
                articleComment.setContent(dto.content());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    /**
     * 댓글을 삭제합니다.
     * @param articleCommentId 댓글 ID
     * @param userId 유저 ID
     */
    public void deleteArticleComment(Long articleCommentId, String userId) {
        articleCommentRepository.deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }
}