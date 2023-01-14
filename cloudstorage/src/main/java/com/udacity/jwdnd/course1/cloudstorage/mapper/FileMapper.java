package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);
    @Select("SELECT filename FROM FILES WHERE userid =#{userId}")
    List<String> findAllFiles(Integer userId);
    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);
    @Delete("DELETE FROM FILES WHERE fileName = #{fileName}")
    void deleteFile(String fileName);

}
