package com.epam.petproject.view;

import com.epam.petproject.controller.SkillController;
import com.epam.petproject.repository.JavaIOSkillRepositoryImpl;
import com.epam.petproject.service.SkillService;

import java.util.Scanner;


public class SkillView {

    private Scanner scanner = new Scanner(System.in);
    private InputOptions inputOption;
    private SkillController skillController = new SkillController(new SkillService(new JavaIOSkillRepositoryImpl()));

    private InputOptions getUserChoice() {
        System.out.println("Select action: " + '\n'
                + "to getAll - GetAll" + '\n'
                + "to save - Save" + '\n'
                + "to read by id - Read" + '\n'
                + "to update - Update" + '\n'
                + "to Delete - Delete" + '\n'
                + "to exit - exit");
        return InputOptions.parseType(scanner.next().toUpperCase());
    }

    private void runApp() {

        while (inputOption != InputOptions.EXIT) {
            if (inputOption == null) {
                System.out.println("invalid try again");
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.GETALL)) {
                System.out.println(skillController.getElementCollection());
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.SAVE)) {
                System.out.println("Enter Value");
                System.out.println(skillController.save(scanner.next()));
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.READ)) {
                System.out.println("Enter element id");
                String id = scanner.next();
                System.out.println(skillController.getById(id));
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.UPDATE)) {
                System.out.println("Enter element id");
                String id = scanner.next();
                System.out.println("now new value");
                String name = scanner.next();
                System.out.println(skillController.updateElement(id, name));
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.DELETE)) {
                System.out.println("Enter id");
                String id = scanner.next();
                skillController.deleteById(id);
                inputOption = getUserChoice();
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        SkillView skillView = new SkillView();
        skillView.runApp();
    }
}
