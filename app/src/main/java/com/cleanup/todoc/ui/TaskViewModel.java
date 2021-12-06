package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final TaskDataRepository tasksDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    @Nullable
    private LiveData<List<Project>> currentProjects;

    public TaskViewModel(TaskDataRepository tasksDataSource, ProjectDataRepository projectDataSource,
                         Executor executor){
        this.tasksDataSource = tasksDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init(){
        if (this.currentProjects != null) {
            return;
        }
        currentProjects = projectDataSource.getProjects();
    }

    // FOR TASKS
    public LiveData<List<Task>> getTasks(){
        return tasksDataSource.getTasks();
    }

    public void createTask(Task task){
        executor.execute(()->{
            tasksDataSource.createTask(task);
        });
    }

    public void deleteTask(long id){
        executor.execute(()->{
            tasksDataSource.deleteTask(id);
        });
    }

}
