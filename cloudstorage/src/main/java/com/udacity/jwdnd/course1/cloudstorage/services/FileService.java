package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;

import org.springframework.core.io.Resource;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public boolean isFileExist(String fileName) {
        return fileMapper.getFile(fileName) != null;
    }

    public int createFile(MultipartFile multipartFile, Integer userId) throws IOException, SQLException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String contentType = multipartFile.getContentType();
        String fileSize = Long.toString(multipartFile.getSize());
        byte[] fileData = multipartFile.getBytes();

        return fileMapper.insert(new File(null, fileName, contentType, fileSize, userId, fileData));
    }

    public List<String> findAllFiles(Integer userId) {
        return fileMapper.findAllFiles(userId);
    }

    public File getFile(String fileName) {
        return fileMapper.getFile(fileName);
    }
    public byte[] getFileData(String fileName)  {
        return getFile(fileName).getFileData();
    }
    public String getContentType(String fileName){
        return getFile(fileName).getContentType();
    }

    public Long getContentLength(String fileName){
        return Long.parseLong(getFile(fileName).getFileSize());
    }

    public void deleteFile(String fileName){
        fileMapper.deleteFile(fileName);
    }


}

