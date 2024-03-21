package com.example.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel

class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    private val args: EditNoteFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return editNoteBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!


        editNoteBinding?.editNoteTitle?.setText(currentNote.noteTitle)
        editNoteBinding?.editNoteDesc?.setText(currentNote.noteDesc)
        editNoteBinding?.editNoteFab?.setOnClickListener {it
            val noteTitle =editNoteBinding?.editNoteTitle?.text.toString().trim()
            val noteDesc = editNoteBinding?.editNoteDesc?.text.toString().trim()
            if (noteTitle.isNotEmpty()) {
                val note = Note (currentNote.id, noteTitle, noteDesc)
                notesViewModel.updateNote (note)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context,  " Please enternote title", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun deleteNote () {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton( "Delete"){_,_ ->
                notesViewModel.deleteNote (currentNote)
                Toast.makeText(context, " Note Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack (R.id.homeFragment, false)
            }
            setNegativeButton(  "Cancel",  null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            } else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null
    }
}