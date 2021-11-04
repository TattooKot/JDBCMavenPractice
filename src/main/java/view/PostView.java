package view;

import controller.LabelController;
import controller.PostController;
import model.Label;
import model.Post;
import model.PostStatus;

import java.util.*;

public class PostView {
    private final PostController controller = new PostController();
    private final LabelController labelController = new LabelController();

    public void getAllPosts(){
        List<Post> postList = controller.getAllPosts();
        postList.forEach(System.out::println);
    }

    public void getPostById(){
        Post post = postIdScanner();

        if(Objects.isNull(post)){
            return;
        }
        System.out.println(post);
    }

    public void createPost(){
        System.out.println("Write post: ");
        System.out.println("(Enter /q when you finished)");

        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        String line;

        while(!(line = scanner.nextLine()).equals("/q")){
            content.append(line).append("\n");
        }

        System.out.println("Enter tags id: ");
        System.out.println("(Enter /q when you finished)");
        List<Label> labels = new ArrayList<>();
        int id;
        while((id = idScanner()) != -1){
            Label label = labelController.getLabelById(id);
            if(Objects.isNull(label)){
                System.out.println("Something goes wrong with label...");
                continue;
            }
            labels.add(label);
        }

        Post post = new Post();
        post.setContent(content.toString());
        post.setCreated(new Date());
        post.setUpdated(new Date());
        post.setStatus(PostStatus.ACTIVE);
        post.setLabels(labels);

        System.out.println("Post created:\n" + controller.createPost(post));

    }

    private int idScanner(){
        Scanner scanner = new Scanner(System.in);
        String string_id = scanner.nextLine();

        if(!string_id.matches("^\\d{1,2}$")){
            return -1;
        }

        return Integer.parseInt(string_id);
    }

    private Post postIdScanner(){
        System.out.println("Enter post id:");

        int id = idScanner();

        if(id == -1){
            System.out.println("Wrong id");
            return null;
        }

        Post post = controller.getPostById(id);

        if(Objects.isNull(post)){
            System.out.println("Post does not exist");
        }
        return post;
    }
}
