package com.kazu.incrediblysimplenote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.kazu.incrediblysimplenote.databinding.ActivityNoteBinding;

public class NoteActivity extends AppCompatActivity {

    /** binding */
    private ActivityNoteBinding mBinding;

    public static void open(Activity activity) {
        Intent intent = new Intent(activity, NoteActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mBinding.back.setOnClickListener(v -> {
            finish();
        });
    }

    private void initView() {
        mBinding = ActivityNoteBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
    }
}
