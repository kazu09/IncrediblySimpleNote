/**
 * NoteActivity.java
 * IncrediblySimpleNote
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */
package com.kazu.incrediblysimplenote.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kazu.incrediblysimplenote.databinding.ActivityNoteBinding;
import com.kazu.incrediblysimplenote.db.dao.NoteDao;
import com.kazu.incrediblysimplenote.db.helpers.NoteHelper;
import com.kazu.incrediblysimplenote.db.helpers.NoteRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {

    /** binding */
    private ActivityNoteBinding mBinding;

    /** NoteRepository */
    private NoteRepository noteRepository;

    /** Update Flag */
    private boolean isUpdateNote = false;

    /** uid */
    private int uid;

    /** TAG */
    private final String TAG = "NoteActivity";

    private final static String UID = "UID";

    private final static String TITLE = "TITLE";

    private final static String CONTENT = "CONTENT";

    private final static String FLAG = "FLAG";

    /**
     * Open Display
     *
     * @param activity activity
     */
    public static void open(Activity activity, int uid, String title, String content, boolean isUpdateNote) {
        Intent intent = new Intent(activity, NoteActivity.class);
        intent.putExtra(UID, uid);
        intent.putExtra(TITLE, title);
        intent.putExtra(CONTENT, content);
        intent.putExtra(FLAG, isUpdateNote);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        initView();
        initDatabase();
        Intent intent = getIntent();
        isUpdateNote = intent.getBooleanExtra(FLAG, false);
        if (isUpdateNote) {
            // Set existing text
            uid = intent.getIntExtra(UID, -1);
            mBinding.title.setText(intent.getStringExtra(TITLE));
            mBinding.content.setText(intent.getStringExtra(CONTENT));
        }

        mBinding.back.setOnClickListener(v -> {
            if (isUpdateNote) {
                // Update save
                updateNote();
            } else {
                // New save
                saveNote();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isUpdateNote) {
            // Update save
            updateNote();
        } else {
            // New save
            saveNote();
        }
    }

    /**
     * initView
     */
    private void initView() {
        mBinding = ActivityNoteBinding.inflate(getLayoutInflater());
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

    /**
     * Save Note
     */
    @SuppressLint("CheckResult")
    private void saveNote() {
        // Title is empty, Change to "No Title"
        String newTitle = !mBinding.title.getText().toString().equals("") ? mBinding.title.getText().toString() : "<No Title>";
        // Content
        String newContent = mBinding.content.getText().toString();
        noteRepository.setNoteInfo(newTitle, newContent);
        // Save to database
        noteRepository.insertNote()
                .subscribeOn(Schedulers.io()) // IO thread
                .observeOn(AndroidSchedulers.mainThread()) // Receive results main thread
                .subscribe(newNote -> {
                    // Save to database is complete
                    finish();
                }, throwable -> {
                    // error
                    Log.d(TAG,throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    private void updateNote() {
        if (uid < 0) {
            Toast.makeText(this, "An error has occurred.", Toast.LENGTH_LONG).show();
            finish();
        }
        // Title is empty, Change to "No Title"
        String newTitle = !mBinding.title.getText().toString().equals("") ? mBinding.title.getText().toString() : "<No Title>";
        // Content
        String newContent = mBinding.content.getText().toString();
        noteRepository.setNoteInfo(uid, newTitle, newContent);
        // update to database
        noteRepository.updateNote()
                .subscribeOn(Schedulers.io()) // IO thread
                .observeOn(AndroidSchedulers.mainThread()) // Receive results main thread
                .subscribe(newNote -> {
                    // Save to database is complete
                    finish();
                }, throwable -> {
                    // error
                    Log.d(TAG,throwable.getMessage());
                });
    }

}
