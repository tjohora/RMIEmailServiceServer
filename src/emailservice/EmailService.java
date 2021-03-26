/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailservice;

import Commands.Command;
import Commands.LoginCommand;
import Commands.RegisterCommand;
import ObjectStore.EmailStore;
import ObjectStore.UserStore;

/**
 *
 * @author TJ
 */
public class EmailService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Command command = new LoginCommand();
        System.out.println(command.execute());
        
        UserStore users = new UserStore();
        users.login("Test", "123456");
        users.test();
        
        
        String test = "LOGIN%%";
    }
    
}
