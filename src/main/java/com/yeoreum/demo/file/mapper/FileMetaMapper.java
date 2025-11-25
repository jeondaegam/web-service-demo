package com.yeoreum.demo.file.mapper;

import com.yeoreum.demo.file.domain.FileMeta;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMetaMapper {

    void save(FileMeta fileMeta);
    FileMeta findById(Long id);
    List<FileMeta> findAll();
}
