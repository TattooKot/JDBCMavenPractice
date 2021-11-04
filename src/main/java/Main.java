import view.LabelView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LabelView labelView = new LabelView();

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
            }
            line = scanner.nextLine();
        }
    }
}
