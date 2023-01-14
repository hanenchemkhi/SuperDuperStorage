package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);
    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
    Credential getCredentialByUrl(String url);
    @Select("SELECT * FROM CREDENTIALS WHERE userid =#{userId}")
    List<Credential> findAllCredentials(Integer userId);
    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) " +
            "VALUES(#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET username = #{username}, key = #{key}, password =#{password} " +
            "WHERE url = #{url}")
    void updateCredential(String url,String username, String key, String password);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);
}

