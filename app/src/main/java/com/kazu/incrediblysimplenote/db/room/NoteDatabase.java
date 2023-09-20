/**
 * NoteDatabase.java
 * IncrediblySimpleNote
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */
package com.kazu.incrediblysimplenote.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kazu.incrediblysimplenote.db.dao.NoteDao;
import com.kazu.incrediblysimplenote.db.entity.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
