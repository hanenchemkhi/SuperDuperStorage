package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;

    }

    public List<Note> findAllNotes(Integer userId) {
        return noteMapper.findAllNotes(userId);
    }
    public boolean isNoteExist(Integer noteId) {
        return noteMapper.getNote(noteId) != null;
    }

    public Note getNote(Integer noteId){
        return noteMapper.getNote(noteId);
    }

    public void updateNote(Note note, Integer userId) {
        noteMapper.update(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), userId));
    }
    public int addNote(Note note, Integer userId) {
        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userId));
    }
    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }


}
