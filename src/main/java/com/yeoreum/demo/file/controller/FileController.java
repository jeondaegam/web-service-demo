package com.yeoreum.demo.file.controller;

import com.yeoreum.demo.file.domain.FileMeta;
import com.yeoreum.demo.file.service.FileMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileMetaService fileMetaService;

    @Autowired
    public FileController(FileMetaService fileMetaService) {
        this.fileMetaService = fileMetaService;
    }

    // 업로드 폼
    @GetMapping("/new")
    public String uploadFileForm() {
        return "files/uploadFileForm";
    }

    // 파일 업로드
    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileMetaService.save(file);
        return "redirect:/";
    }

    // 파일 전체 리스트
    @GetMapping
    public String allFiles(Model model) {
//        List<FileMeta> allFiles = fileMetaService.findFiles();
        model.addAttribute("files", fileMetaService.findFiles());
        return "files/allFiles";
    }

    @GetMapping("/{id}")
    public String fileDetail(@PathVariable("id") Long id, Model model) {
        Optional<FileMeta> fileOptional = fileMetaService.findOne(id);
        FileMeta file = fileOptional.orElse(null);
        model.addAttribute("file", file);
        return "files/viewDetail";
    }

}
