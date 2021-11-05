package view;

import controller.PostController;
import controller.WriterController;
import model.Post;
import model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class WriterView {
    private final WriterController controller = new WriterController();
    private final PostController postController = new PostController();

    public void getAllWriters(){
        controller.getAllWriters().forEach(System.out::println);
    }

    public void getWriterById(){
        Writer writer = writerIdScanner();

        if(Objects.isNull(writer)){
            return;
        }

        System.out.println(writer);
    }

    public void createWriter(){
        Scanner scanner = new Scanner(System.in);
        Writer writer = new Writer();

        System.out.println("Enter first name:");
        String line = scanner.nextLine();
        writer.setFirstName(line);

        System.out.println("Enter last name:");
        line = scanner.nextLine();
        writer.setLastName(line);

        writer.setPosts(getPostListFromConsole());

        System.out.println("Writer created:\n" + controller.createWriter(writer));
    }

    public void updateWriter(){
        Writer writer = writerIdScanner();

        if(Objects.isNull(writer)){
            return;
        }

        System.out.println("""
                Select update option:
                1.First name
                2.Last name
                3.Post list""");

        int option = idScanner();

        switch (option){
            case 1 -> {
                System.out.println("Enter new first name:");
                Scanner scanner = new Scanner(System.in);
                String newFirstName = scanner.nextLine();
                writer.setFirstName(newFirstName);
            }
            case 2 -> {
                System.out.println("Enter new last name:");
                Scanner scanner = new Scanner(System.in);
                String newLastName = scanner.nextLine();
                writer.setLastName(newLastName);
            }
            case 3 -> writer.setPosts(getPostListFromConsole());
            default -> {
                System.out.println("Wrong option, try again...");
                return;
            }
        }

        System.out.println("Post updated:\n" + controller.updateWriter(writer));
    }

    public void deleteWriter(){
        Writer writer = writerIdScanner();

        if(Objects.isNull(writer)){
            return;
        }

        controller.deleteWriterById(writer.getId());

        System.out.println("Writer deleted");
    }

    private int idScanner(){
        Scanner scanner = new Scanner(System.in);
        String string_id = scanner.nextLine();

        if(!string_id.matches("^\\d{1,2}$")){
            return -1;
        }

        return Integer.parseInt(string_id);
    }

    private Writer writerIdScanner(){
        System.out.println("Enter writer id:");

        int id = idScanner();

        if(id == -1){
            System.out.println("Wrong id");
            return null;
        }

        Writer writer = controller.getWriterById(id);

        if(Objects.isNull(writer)){
            System.out.println("Writer does not exist");
        }
        return writer;
    }

    private List<Post> getPostListFromConsole(){
        System.out.println("Enter post id: ");
        System.out.println("(Enter /q when you finished)");
        List<Post> posts = new ArrayList<>();
        int id;
        while((id = idScanner()) != -1){
            Post post = postController.getPostById(id);
            if(Objects.isNull(post)){
                System.out.println("Post does not exist...");
                continue;
            }
            posts.add(post);
        }
        return posts;
    }

}
