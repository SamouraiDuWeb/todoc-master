package com.cleanup.todoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 2L;
    private static long TEST_TIMESTAMP = 1499070300000L;
    private static Task TASK_DEMO = new Task(PROJECT_ID, "Test Task", TEST_TIMESTAMP);


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private Project testProject = new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);
    private long projectId = testProject.getId();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {

        this.database.projectDao().insertProject(testProject);
        this.database.taskDao().createTask(TASK_DEMO);


        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {

        this.database.projectDao().insertProject(testProject);
        this.database.taskDao().createTask(TASK_DEMO);

        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        this.database.taskDao().deleteTask(task.getId());
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());

    }

    @Test
    public void createAndGetProject() throws InterruptedException {
        this.database.projectDao().insertProject(testProject);
        List<Project> project = LiveDataTestUtil.getValue(this.database.projectDao().getProjects());
        assertTrue(project.get(0).getId() == projectId
                && project.get(0).getName().equals(testProject.getName())
                && project.get(0).getColor() == testProject.getColor());
    }


    @After
    public void closeDb() {
        database.close();
    }
}
