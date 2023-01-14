package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController{
    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }
    @PostMapping ("/save")
    public String saveChanges (@ModelAttribute Note note, Authentication authentication, Model model, RedirectAttributes redirectAttributes){
        String noteError= null;

        Integer userId = userService.getUser(authentication.getName()).getUserId();
        if(note.getNoteTitle().equals("")){
            noteError= "Please enter a title";
        }
        if(noteError == null){
            if(noteService.isNoteExist(note.getNoteId())) {
                noteService.updateNote(note, userId);
            }else{
                noteService.addNote(note, userId );
            }
            model.addAttribute("success","true");
            //redirectAttributes.addFlashAttribute("status", "success");
        }else{
            model.addAttribute("error", noteError);
            //redirectAttributes.addFlashAttribute("status", noteError);
        }

        return "result";
    }
    @GetMapping("/delete/{noteid:.+}")
    public String deleteNote(@PathVariable Integer noteid, Model model) {
        String deleteError = null;
        if (!noteService.isNoteExist(noteid)) {
            deleteError = "Note does not exist";
        }
        if (deleteError == null) {
            noteService.deleteNote(noteid);
            model.addAttribute("success", "true");
        } else {
            model.addAttribute("error", deleteError);
        }
        return "result";
    }
}



