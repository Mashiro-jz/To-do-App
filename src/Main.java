void main() throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        String fileName = "tasks.json";
        TaskManager taskManager = new TaskManager(fileName);

        while(running){
                System.out.println("===== TO-DO APP =====");
                System.out.println("1. Add task");
                System.out.println("2. Show tasks");
                System.out.println("3. Mark as done (change status)");
                System.out.println("4. Remove task");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");

                if (!scanner.hasNextInt()) {
                        System.out.println("Please enter a valid number.");
                        scanner.nextLine(); // clear buffer
                        continue;
                }
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice){
                        case 1 -> addTask(taskManager,scanner);
                        case 2 -> showTasks(taskManager);
                        case 3 -> markAsDone(taskManager,scanner);
                        case 4 -> removeTask(taskManager,scanner);
                        case 5 -> running = exit(taskManager, fileName);
                        default -> System.out.println("Invalid option. Try again.");
                }
        }

}
private static void addTask(TaskManager taskManager, Scanner scanner){
        System.out.println("Enter the task title:");
        String title = scanner.nextLine();

        System.out.println("Enter the task description:");
        String desc = scanner.nextLine();

        System.out.println("Enter the task due date: (year-month-day)");
        String dueDate = scanner.nextLine();

        System.out.println("Enter the task priority: (LOW-MEDIUM-HIGH)");
        String priority = scanner.nextLine().toUpperCase();
        Priority p = Priority.valueOf(priority);
        Task task = new Task(title, desc, dueDate, p);

        taskManager.addTask(task);
        System.out.println("Task added successfully!");
}

private static void showTasks(TaskManager taskManager){
        taskManager.showTasks();
}

private static void markAsDone(TaskManager taskManager, Scanner scanner){
        System.out.println("Enter the task title:");
        String title = scanner.nextLine();

        if(taskManager.exists(title))
                System.out.println("Task not found.");
        else{
                taskManager.toggleCompleted(title);
                System.out.println("Task status has been changed");
        }
}

private static void removeTask(TaskManager taskManager, Scanner scanner){
        System.out.println("Enter the task title:");
        String title = scanner.nextLine();

        if(taskManager.exists(title))
                System.out.println("Task not found.");
        else{
                taskManager.removeTask(title);
                System.out.println("Task has been deleted");
        }
}

private static boolean exit(TaskManager taskManager, String fileName) {
        taskManager.save(fileName);
        System.out.println("Tasks saved. Goodbye!");
        return false;
}



