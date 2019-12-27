package com.epam.petproject.view;

import com.epam.petproject.controller.SkillController;
import com.epam.petproject.model.Skill;

import java.util.Scanner;


public class SkillView {

    private Scanner scanner = new Scanner(System.in);
    private InputOptions inputOption;
    private SkillController skilController = new SkillController();


    private InputOptions getUserChoice() {
        System.out.println("Select action: " + '\n'
                + "to getAll - GetAll" +'\n'
                + "to save - Save" + '\n'
                + "to read - Read" + '\n'
                + "to update - Update" + '\n'
                + "to Delete - Delete");
        return InputOptions.parseType(scanner.next().toUpperCase());
    }

    private void runApp() {
        while (inputOption == null) {
            inputOption = getUserChoice();
            if (inputOption == null) {
                System.out.println("invalid try again");
            } else if (inputOption.equals(InputOptions.GETALL)) {
                System.out.println( skilController.getElementCollection());
            } else if (inputOption.equals(InputOptions.SAVE)) {
                System.out.println("Enter Value");
                skilController.save(new Skill(null, scanner.next()));
            } else if (inputOption.equals(InputOptions.READ)) {
                System.out.println(skilController.getElementCollection());
            } else if (inputOption.equals(InputOptions.UPDATE)) {
                System.out.println("Enter element id and new name");
                Long id = scanner.nextLong();
                System.out.println("now name");
                String name = scanner.next();
                if(!name.equals("")){
                    skilController.updateElement(new Skill(id,name));
                }
            }else {
                System.out.println("Enter id");
                skilController.deleteById(Long.parseLong(scanner.next()));
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        SkillView sview = new SkillView();
        sview.runApp();
    }
}
