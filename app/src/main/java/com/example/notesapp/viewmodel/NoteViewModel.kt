package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notesapp.model.Note
import androidx.lifecycle.viewModelScope
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepository: NoteRepository ) : AndroidViewModel(app) {

    fun insertNote(note: Note)=
        viewModelScope.launch{
            noteRepository.insertNote(note)
        }

    fun deleteNote(note: Note)=
        viewModelScope.launch{
            noteRepository.deleteNote(note)
        }

    fun updateNote(note: Note)=
        viewModelScope.launch{
            noteRepository.updateNote(note)
        }

    fun getAllNotes() = noteRepository.getAllNotes();

    fun searchNote(query:String) = noteRepository.searchNote(query)

}