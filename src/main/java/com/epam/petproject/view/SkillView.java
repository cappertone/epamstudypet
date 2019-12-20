package com.epam.petproject.view;

import com.epam.petproject.controller.SkilController;

import java.util.Scanner;


public class SkillView {

    private Scanner scanner = new Scanner(System.in);
    private InputOptions inputOption;
    private static InputOptions[] options = InputOptions.values();

    private SkilController skilController = new SkilController();


    private InputOptions getUserChoice() {
        System.out.println("Select action: " + '\n'
                + "to create - Create" + '\n'
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
            } else if (inputOption.equals(InputOptions.CREATE)) {
                System.out.println("Enter Value");
                skilController.createElement(scanner.next());
            } else if (inputOption.equals(InputOptions.READ)) {
                System.out.println(skilController.getElementCollection());
            } else if (inputOption.equals(InputOptions.UPDATE)) {
                System.out.println("Enter element name and new value");
                String element = scanner.next();
                System.out.println("now value");
                String value = scanner.next();
                if(!element.equals("")){
                    skilController.updateElement(element,value);
                }
            }else {
                System.out.println("Enter Value");
                skilController.deleteElement(scanner.next());
            }
        }
        scanner.close();

    }

    public static void main(String[] args) {
        SkillView sview = new SkillView();
        sview.runApp();
    }
}
