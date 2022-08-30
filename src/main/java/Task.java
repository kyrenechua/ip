public class Task {
    protected String description;
    protected int number;
    protected  char status = ' ';
    protected char alphabet = ' ';
    protected boolean isDone;
    public void markAsDone(Task t) {
        System.out.println("Good job! You have completed another task! I've marked this task as done:\n" + "    [X] " + t.description);
        t.isDone = true;
        t.status = 'X';
    }
    public void markAsUndone(Task t) {
        System.out.println("Ok, I've unmarked this task. Remember to do it soon!\n" + "    [ ] " + t.description);
        t.isDone = false;
        t.status = ' ';
    }

    @Override
    public String toString() {
        return "   [" + alphabet + "]" + " [" + status + "] " + description;
    }

}
