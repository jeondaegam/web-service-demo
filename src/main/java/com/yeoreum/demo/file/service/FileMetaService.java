package com.yeoreum.demo.file.service;

import com.yeoreum.demo.file.domain.FileMeta;
import com.yeoreum.demo.file.mapper.FileMetaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FileMetaService {

    @Autowired
    FileMetaMapper fileMetaMapper;

    public Long save(MultipartFile file) throws IOException {

        // 0. UUID 생성(서버 저장용 파일명)
        String savedFileName = UUID.randomUUID().toString();
        System.out.println("savedFileName:: " + savedFileName);

        // 1. FileMeta 생성
        FileMeta fileMeta = new FileMeta();
        fileMeta.setSavedName(savedFileName);
        fileMeta.setOriginalName(file.getOriginalFilename());
        fileMeta.setContentType(file.getContentType());
        fileMeta.setSize(file.getSize());
        fileMeta.setCreatedAt(new Date());


        // 3. 저장 경로 설정(스토리지 경로)
        String uploadDir = "/Users/yeoreum/Projects/file-storage";
        String subDir = fileMeta.getSavedName().substring(0, 2);
//        Path targetDirPath = Paths.get(uploadDir, fileMeta.getSavedName().substring(0, 2)); // uuid 앞 두자리
        Path targetDirPath = Paths.get(uploadDir).resolve(subDir);

        // 하위 폴더 분산 (uuid 앞 2자리로 하위폴더 생성)
//        File targetDir = new File(uploadDir + File.separator + targetDirPath);
        File targetDir = targetDirPath.toFile();

        // 4. 디렉토리 생성
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new IOException("디렉토리 생성 실패: " + targetDirPath);
            }
        }


        // 5. 실제 파일 저장
        File savedFile = new File(targetDir, savedFileName);
        file.transferTo(savedFile);
//        log.info("파일 저장 성공::" + savedFile.getAbsolutePath());
        System.out.println("savedFile: " + savedFile.getAbsolutePath());

        String fileName = fileMeta.getOriginalName() + "." + fileMeta.getContentType();
        System.out.println("fileName: " + fileName);

        // 5. DB 저장
        fileMetaMapper.save(fileMeta);

        return fileMeta.getId();


        // 디렉토리 생성 (세밀한조정, 성공/실패내역 로그 남기고싶을 때 사용)
//        if (!directory.exists() && directory.mkdir()) {
//            log.info("디렉토리 생성 성공:: " + uploadPath);
//        } else if (!directory.exists()) {
//            log.info("디렉토리 생성 실패:: " + uploadPath);
//        } else {
//            log.info("디렉토리가 이미 존재합니다:: " + uploadPath);
//        }


    }


    /**
     * 전체파일 조회
     *
     * @return
     */
    public List<FileMeta> findFiles() {
        return fileMetaMapper.findAll();
    }


    public Optional<FileMeta> findOne(Long id) {
        FileMeta fileMeta = fileMetaMapper.findById(id);
        return Optional.ofNullable(fileMeta);
    }


}
