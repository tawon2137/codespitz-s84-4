import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        CommandTask root = new CommandTask("Root", LocalDateTime.now());
        root.addTask("sub1", LocalDateTime.now());
        root.addTask("sub2", LocalDateTime.now());

        Renderer renderer1 = new Renderer(()->new ConsoleVisitor());
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));
        root.undo();
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));
        root.undo();
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));
        root.redo();
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

       TaskReport report = root.getReport(CompositeSortType.TITLE_ASC);
       List<TaskReport> list = report.getList();
       CompositeTask sub1 = list.get(0).getTask();
       CompositeTask sub2 = list.get(1).getTask();
       sub1.addTask("sub1_1", LocalDateTime.now());
       sub1.addTask("sub1_2", LocalDateTime.now());
       sub2.addTask("sub2_1", LocalDateTime.now());
       sub2.addTask("sub2_2", LocalDateTime.now());

       Renderer renderer2 = new Renderer(()->new JsonVisitor());
       renderer2.render(root.getReport(CompositeSortType.TITLE_ASC));
       /**
        * host code
        */
       root.save("test-key");
       root.addTask("sub3", LocalDateTime.now());
       System.out.println("");
       System.out.println("저장 후 add Task After Get Report");
        
       System.out.println("로드 후 Task After Get Report");
       root.load("test-key");
    }
}