package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.SQLException;
import org.springframework.http.HttpHeaders;



@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private final FileService fileService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final NoteService noteService;
    @Autowired
    private final CredentialService credentialService;

    @Autowired
    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {

        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }
    @GetMapping()
    public String homeView(Authentication authentication, Model model){

        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.findAllFiles(userId));
        model.addAttribute("notes", noteService.findAllNotes(userId));
        model.addAttribute("credentials", credentialService.findAllCredentials(userId) );
        model.addAttribute("credentialService", credentialService);
        return "home";
    }


    @PostMapping("/upload")
    public String uploadFile(Authentication authentication, Model model, @RequestParam("fileUpload") MultipartFile multipartFile) throws SQLException, IOException {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        String uploadError = null;

        if (fileService.isFileExist(multipartFile.getOriginalFilename())){
            uploadError = "File name already exist!";
        }

        if(uploadError == null){
            if(multipartFile.isEmpty()){
                uploadError ="There was an error uploading the file. Please try again.";
            }
        }

        if(uploadError == null) {
            fileService.createFile(multipartFile, userId);
            model.addAttribute("success", "true");
        }else{
            model.addAttribute("error", uploadError);
        }

        return "result";
    }

    // used these resources :
    //https://stackoverflow.com/questions/33753975/thymeleaf-using-path-variables-to-thhref
    //https://www.baeldung.com/spring-mvc-image-media-data
    //https://www.bezkoder.com/spring-boot-file-upload/
    // https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
    @GetMapping("/view/{filename:.+}")
    @ResponseBody
    public ResponseEntity viewFile(@PathVariable String filename) {

        String contentType = fileService.getContentType(filename);
        long fileLength = fileService.getContentLength(filename);
        byte[] fileData = fileService.getFileData(filename);
        ByteArrayResource byteArrayResource = new ByteArrayResource(fileService.getFileData(filename));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename="+filename )
                .contentType(MediaType.valueOf(contentType))
                .contentLength(fileLength)
                .body(fileData);
    }

    @GetMapping("/delete/{filename:.+}")
    public String deleteFile(@PathVariable String filename, Model model){
        String deleteError = null;
        if (! fileService.isFileExist(filename)) {
            deleteError = "File does not exist";
        }
        if(deleteError == null){
            fileService.deleteFile(filename);
            model.addAttribute("success", "true");
        }else{
            model.addAttribute("error", deleteError);
        }
        return "result";
    }


}

