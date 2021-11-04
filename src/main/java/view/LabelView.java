package view;

import controller.LabelController;
import model.Label;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LabelView {
    private final LabelController controller = new LabelController();

    public void getAllLabels(){
        List<Label> labels = controller.getAllLabels();
        labels.forEach(System.out::println);
    }

    public void getLabelById(){
        Label label = labelIdScanner();

        if(Objects.isNull(label)){
            return;
        }

        System.out.println(label);

    }

    public void createLabel(){
        System.out.println("Enter label name:");

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        Label label = controller.createLabel(name);

        if(Objects.isNull(label)){
            System.out.println("Something goes wrong...");
            return;
        }

        System.out.println("Label created: " + label);
    }

    public void updateLabel(){
        Label label = labelIdScanner();

        if(Objects.isNull(label)){
            return;
        }

        System.out.println("Enter new name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        label.setName(name);

        Label updated = controller.updateLabel(label);

        if(Objects.isNull(updated)){
            System.out.println("Something goes wrong...");
            return;
        }

        System.out.println("Label updated: " + updated);
    }

    public void deleteLabel(){
        Label label = labelIdScanner();

        if(Objects.isNull(label)){
            return;
        }

        if(controller.deleteLabelById(label.getId())){
            System.out.println("Label deleted");
        } else {
            System.out.println("Label does not deleted");
        }

    }

    private Label labelIdScanner(){
        System.out.println("Enter label id:");

        Scanner scanner = new Scanner(System.in);
        String string_id = scanner.nextLine();

        if(!string_id.matches("^\\d{1,2}$")){
            System.out.println("Wrong id");
            return null;
        }

        Label label = controller.getLabelById(Integer.parseInt(string_id));

        if(Objects.isNull(label)){
            System.out.println("Label does not exist");
        }
        return label;
    }
}
