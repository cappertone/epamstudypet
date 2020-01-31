package com.epam.petproject.view;

import com.epam.petproject.controller.DeveloperController;
import com.epam.petproject.model.Developer;
import com.epam.petproject.repository.JavaIODeveloperRepositoryImpl;
import com.epam.petproject.service.DeveloperService;

import java.util.Scanner;

public class DeveloperView {
    private Scanner scanner = new Scanner(System.in);
    private InputOptions inputOption;
    private DeveloperController developerController = new DeveloperController(new DeveloperService(new JavaIODeveloperRepositoryImpl()));


    private InputOptions getUserChoice() {
        System.out.println("Select action: " + '\n'
                + "to getAll - GetAll" + '\n'
                + "to save - Save" + '\n'
                + "to read - Read" + '\n'
                + "to update - Update" + '\n'
                + "to Delete - Delete");
        return InputOptions.parseType(scanner.next().toUpperCase());
    }

    private void runApp() {
        while (inputOption != InputOptions.EXIT) {
            if (inputOption == null) {
                System.out.println("invalid try again");
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.GETALL)) {
                System.out.println(developerController.getElementCollection());
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.SAVE)) {
                System.out.println("Enter developer name");
                String name = scanner.next();
                developerController.save(name);
                inputOption = getUserChoice();
            } else if (inputOption.equals(InputOptions.READ)) {
                System.out.println("enter id");
                String id = scanner.next();
                Developer developer = developerController.getById(id);
                if (null != developer) {
                    System.out.println(developer);
                    inputOption = getUserChoice();
                } else {
                    System.out.println("invalid value");
                    inputOption = InputOptions.READ;
                }
            } else if (inputOption.equals(InputOptions.UPDATE)) {
                System.out.println("Enter element id");
                Long id = developerController.getIDfromInput(scanner.next());
                System.out.println("now choice: ADDSKILL, REMOVESKILL, ACCOUNT");
                String choice = scanner.next();
                if (id != 0L && !choice.equals("")) {
                    System.out.println("enter value");
                    String value = scanner.next();
                    developerController.updateElement(id,choice,value);
                } else {
                    System.out.println("invalid id or empty name");
                    inputOption = InputOptions.UPDATE;
                }
            } else {
                System.out.println("Enter id");
                developerController.deleteById(Long.parseLong(scanner.next()));
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        DeveloperView developerView = new DeveloperView();
        developerView.runApp();
    }
}
