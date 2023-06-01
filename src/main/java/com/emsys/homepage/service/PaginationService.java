package com.emsys.homepage.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    /**
     * 현재 페이지를 기준으로 페이지네이션 바에 표시될 숫자 목록을 반환합니다.
     * @param currentPageNumber 현재 페이지 번호
     * @param totalPages 전체 페이지 수
     * @return 페이지네이션 바에 표시될 숫자 목록
     */
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    /**
     * 페이지네이션 바의 길이를 반환합니다.
     * @return 페이지네이션 바의 길이
     */
    public int currentBarLength() {
        return BAR_LENGTH;
    }
}