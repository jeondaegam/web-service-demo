package com.yeoreum.demo.videoPost.controller;

import com.yeoreum.demo.file.service.FileMetaService;
import com.yeoreum.demo.videoPost.domain.VideoPost;
import com.yeoreum.demo.videoPost.dto.VideoPostRequest;
import com.yeoreum.demo.videoPost.service.VideoPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequestMapping("video-posts")
@Controller
public class VideoPostController {

    private final VideoPostService videoPostService;
    private final FileMetaService fileMetaService;

    @Autowired
    VideoPostController(VideoPostService videoPostService
            , FileMetaService fileMetaService) {
        this.videoPostService = videoPostService;
        this.fileMetaService = fileMetaService;
    }


    // 전체 목록 조회
//    @GetMapping
//    public String list(Model model) {
//        List<VideoPost> list = videoPostService.findAll();
//        model.addAttribute("videoPosts", list);
//        return "videoPosts/list";
//    }
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<VideoPost>> list() {
        List<VideoPost> list = videoPostService.findAll();
        return ResponseEntity.ok(list);
    }

    // 개별 조회
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) { // 이거 브라우저에서 요청 보낼때 자동으로 파싱되나?
        VideoPost videoPost = videoPostService.findById(id);
        model.addAttribute("videoPost", videoPost);
        return "videoPosts/detail";
    }

    // 등록
    @PostMapping
    public String post(@ModelAttribute VideoPostRequest req,
                       @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {

        // 썸네일 이미지 파일 저장
        Long savedFileId = fileMetaService.save(thumbnail);
        Date now = new Date();

        // 게시글 객체 생성
        VideoPost videoPost = VideoPost.builder()
                .title(req.getTitle())
                .videoId(req.getVideoId())
                .thumbnailFileId(savedFileId)
                .content(req.getContent())
                .createdAt(now)
                .updatedAt(now)
                .build();

        // 게시글 저장
        Long id = videoPostService.save(videoPost);

        System.out.println("게시글이 저장 완료 되었습니다. ");
        return "redirect:/video-posts";
    }

    // 수정 (본인만)
    @PostMapping("/{id}")
    public String edit() {
        return "";
    }

    // 삭제 (본인 or 관리자만)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return "";
    }


    @GetMapping("/new")
    public String createForm() {
        return "videoPosts/createForm";
    }

}
