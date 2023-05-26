package com.emsys.homepage.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service  // 이 클래스가 Spring의 Service 레이어의 클래스임을 나타내는 어노테이션입니다.
public class PaginationService {

    private static final int BAR_LENGTH = 5;  // 페이지네이션 바에 표시될 페이지 번호의 개수를 설정하는 상수입니다.

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        // 현재 페이지 번호와 총 페이지 수를 입력받아, 페이지네이션 바에 표시될 페이지 번호의 목록을 반환합니다.

        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);  // 페이지네이션 바의 시작 번호를 계산합니다.
        // 현재 페이지 번호에서 페이지네이션 바 길이의 절반을 뺀 값이 0보다 작다면 0으로 설정하고, 아니라면 해당 값을 시작 번호로 설정합니다.

        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);  // 페이지네이션 바의 끝 번호를 계산합니다.
        // 시작 번호에 페이지네이션 바 길이를 더한 값이 총 페이지 수보다 크다면 총 페이지 수를 끝 번호로 설정하고, 아니라면 해당 값을 끝 번호로 설정합니다.

        return IntStream.range(startNumber, endNumber).boxed().toList();  // 시작 번호부터 끝 번호까지의 정수 스트림을 생성하고, 이를 리스트로 변환하여 반환합니다.
    }

    public int currentBarLength() {
        // 현재 페이지네이션 바의 길이를 반환하는 메서드입니다.
        return BAR_LENGTH;  // 페이지네이션 바 길이 상수를 반환합니다.
    }
}
