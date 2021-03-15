package com.zakaria.AndroidArchitechure.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zakaria.AndroidArchitechure.adapter.NodeAdapter;
import com.zakaria.AndroidArchitechure.model.Note;
import com.zakaria.AndroidArchitechure.viewmodel.NoteViewModel;
import com.zakaria.AndroidArchitechure.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String des = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int prinority = data.getIntExtra(AddNoteActivity.EXTRA_PRINORITY, 1);

            Note note = new Note(title, des, prinority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Save", Toast.LENGTH_LONG).show();
        }


        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Note Can't Updated", Toast.LENGTH_LONG).show();
                return;
            }
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String des = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int prinority = data.getIntExtra(AddNoteActivity.EXTRA_PRINORITY, 1);
            Note note = new Note(title, des, prinority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note Can't Updated", Toast.LENGTH_LONG).show();

        }


        else {
            Toast.makeText(this, "Note not Save", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Flotting action btn
        FloatingActionButton addNoteBtn = findViewById(R.id.button_add_note);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
                //startActivity(intent);
            }
        });





        RecyclerView recyclerView = findViewById(R.id.recycler_home);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        final NodeAdapter adapter = new NodeAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                adapter.submitList(notes);
                //update our recycleer
                Toast.makeText(MainActivity.this, "Onchange" +notes.size(), Toast.LENGTH_LONG).show();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,

                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Item Deleted" , Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);



        adapter.setOnItemClickListener(new NodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRINORITY, note.getPriority());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_or_not:
                noteViewModel.deleteAllNote();
                Toast.makeText(MainActivity.this, "All Item Deleted" , Toast.LENGTH_LONG).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}