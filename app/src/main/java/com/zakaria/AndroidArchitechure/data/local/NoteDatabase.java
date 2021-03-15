package com.zakaria.AndroidArchitechure.data.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zakaria.AndroidArchitechure.data.local.NoteDatabase;
import com.zakaria.AndroidArchitechure.model.Note;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();


    public static  synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }return instance;
    }



    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyantask(instance).execute();
        }
    };



    private static class  PopulateDBAsyantask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;
        private PopulateDBAsyantask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //noteDao.insert(new Note("title 1 ", "des 1", 1));
            //noteDao.insert(new Note("title 2 ", "des 2", 2));
            //noteDao.insert(new Note("title 3 ", "des 3", 3));
            return null;
        }
    }
}
