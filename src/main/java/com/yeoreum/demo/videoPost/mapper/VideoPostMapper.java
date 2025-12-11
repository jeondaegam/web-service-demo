package com.yeoreum.demo.videoPost.mapper;

import com.yeoreum.demo.videoPost.domain.VideoPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoPostMapper {

    // 전체 목록 조회
    List<VideoPost> findAll();

    // 개별 조회
    VideoPost findById(Long id);

    // 등록
    void save(VideoPost videoPost);

    // 수정
    void update(VideoPost videoPost);

    // 삭제
    void delete(Long id);

}
