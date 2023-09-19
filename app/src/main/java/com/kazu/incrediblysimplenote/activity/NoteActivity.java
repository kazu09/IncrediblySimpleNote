package com.kazu.incrediblysimplenote.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    /** TAG */
    private final String TAG = "NoteActivity";

    /**
     * Open Display
     *
     * @param activity activity
     */
    public static void open(Activity activity) {
        Intent intent = new Intent(activity, NoteActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        initView();
        initDatabase();
        mBinding.back.setOnClickListener(v -> {
            saveNote();
        });
    }

    @Override
    public void onBackPressed() {
        saveNote();
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
        String title = !mBinding.title.getText().toString().equals("") ? mBinding.title.getText().toString() : "<No Title>";
        // Content
        String content = mBinding.content.getText().toString();
        noteRepository.setNoteInfo(title, content);
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

}
