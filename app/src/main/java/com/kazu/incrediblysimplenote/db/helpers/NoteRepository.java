/**
 * NoteRepository.java
 * IncrediblySimpleNote
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */
package com.kazu.incrediblysimplenote.db.helpers;

import com.kazu.incrediblysimplenote.db.dao.NoteDao;
import com.kazu.incrediblysimplenote.db.entity.Note;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteRepository {
    /** NoteDao */
    private final NoteDao noteDao;

    /** Uid */
    private int Uid = -1;

    /** Title */
    private String Title = "";

    /** Content */
    private String Content = "";

    /**
     * Constructor
     * @param noteDao NoteDao
     */
    public NoteRepository(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    /**
     * Note info
     *
     * @param title title
     * @param content content
     */
    public void setNoteInfo(String title, String content) {
        Title = title;
        Content = content;
    }

    /**
     * Note info
     *
     * @param uid uid
     * @param title title
     * @param content content
     */
    public void setNoteInfo(int uid, String title, String content) {
        Uid = uid;
        Title = title;
        Content = content;
    }

    /**
     * Insert
     *
     * @return Observable.fromCallable
     */
    public Observable<Note> insertNote() {
        return Observable.fromCallable(() -> {
                    Note noteEntity = new Note();
                    noteEntity.uid = 0;
                    noteEntity.title = Title;

                    noteEntity.content = Content;
                    noteDao.insert(noteEntity);
                    return noteEntity;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Update
     *
     * @return Observable.fromCallable
     */
    public Observable<Note> updateNote() {
        return Observable.fromCallable(() -> {
                    Note noteEntity = new Note();
                    noteEntity.uid = Uid;
                    noteEntity.title = Title;

                    noteEntity.content = Content;
                    noteDao.update(noteEntity);
                    return noteEntity;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Delete
     *
     * @return Observable.fromCallable
     */
    public Observable<Note> deleteNote() {
        return Observable.fromCallable(() -> {
                    // このブロック内でデータベース操作を実行
                    Note noteEntity = new Note();
                    noteEntity.uid = Uid;
                    noteEntity.title = Title;
                    noteEntity.content = Content;
                    noteDao.delete(noteEntity);
                    return noteEntity;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Note>> getAllNotes() {
        return noteDao.getAll();
    }
}
