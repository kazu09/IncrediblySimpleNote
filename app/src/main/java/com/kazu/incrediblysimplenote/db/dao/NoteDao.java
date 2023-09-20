/**
 * NoteDao.java
 * IncrediblySimpleNote
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */
package com.kazu.incrediblysimplenote.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kazu.incrediblysimplenote.db.entity.Note;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    Single<List<Note>> getAll();

    @Update
    void update(Note note);

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);
}
