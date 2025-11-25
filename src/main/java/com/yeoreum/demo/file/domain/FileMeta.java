package com.yeoreum.demo.file.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FileMeta {

    // key
    private Long id;

    // 스토리지 저장용 파일명 (uuid)
    private String savedName;

    // 원본 파일명
    private String originalName;

    // MIME 타입
    private String contentType;

    // 파일 크기
    private Long size;

    // 저장 경로
    private String path;

    // 파일 업로드 시각
    private Date createdAt;
}