import java.time.LocalDateTime;
import java.util.*;

public class CommandTask {
    private final CompositeTask task;
    private List<Command> commands = new ArrayList<>();
    private int cursor = 0;
    private final Map<String, String> saved = new HashMap<>();

    public CommandTask(String title, LocalDateTime date) {
        task = new CompositeTask(title, date);
    }

    public void save(String key) {
        JsonVisitor visitor = new JsonVisitor();
        Renderer renderer1 = new Renderer(()->visitor);
        renderer1.render(task.getReport(CompositeSortType.TITLE_ASC));
        saved.put(key, visitor.getJson());
    }

    // TODO homework
    public void load(String key) {
        String json = saved.get(key);
        // subTask 삭제
        // json 순회하면서 복원
    }

    public void redo() {
        if (cursor == commands.size() - 1) return;
        commands.get(++cursor).execute(task);
    }

    public void undo() {
        if (cursor < 0) return;
        commands.get(cursor--).undo(task);
    }

    private void addCommand(Command cmd) {
        for (int i = commands.size() - 1; i > cursor; i--) {
            commands.remove(i);
        }
        cmd.execute(task);
        commands.add(cmd);
        cursor = commands.size() - 1;
    }

    public void toggle() {
        addCommand(new Toggle());
    }

    public void setTitle(String title) {
        addCommand(new Title(title));
    }

    public void setDate(LocalDateTime date) {
        addCommand(new Date(date));
    }

    public String getTitle() {
        return task.getTitle();
    }

    public LocalDateTime getDate() {
        return task.getDate();
    }

    public TaskReport getReport(CompositeSortType type) {
        return task.getReport(type);
    }

    public void addTask(String title, LocalDateTime date) {
        addCommand(new Add(title, date));
    }

    public void removeTask(CompositeTask task) {
        addCommand(new Remove(task));
    }
}
