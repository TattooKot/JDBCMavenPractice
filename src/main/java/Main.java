import view.LabelView;
import view.PostView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LabelView labelView = new LabelView();
        PostView postView = new PostView();

        System.out.println("Enter command:");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("q")){
            switch (line) {
                case "create label" -> labelView.createLabel();
                case "get all labels" -> labelView.getAllLabels();
                case "get label by id" -> labelView.getLabelById();
                case "update label" -> labelView.updateLabel();
                case "delete label" -> labelView.deleteLabel();
                case "create post" -> postView.createPost();
                case "get all posts" -> postView.getAllPosts();
                case "get post by id" -> postView.getPostById();
            }
            line = scanner.nextLine();
        }
    }
}
