package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptedService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptedService;
    }
    public List<Credential> findAllCredentials(Integer userId){return credentialMapper.findAllCredentials(userId);}
    public boolean isUrlExist(String url) {
        return credentialMapper.getCredentialByUrl(url) != null;
    }
    public boolean isCredentialExist(Integer credentialId) {
        return credentialMapper.getCredential(credentialId) != null;
    }

    public Credential getCredential(Integer credentialId){
        return credentialMapper.getCredential(credentialId);
    }

    public void updateCredential(Credential credential, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credentialMapper.updateCredential(credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword);
    }
    public int addCredential(Credential credential, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return credentialMapper.insertCredential(
                new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId));

    }
    public String decryptPassword(Integer credentialId){
        Credential credential =  credentialMapper.getCredential(credentialId);
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.deleteCredential(credentialId);
    }

}
