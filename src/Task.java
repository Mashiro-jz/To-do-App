import java.time.LocalDate;

public class Task {
    String title;
    String description;
    // CHANGE TO LocalDateTime to get (dd-MM-yyyy HH:mm:ss)
    LocalDate dueDate;
    boolean completed = false;
    Priority priority;

    public Task(String title, String description, String dueDate, Priority priority) {
        this.priority = priority;
        this.dueDate = LocalDate.parse(dueDate);
        this.description = description;
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return """
            TASK
            ------------------------------
            Title      : %s
            Description: %s
            Due date   : %s
            Completed  : %s
            ------------------------------
            """.formatted(
                title,
                description,
                dueDate,
                completed ? "Yes" : "No"
        );
    }
}

