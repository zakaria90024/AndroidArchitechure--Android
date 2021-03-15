package com.zakaria.AndroidArchitechure.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.zakaria.AndroidArchitechure.R;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.zakaria.AndroidArchitechure.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.zakaria.AndroidArchitechure.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.zakaria.AndroidArchitechure.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRINORITY = "com.zakaria.AndroidArchitechure.EXTRA_PRINORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker NumberPickPinority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editText_title_id);
        editTextDescription = findViewById(R.id.editText_description_id);
        NumberPickPinority = findViewById(R.id.numberpicker_id);
        NumberPickPinority.setMinValue(1);
        NumberPickPinority.setMaxValue(10);


        //for show indecator close and title
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Update Note");
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            NumberPickPinority.setValue(intent.getIntExtra(EXTRA_PRINORITY,1));

        }else {
            setTitle("Add Note");
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {

        String title = editTextTitle.getText().toString();
        String des = editTextDescription.getText().toString();
        int prinority = NumberPickPinority.getValue();

        if(title.trim().isEmpty() || des.trim().isEmpty()){
            Toast.makeText(this, "Plaease Fill Up Empty Field", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, des);
        data.putExtra(EXTRA_PRINORITY, prinority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}