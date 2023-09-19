package com.kazu.incrediblysimplenote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kazu.incrediblysimplenote.databinding.ActivitySelectBinding;
import com.kazu.incrediblysimplenote.db.dao.NoteDao;
import com.kazu.incrediblysimplenote.db.helpers.NoteHelper;
import com.kazu.incrediblysimplenote.db.helpers.NoteRepository;

public class SelectActivity extends AppCompatActivity {

    /** binding */
    private ActivitySelectBinding mBinding;

    /** NoteRepository */
    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDatabase();
        mBinding.createNoteButton.setOnClickListener(v -> {
            NoteActivity.open(this);
        });
    }

    /**
     * initView
     */
    private void initView() {
        mBinding = ActivitySelectBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
    }

    /**
     * initDatabase
     */
    private void initDatabase() {
        // database Initialization
        NoteHelper noteHelper = NoteHelper.getInstance(this);
        NoteDao noteDao = noteHelper.noteDao();
        noteRepository = new NoteRepository(noteDao);
    }
}