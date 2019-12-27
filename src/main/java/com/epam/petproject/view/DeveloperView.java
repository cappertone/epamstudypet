package com.epam.petproject.view;

import com.epam.petproject.controller.DeveloperController;
import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;
import com.epam.petproject.model.Developer;

import java.util.Scanner;

public class DeveloperView {
    private Scanner scanner = new Scanner(System.in);
    private InputOptions inputOption;
    private DeveloperController developerController = new DeveloperController();


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
        while (inputOption == null) {
            inputOption = getUserChoice();
            if (inputOption == null) {
                System.out.println("invalid try again");
            } else if (inputOption.equals(InputOptions.GETALL)) {
                System.out.println(developerController.getElementCollection());
            } else if (inputOption.equals(InputOptions.SAVE)) {
                System.out.println("Enter comma separated skills");
                String skills = scanner.nextLine();
                System.out.println("Enter next one account status: Active , Inactive, Banned");
                AccountStatus status = AccountStatus.valueOf(scanner.next().toUpperCase());
                developerController.save(
                        new Developer(null, developerController.parseSkills(skills),
                                new Account(null, status)));
            } else if (inputOption.equals(InputOptions.READ)) {
                System.out.println("enter id");
                Long id = Long.parseLong(scanner.next());
                System.out.println(developerController.getById(id));
            } else if (inputOption.equals(InputOptions.UPDATE)) {
                System.out.println("Enter element id,");
                Long id = scanner.nextLong();
                System.out.println("now skill");
                String value = scanner.next();
                System.out.println("now account");
                AccountStatus status = AccountStatus.valueOf(scanner.next().toUpperCase());
                if (!value.equals("")) {
                    developerController.updateElement(new Developer(
                            id, developerController.parseSkills(value),
                            new Account(null, status)
                    ));
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
