package duke;

import Commands.Command;
import dukeExceptionsPackage.*;
import dukeTasksPackage.TaskList;
import storage.DukeStorage;
import ui.DukeUI;
import parser.Parser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Duke {
    public static final String FILE_PATH = "C:\\Users\\cwxky\\projects\\cs2113-git\\data\\duke.txt";
    private static DukeStorage storage;
    private static TaskList taskList;
    private static Parser parser;
    private static DukeUI ui;

    /**
     * constructor for Duke
     */
    public Duke() {
        storage = new DukeStorage(Paths.get(FILE_PATH));
        taskList = new TaskList();
        storage.initaliseTaskList(taskList);
        parser = new Parser();
        ui = new DukeUI();
    }
    public static void main(String[] args) {
        new Duke();
        run();
    }

    /**
     * To start the program
     */
    public static void run(){
        Scanner in = new Scanner(System.in);

        DukeUI.showWelcomeMessage();
        try {
            storage.loadTextFile();
            while (true) {
                String line = DukeUI.getInput();
                Command command = parser.parseInputString(line);
                switch (command) {
                    case BYE:
                        ui.showByeMessage();
                        System.exit(0);
                        break;
                    case TODO:
                        taskList.makeTodo(line);
                        storage.saveTasks(taskList);
                        break;
                    case DEADLINE:
                        taskList.makeDeadline(line);
                        storage.saveTasks(taskList);
                        break;
                    case EVENT:
                        taskList.makeEvent(line);
                        storage.saveTasks(taskList);
                        break;
                    case LIST:
                        taskList.showList();
                        break;
                    case MARK:
                        taskList.markAsDone(line);
                        storage.saveTasks(taskList);
                        break;
                    case UNMARK:
                        taskList.markAsUndone(line);
                        storage.saveTasks(taskList);
                        break;
                    case DELETE:
                        taskList.deleteTask(line);
                        storage.saveTasks(taskList);
                        break;
                    case CLEAR:
                        ui.clearTextFileMessage();
                        storage.clearFile();
                        break;
                    case FIND:
                        taskList.findWord(line);
                        break;
                    default:
                        try {
                            throw new UnrecognisedInput(line);
                        } catch (UnrecognisedInput e) {
                            System.out.println(e.getExceptionMessage());
                        }
                        break;
                }
            }
        } catch (FileDoesNotExistException e) {
            System.out.println(e.getExceptionMessage());
        } catch (IOException f) {
            System.out.println("Something went wrong");
        }
    }
}
