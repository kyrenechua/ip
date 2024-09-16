package dukeTasksPackage;

import dukeExceptionsPackage.*;
import ui.DukeUI;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Show a list of tasks
 */
public class TaskList {

    private DukeUI ui ;
    protected static ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<Task>();
        ui = new DukeUI();
    }

    /**
     * clear the task list
     */
    public void clearTaskList() {
        tasks.clear();
    }
    /**
     * To add task into tasklist
     * @param t task to be added
     */
    public void addTask(Task t) {
        tasks.add(t);
    }

    /**
     * Returns the task located at the specified position in the task list
     * @param itemNumber 0-based item number of task to be retrieved
     * @return Task
     */
    public Task getTask(int itemNumber) {
        return tasks.get(itemNumber);
    }

    /**
     * To get the size of the task list
     * @return size of task list
     */
    public int getTaskListSize() {
        return tasks.size();
    }

    /**
     * Removes a task at the specified position in the task list.
     * @param itemNumber 0-based item number of task to be deleted
     */
    public void removeTask(int itemNumber) {
        tasks.remove(itemNumber);
    }

    /**
     * to convert input into todo data type and adds the todo into the task list
     * @param input user input
     * @return the input that has been converted into a todo type
     */
    public void makeTodo(String input){
        try {
            if (input.equals("todo")) {
                throw new EmptyDescriptionException(input);
            } else {
                String description = input.substring(input.indexOf(' ') + 1);
                Todo todo = new Todo(description);
                DukeUI.addTaskMessage(description);
                addTask(todo);            }
        } catch (EmptyDescriptionException e) {
            System.out.println(e.getExceptionMessage());
        }
    }
    /**
     * to convert input into deadline data type and adds the deadline into the task list
     * @param input user input
     */
    public void makeDeadline(String input) {
        try {
            if (input.equals("deadline")) {
                throw new EmptyDescriptionException(input);
            } else {
                if (!input.contains("/") || !input.contains("by")) {
                    throw new UnrecognisedDeadlineException(input);
                } else {
                    String description = input.substring(input.indexOf(' ') + 1, input.indexOf("/by") - 1);
                    String dueDate = input.substring(input.indexOf("/by") + 4);
                    Deadline deadline = new Deadline(description, dueDate);
                    DukeUI.addTaskMessage(description);
                    addTask(deadline);
                }
            }
        } catch (EmptyDescriptionException e) {
            System.out.println(e.getExceptionMessage());
        } catch (UnrecognisedDeadlineException f) {
            System.out.println(f.getExceptionMessage());
        }
    }
    /**
     * To convert user input string into event data type
     * @param input user input
     * @return the input that has been converted into deadline data type
     */
    public void makeEvent(String input) {
        try {
            if (input.equals("event")) {
                throw new EmptyDescriptionException(input);
            } else {
                if (!input.contains("/") || !input.contains("at")) {
                    throw new UnrecognisedEventException(input);
                } else {
                    String description = input.substring(input.indexOf(' ') + 1, input.indexOf("/at") - 1);
                    String date = input.substring(input.indexOf("/at") + 4);
                    Event event = new Event(description, date);
                    DukeUI.addTaskMessage(description);
                    addTask(event);
                }
            }
        } catch (EmptyDescriptionException e) {
            System.out.println(e.getExceptionMessage());
        } catch (UnrecognisedEventException f) {
            System.out.println(f.getExceptionMessage());
        }
    }

    /**
     * To delete a task from Task list
     * @param input user input
     */
    public void deleteTask(String input) {
        try {
            int itemNumber = Integer.parseInt(input.split(" ")[1]) - 1;
            if (itemNumber < 0 || itemNumber > tasks.size() - 1) {
                throw new IllegalTaskNumber(input);
            } else {
                Task toBeRemoved = tasks.get(itemNumber);
                removeTask(itemNumber);
                ui.deleteTaskMessage(toBeRemoved.description);
            }
        } catch (IllegalTaskNumber e) {
            System.out.println(e.getExceptionMessage());
        }
    }
    /**
     * To mark a task as done
     * @param input user input
     */
    public void markAsDone(String input) {
        try {
            if (input.equals("mark")) {
                throw new UnrecognisedInput(input);
            }
            else {
                int itemNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (itemNumber < 0 || itemNumber > tasks.size() - 1) {
                    throw new IllegalTaskNumber("error");
                } else {
                    Task t = tasks.get(itemNumber);
                    if (t.isDone) {
                        throw new IllegalMarkingException("already marked");
                    } else {
                        t.isDone = true;
                        t.status = 'X';
                        ui.markingMessage(t.description, true);
                    }
                }
            }
        } catch (IllegalTaskNumber e) {
            System.out.println(e.getExceptionMessage());
        } catch (IllegalMarkingException f) {
            System.out.println(f.getExceptionMessage());
        } catch (UnrecognisedInput g) {
            System.out.println(g.getExceptionMessage());
        }
    }
    /**
     * To mark a task as undone
     * @param input user input
     */
    public void markAsUndone(String input) {
        try {
            if (input.equals("unmark")) {
                throw new UnrecognisedInput(input);
            } else {
                int itemNumber = Integer.parseInt(input.split(" ")[1]) - 1;
                if (itemNumber < 0 || itemNumber > tasks.size() - 1) {
                    throw new IllegalTaskNumber("error");
                } else {
                    Task t = tasks.get(itemNumber);
                    if (t.isDone) {
                        t.isDone = false;
                        t.status = ' ';
                        ui.markingMessage(t.description, false);
                    } else {
                        throw new IllegalUnmarkingException("still unmarked\n");
                    }
                }
            }
        } catch (IllegalTaskNumber e) {
            System.out.println(e.getExceptionMessage());
        } catch (IllegalUnmarkingException f) {
            System.out.println(f.getExceptionMessage());
        } catch (UnrecognisedInput g) {
            System.out.println(g.getExceptionMessage());
        }
    }
    /**
     * To show the list of tasks inputted by the user to Duke
     */
    public void showList() {
        try {
            if (tasks.isEmpty()) {
                throw new EmptyListException("empty");
            } else {
                ui.listMessage();
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(tasks.get(i));
                }
            }
        } catch (EmptyListException e) {
            System.out.println(e.getExceptionMessage());
        }
    }


    /**
     * to find all tasks that contain a keyword
     * @param input user input
     */
    public void findWord(String input) {
        try {
            if (input.equals("find")) {
                throw new EmptyKeywordException("no keyword");
            } else {
                String[] words = input.split("\\s");
                ArrayList<Integer> results = new ArrayList<>();
                String keyword = words[1].toLowerCase();
                for (int i = 0; i < tasks.size(); i++) {
                    Task search = tasks.get(i);
                    if (search.description.toLowerCase().contains(keyword)) {
                        results.add(i);
                    }
                }
                if (results.isEmpty()) {
                    throw new NoSuchKeywordException(input);
                } else {
                    ui.showFindMessage();
                    for (int taskNumber : results) {
                        Task print = tasks.get(taskNumber);
                        System.out.println(print.toString());
                    }
                }
            }
        } catch (EmptyKeywordException e) {
            System.out.println(e.getExceptionMessage());
        } catch (NoSuchKeywordException f) {
            System.out.println(f.getExceptionMessage());
        }

    }
}
