package com.epam.petproject.view;

import com.epam.petproject.controller.AccountController;
import com.epam.petproject.model.Account;
import com.epam.petproject.model.AccountStatus;

import java.util.Scanner;

public class AccountView {
    private Scanner scanner = new Scanner(System.in);
    private InputOptions inputOption;
    private AccountController accountController = new AccountController();


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
                System.out.println( accountController.getElementCollection());
            } else if (inputOption.equals(InputOptions.SAVE)) {
                System.out.println("Enter next one : Active , Inactive, Banned");
                accountController.save(new Account(null, AccountStatus.valueOf(scanner.next().toUpperCase())));
            } else if (inputOption.equals(InputOptions.READ)) {
                System.out.println(accountController.getElementCollection());
            } else if (inputOption.equals(InputOptions.UPDATE)) {
                System.out.println("Enter element id and new name");
                Long id = scanner.nextLong();
                System.out.println("now name");
                String value = scanner.next();
                if(!value.equals("")){
                    accountController.updateElement(new Account(id, AccountStatus.valueOf(value)));
                }
            }else {
                System.out.println("Enter id");
                accountController.deleteById(Long.parseLong(scanner.next()));
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        AccountView accView = new AccountView();
        accView.runApp();
    }
}
