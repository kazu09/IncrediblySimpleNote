package com.kazu.incrediblysimplenote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kazu.incrediblysimplenote.databinding.ActivitySelectBinding;

public class SelectActivity extends AppCompatActivity {

    /** binding */
    private ActivitySelectBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mBinding.createNoteButton.setOnClickListener(v -> {
            NoteActivity.open(this);
        });
    }

    private void initView() {
        mBinding = ActivitySelectBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
    }
}