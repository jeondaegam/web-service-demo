package com.yeoreum.demo.videoPost.service;

import com.yeoreum.demo.videoPost.domain.VideoPost;
import com.yeoreum.demo.videoPost.mapper.VideoPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VideoPostService {

    @Autowired
    VideoPostMapper videoPostMapper;


    /*
        - 목록 조회: GET videoPosts
        - 개별 조회 GET videoPosts/{id}
        - 등록 POST videoPosts
        - 수정
        - 삭제
    */

    // 전체 목록 조회
    public List<VideoPost> findAll() {
        return videoPostMapper.findAll();
    }

    // 개별 조회 (id)
    public VideoPost findById(Long id) {
        return videoPostMapper.findById(id);
    }

    // 등록
    public Long save(VideoPost videoPost) {
        videoPostMapper.save(videoPost);
        return videoPost.getId();
    }

    // 수정
    public void update(VideoPost videoPost) {
        videoPostMapper.update(videoPost);
    }

    // 삭제
    public void deleteById(Long id) {
        videoPostMapper.delete(id);
    }

}
