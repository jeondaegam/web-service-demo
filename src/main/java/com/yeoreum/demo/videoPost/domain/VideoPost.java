package com.yeoreum.demo.videoPost.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class VideoPost {

    // key
    Long id;

    // 게시글 제목
    String title;

    // 유튜브 영상 ID
    String videoId;

    // 썸네일 이미지 파일 ID
    Long thumbnailFileId;

    // 작성 내용
    String content;

    // 생성일시
    Date createdAt;

    // 수정일시
    Date updatedAt;

}
