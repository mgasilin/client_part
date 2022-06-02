package com.example.test.model;

import android.content.Context;
import android.service.autofill.AutofillService;

import com.example.test.Server.Server;

import java.util.ConcurrentModificationException;

public class Comment {
    private long id;
    private long event_id;
    private long author_id;
    private String message;
    private long sequence_number;
    private String  authorName=null;

    public Comment(long id, long event_id, long author_id, String message, long sequence_number) {
        this.id = id;
        this.event_id = event_id;
        this.author_id = author_id;
        this.message = message;
        this.sequence_number = sequence_number;
    }

    public Comment(long id,long event_id, long author_id, String message, long sequence_number, String user_name) {
        this.id = id;
        this.event_id = event_id;
        this.author_id = author_id;
        this.message = message;
        this.sequence_number = sequence_number;
        this.authorName=user_name;
    }

    public long getId() {
        return id;
    }

    public long getEvent_id() {
        return event_id;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public String getMessage() {
        return message;
    }

    public long getSequence_number() {
        return sequence_number;
    }

    public String getAuthorName(){
        return authorName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", event_id=" + event_id +
                ", author_id=" + author_id +
                ", message='" + message + '\'' +
                ", sequence_number=" + sequence_number +
                '}';
    }

    public Comment(long event_id, long author_id, String message) {
        this.event_id = event_id;
        this.author_id = author_id;
        this.message = message;
    }


}
