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
        String content = getContentFromConsole();
        List<Label> labels = getLabelListFromConsole();

        Post post = new Post();
        post.setContent(content);
        post.setCreated(new Date());
        post.setUpdated(new Date());
        post.setStatus(PostStatus.UNDER_REVIEW);
        post.setLabels(labels);

        System.out.println("Post created:\n" + controller.createPost(post));

    }

    public void updatePost(){
        Post post = postIdScanner();
        if(Objects.isNull(post)){
            return;
        }

        System.out.println("""
                Select update option:
                1.Content
                2.Post status
                3.Label list""");

        int option = idScanner();

        switch (option){
            case 1 ->{
                System.out.println("Write new content:");
                String content = getContentFromConsole();
                post.setContent(content);
            }
            case 2 ->{
                System.out.println("Select status id");
                PostStatus[] values = PostStatus.values();
                for(PostStatus status : values){
                    System.out.println(status.name());
                }
                int id = idScanner()-1;
                if(id == -1){
                    System.out.println("Wrong status id, try again");
                    return;
                }
                post.setStatus(values[id]);
            }
            case 3 -> post.setLabels(getLabelListFromConsole());
            default -> {
                System.out.println("Wrong option, try again");
                return;
            }
        }
        System.out.println("Post updated:\n" + controller.updatePost(post));
    }

    public void deletePostById(){
        Post post = postIdScanner();

        if(Objects.isNull(post)){
            return;
        }

        controller.deletePostById(post.getId());
        System.out.println("Post deleted");
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

    private String getContentFromConsole(){
        System.out.println("(Enter /q when you finished)");

        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        String line;

        while(!(line = scanner.nextLine()).equals("/q")){
            content.append(line).append("\n");
        }

        return content.toString();
    }

    private List<Label> getLabelListFromConsole(){
        System.out.println("Enter labels id: ");
        System.out.println("(Enter /q when you finished)");
        List<Label> labels = new ArrayList<>();
        int id;
        while((id = idScanner()) != -1){
            Label label = labelController.getLabelById(id);
            if(Objects.isNull(label)){
                System.out.println("Label does not exist...");
                continue;
            }
            labels.add(label);
        }
        return labels;
    }
}
