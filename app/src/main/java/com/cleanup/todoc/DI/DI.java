package com.cleanup.todoc.DI;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;

import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DI {

    public static TaskDataRepository provideTasksDataSource(Context context){
        TodocDatabase dataBase = TodocDatabase.getInstance(context);
        return new TaskDataRepository(dataBase.taskDao());
    }

    public static ProjectDataRepository provideProjectSource(Context context){
        TodocDatabase dataBase = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(dataBase.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static  ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTasksDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
