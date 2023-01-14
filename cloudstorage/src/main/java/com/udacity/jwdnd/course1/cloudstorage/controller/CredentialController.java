package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final UserService userService;

    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }
    @PostMapping("/save")
    public String saveChanges (@ModelAttribute Credential credential, Authentication authentication, Model model){
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        String credentialError = null;
        if (credential.getUrl().equals("") || credential.getUsername().equals("") || credential.getPassword().equals("")) {
            credentialError = "Missing input";
        }
        if(credentialError == null){
            if(credentialService.isUrlExist(credential.getUrl())) {
                credentialService.updateCredential(credential, userId);
            }else{
                credentialService.addCredential(credential, userId);
            }
            model.addAttribute("success", "true");
        }else{
            model.addAttribute("error", credentialError);
        }
        return "result";
    }

    @GetMapping("/delete/{credentialid:.+}")
    public String deleteCredential(@PathVariable Integer credentialid, Model model) {
        String deleteError = null;
        if (!credentialService.isCredentialExist(credentialid)) {
            deleteError = "Credential does not exist";
        }
        if (deleteError == null) {
            credentialService.deleteCredential(credentialid);
            model.addAttribute("success", "true");
        } else {
            model.addAttribute("error", deleteError);
        }
        return "result";
    }



}
