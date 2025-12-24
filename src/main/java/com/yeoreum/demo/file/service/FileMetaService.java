package com.yeoreum.demo.file.service;

import com.yeoreum.demo.file.domain.FileMeta;
import com.yeoreum.demo.file.mapper.FileMetaMapper;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final Tika tika = new Tika();
    @Value("${file.upload-dir}")
    private String uploadDir;

    public Long save(MultipartFile file) throws IOException {

        validateImageFile(file);

        // 1. UUID 생성(서버 저장용 파일명)
        String savedFileName = UUID.randomUUID().toString();
        System.out.println("savedFileName:: " + savedFileName);


        // 2. 저장 경로 설정(스토리지 경로)
        String subDir = savedFileName.substring(0, 2);
        Path targetDirPath = Paths.get(uploadDir).resolve(subDir);

        // 2-1. 하위 폴더 분산 (uuid 앞 2자리로 하위폴더 생성)
        File targetDir = targetDirPath.toFile();


        // 3. 디렉토리 생성
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created) {
                throw new IOException("디렉토리 생성 실패: " + targetDirPath);
            }
        }


        // 4. 실제 파일 저장
        File savedFile = new File(targetDir, savedFileName);
        file.transferTo(savedFile);

        // 5. 저장된 파일 정보 DB 저장
        FileMeta fileMeta = new FileMeta();
        fileMeta.setSavedName(savedFileName);
        fileMeta.setOriginalName(file.getOriginalFilename());
        fileMeta.setContentType(file.getContentType());
        fileMeta.setSize(file.getSize());
        fileMeta.setCreatedAt(new Date());
        fileMeta.setPath(targetDirPath.toString());

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


    private void validateImageFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        String mimeType = tika.detect(file.getInputStream());

        List<String> allowMimeTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");

        if (!allowMimeTypes.contains(mimeType)) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다. (감지된 타입: " + mimeType + ")");
        }
    }


}
