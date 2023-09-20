/**
 * SelectActivity.java
 * IncrediblySimpleNote
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */
package com.kazu.incrediblysimplenote.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kazu.incrediblysimplenote.adpter.SelectListRecyclerViewAdapter;
import com.kazu.incrediblysimplenote.common.Constants;
import com.kazu.incrediblysimplenote.databinding.ActivitySelectBinding;
import com.kazu.incrediblysimplenote.db.dao.NoteDao;
import com.kazu.incrediblysimplenote.db.entity.Note;
import com.kazu.incrediblysimplenote.db.helpers.NoteHelper;
import com.kazu.incrediblysimplenote.db.helpers.NoteRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SelectActivity extends AppCompatActivity {

    /** binding */
    private ActivitySelectBinding mBinding;

    /** NoteRepository */
    private NoteRepository noteRepository;

    /** adapter */
    SelectListRecyclerViewAdapter adapter;

    /** TAG */
    private final String TAG = "SelectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDatabase();
        mBinding.createNoteButton.setOnClickListener(v -> {
            NoteActivity.open(this,0 ,Constants.EMPTY, Constants.EMPTY, Constants.NEW_NOTE);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        noteRepository.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        notes -> {
                            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                            adapter = new SelectListRecyclerViewAdapter(notes);
                            mBinding.recyclerView.setAdapter(adapter);
                            adapter.setNoteClickListener(new SelectListRecyclerViewAdapter.OnNoteClickListener() {
                                @Override
                                public void onItemClick(Note item) {
                                    // Update note
                                    NoteActivity.open(SelectActivity.this, item.uid, item.title, item.content, Constants.UPDATE_NOTE);
                                }

                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onItemDeleteButton(Note item) {
                                    // Delete button
                                    // Dialog
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this);
                                    builder.setTitle("confirmation");
                                    builder.setMessage("Are you sure you want to delete the memo?");
                                    // OK button
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // OK
                                            noteRepository.setNoteInfo(item.uid, item.title, item.content);
                                            noteRepository.deleteNote()
                                                    .subscribeOn(Schedulers.io()) // IO thread
                                                    .observeOn(AndroidSchedulers.mainThread()) // Receive results main thread
                                                    .subscribe(newNote -> {
                                                        reloadActivity();
                                                    }, throwable -> {
                                                        // error
                                                        Log.d(TAG,throwable.getMessage());
                                                    });

                                        }
                                    });
                                    // Cancel Button
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Cancel
                                        }
                                    });
                                    // Show dialog
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            });

                        },
                        error -> {

                        }
                );
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

    /**
     * reload
     */
    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}