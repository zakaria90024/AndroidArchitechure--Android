package com.zakaria.AndroidArchitechure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.zakaria.AndroidArchitechure.R;
import com.zakaria.AndroidArchitechure.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NodeAdapter extends ListAdapter<Note, NodeAdapter.NoteHolder> {
    //private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;


    //for animation constractor
    public NodeAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {


            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };



    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note CurrentNote = getItem(position);
        holder.textTitle.setText(CurrentNote.getTitle());
        holder.textViewDes.setText(CurrentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(CurrentNote.getPriority()));

    }

//    @Override
//    public int getItemCount() {
//        return notes.size() ;
//    }
//
//
//    public void setNotes(List<Note> notes){
//        this.notes = notes;
//        notifyDataSetChanged();
//    }


    public  Note getNoteAt(int position){
        return  getItem(position);
    }


    class  NoteHolder extends RecyclerView.ViewHolder
    {
        private TextView textTitle;
        private TextView textViewDes;
        private TextView textViewPriority;

        public  NoteHolder(View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textView_title_id);
            textViewDes = itemView.findViewById(R.id.textView_des_id);
            textViewPriority = itemView.findViewById(R.id.textView_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));

                    }
                }
            });
        }
    }


    //for onclick listtener on item

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }
}
