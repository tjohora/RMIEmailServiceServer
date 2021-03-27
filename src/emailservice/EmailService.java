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
        //users.test();
        
        
        String test = "LOGIN%%";
        
        EmailStore emails = new EmailStore();
        emails.sendEmail("Bob", "1/11/1111", "FirstEmail", "Hi, this is an email", "Jon");
        emails.sendEmail("Bob", "1/11/1111", "SecEmail", "qwetretrt", "Jon");

        emails.sendEmail("Jon", "1/11/1111", "123Email", "TestLorem", "Bob");
        //emails.test();
        
        //System.out.println(emails.getAllUnreadEmails("Jon").toString());
        
        System.out.println(emails.getAllUnreadEmails("Jon").toString());
        System.out.println(emails.getAllUnreadEmails("Jon").toString());
        //emails.getAllUnreadEmails("Jon");
        
        //emails.deleteEmail("Jon", 0);
    }
    
}
