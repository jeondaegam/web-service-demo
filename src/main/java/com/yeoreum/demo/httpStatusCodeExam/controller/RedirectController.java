package com.yeoreum.demo.httpStatusCodeExam.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;


@RequestMapping("/http-status")
@Controller
public class RedirectController {

    // builder 패턴을 사용한 리다이렉트 응답 방법
    @GetMapping("/old-event")
    public ResponseEntity<Void> oldEventUri() {

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY) // 상태코드
                .location(URI.create("/http-status/new-event")) // Location 헤더 필드
                .build();
    }

    // HttpHeaders 객체를 사용한 리다이렉트 응답 방법
    @GetMapping("/old-event-origin")
    public ResponseEntity<Void> oldEventOriginUri() {
        // 1. Location URI 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/http-status/new-event"));
        // 2. 상태코드 설정
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }


    @GetMapping("/new-event")
    public String newEventUri() {
        return "pages/new-event";
    }

}
