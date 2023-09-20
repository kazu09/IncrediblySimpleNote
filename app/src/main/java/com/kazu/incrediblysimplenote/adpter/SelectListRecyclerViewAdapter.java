package com.kazu.incrediblysimplenote.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kazu.incrediblysimplenote.R;
import com.kazu.incrediblysimplenote.db.entity.Note;

import java.util.List;

public class SelectListRecyclerViewAdapter extends RecyclerView.Adapter<SelectListRecyclerViewAdapter.SelectViewHolder> {

    /** Note list */
    private List<Note> selectList;

    /** Recycler view tap click listener */
    private  OnNoteClickListener listener;

    /**
     * Constructor
     * @param selectList NoteList
     */
    public SelectListRecyclerViewAdapter(List<Note> selectList) {
        this.selectList = selectList;
    }

    public interface OnNoteClickListener {
        /** Item click listener */
        void onItemClick(Note item);

        /** Delete click listener */
        void onItemDeleteButton(Note item);
    }

    /**
     * clickListener
     *
     * @param listener listener
     */
    public void setNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SelectListRecyclerViewAdapter.SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectListRecyclerViewAdapter.SelectViewHolder holder, int position) {
        Note item = selectList.get(position);
        // Title set
        holder.textView.setText(item.title);

        // Item click listener
        holder.linearLayout.setOnClickListener(v -> {
            listener.onItemClick(item);
        });

        // Delete click listener
        holder.imageView.setOnClickListener(v -> {
            listener.onItemDeleteButton(item);
        });
    }

    @Override
    public int getItemCount() {
        return selectList.size();
    }

    public static class SelectViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        ImageView imageView;
        SelectViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.delete_Button);
        }
    }
}
