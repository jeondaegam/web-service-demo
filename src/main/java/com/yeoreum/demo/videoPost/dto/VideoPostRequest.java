package com.yeoreum.demo.videoPost.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoPostRequest {

    // 게시글 제목
    String title;

    // 유튜브 영상 ID
    String videoId;

    // 작성 내용
    String content;
}
