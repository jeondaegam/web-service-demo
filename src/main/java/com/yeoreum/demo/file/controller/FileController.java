package com.yeoreum.demo.file.controller;

import com.yeoreum.demo.file.domain.FileMeta;
import com.yeoreum.demo.file.service.FileMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class FileController {

    private final FileMetaService fileMetaService;

    @Autowired
    public FileController(FileMetaService fileMetaService) {
        this.fileMetaService = fileMetaService;
    }

    @GetMapping("/files/new")
    public String uploadFileForm() {
        return "files/uploadFileForm";
    }


    @PostMapping("/files/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileMetaService.save(file); // input 태그의 name 속성과 매칭됨, id는 controller와 전혀 상관 없음
        return "redirect:/";
    }

    @GetMapping("files/all")
    public String allFiles(Model model) {
        List<FileMeta> allFiles = fileMetaService.findFiles();
        model.addAttribute("files", allFiles);
        return "files/allFiles";
    }

}
