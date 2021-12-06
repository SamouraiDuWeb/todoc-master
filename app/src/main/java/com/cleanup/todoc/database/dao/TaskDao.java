package com.cleanup.todoc.database.dao;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    int deleteTask(long id);

    @Insert
    void createTask(Task taskDemo);

    @Query("SELECT id, projectId, name, creationTimestamp FROM Task WHERE id = :id")
    Task getTask(long id);
}
