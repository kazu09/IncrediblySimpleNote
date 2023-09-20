/**
 * NoteHelper.java
 * IncrediblySimpleNote
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */
package com.kazu.incrediblysimplenote.db.helpers;

import android.content.Context;

import androidx.room.Room;

import com.kazu.incrediblysimplenote.db.dao.NoteDao;
import com.kazu.incrediblysimplenote.db.room.NoteDatabase;

public class NoteHelper {
    /** NoteHelper instance **/
    private static NoteHelper instance;

    /** NoteDatabase */
    private final NoteDatabase noteDatabase;

    /**
     * Databas initialization
     *
     * @param context context
     */
    private NoteHelper(Context context) {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "note.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Dao
     */
    public NoteDao noteDao() {
        return noteDatabase.noteDao();
    }

    /**
     * NoteHelper get instance
     *
     * @param context context
     * @return instance
     */
    public static synchronized NoteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NoteHelper(context);
        }
        return instance;
    }
}
