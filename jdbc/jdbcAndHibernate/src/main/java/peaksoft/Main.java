package peaksoft;

import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        //userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Muhammad", "Abdulla", (byte) 63);
            userService.saveUser("AbuBakr", "AsSudduk", (byte) 63);
            userService.saveUser("Umar", "IbnAbuTalib", (byte) 59);
            userService.saveUser("Usman", "IbnAffan", (byte) 82);
            System.out.println("Dobavlen v basu dannuh");
        System.out.println(userService.getAllUsers());


    }
}
