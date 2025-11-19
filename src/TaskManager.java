import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    protected final List<Task> tasksList = new ArrayList<>();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .setPrettyPrinting()
            .create();

    private static final Type TASK_LIST_TYPE = new TypeToken<List<Task>>() {}.getType();

    public TaskManager(String fileName) throws IOException {
        Path path = Path.of(fileName);
        Files.createFile(path);
        try (Reader reader = Files.newBufferedReader(path)) {

            List<Task> loaded = GSON.fromJson(reader, TASK_LIST_TYPE);

            if (loaded != null) {
                tasksList.addAll(loaded);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addTask(Task task){
        tasksList.add(task);
    }

    public void removeTask(String title){
        tasksList.removeIf(task -> task.title.equals(title));
    }

    public void toggleCompleted(String title){

      tasksList
              .stream()
              .filter(task -> task.title.equals(title))
              .findAny()
              .ifPresent(task -> task.setCompleted(!task.completed));
    }

    public void showTasks(){
        if(!tasksList.isEmpty())
            tasksList.forEach(System.out::println);
        else
            System.out.println("Task list is empty");
    }

    public void save(String fileName) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(fileName))) {
            GSON.toJson(tasksList, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists(String title) {
        return tasksList.stream().noneMatch(task -> task.title.equals(title));
    }
}
