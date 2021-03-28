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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TJ
 */
public class EmailService implements Runnable {

    /**
     * @param args the command line arguments
     */
    private Socket clientLink;
    private EmailStore emailStore;
    private UserStore userStore;
    private Scanner input;
    private PrintWriter output;
    
    private String name;
    private ThreadGroup group;
    
    private String breakingChar = "%%";
            
    
    
    //EmailService newClient = new EmailService(group, clientLink.getInetAddress(), clientLink, emailstore, userstore);

   // public EmailService(ThreadGroup group, String name, Socket clientLink, EmailStore emailStore, UserStore userStore) {
        public EmailService(Socket clientLink, EmailStore emailStore, UserStore userStore) {
        //super(group, name);
        
        try{
            
            this.clientLink = clientLink;
            this.emailStore = emailStore;
            this.userStore = userStore;
            
            // Create the stream for reading from the Client
            input = new Scanner(new InputStreamReader(this.clientLink.getInputStream()));
            // Create the stream for writing to the Client
            output = new PrintWriter(this.clientLink.getOutputStream(), true);
        }catch (IOException e)
        {
            System.out.println("An exception occurred while setting up connection links for a thread: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String message = "";
            String response;
        try {

            //InetAddress response = clientLink.getInetAddress();
            int responsePort = clientLink.getPort();

            
            

            while (!message.equals("EXIT")) {
                response = null;
                System.out.println("Before nextline");

                message = input.nextLine();
                System.out.println("After nextline. user said: " + message);

                //System.out.println("Message received: " + message + ", from " + response + " on port " + responsePort);

                String[] components = message.split("%%");
                switch (components[0]) {
                    case "LOGIN":

                        break;
                    case "REGISTER":
                        response = "ECHO%%Got your message, it was" + components[1];
                        
                        break;
                    case "LOGOUT":

                        break;
                    case "SEND_MAIL":
                        boolean sentStatus;
                        //create an array of recivers and pass into the send email
                        if(userStore.getUsers().containsKey(components[5])){
                            sentStatus = emailStore.sendEmail(components[1], components[2], components[3], components[4], components[5]);
                        }else{
                            sentStatus = false;
                        }               
                        if(sentStatus == true){
                            response = "SUCCESS";
                        }else{
                            response = "FAILED";
                        }
                        
                        break;
                    case "GET_UNREAD_EMAILS":

                        break;
                    case "GET_READ_EMAILS":

                        break;
                    case "GET_SPAM":

                        break;
                    case "SEARCH_EMAILS":

                        break;
                    case "DELETE_EMAILS":

                        break;
                    case "MARK_SPAM":

                        break;
                    case "DELETE_SPAM":

                        break;
                }
                output.println(response);
                output.flush();
            }
            clientLink.close();

        } catch (IOException ex) {
            Logger.getLogger(EmailServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//        Command command = new LoginCommand();
//        System.out.println(command.execute());
//        
//        UserStore users = new UserStore();
//        users.login("Test", "123456");
//        //users.test();
//        
//        
//        String test = "LOGIN%%";
//        
//        EmailStore emails = new EmailStore();
//        emails.sendEmail("Bob", "1/11/1111", "FirstEmail", "Hi, this is an email", "Jon");
//        emails.sendEmail("Bob", "1/11/1111", "SecEmail", "qwetretrt", "Jon");
//
//        emails.sendEmail("Jon", "1/11/1111", "123Email", "TestLorem", "Bob");
//        //emails.test();
//        
//        //System.out.println(emails.getAllUnreadEmails("Jon").toString());
//        
//        System.out.println(emails.getAllUnreadEmails("Jon").toString());
//        System.out.println(emails.getAllUnreadEmails("Jon").toString());
//        //emails.getAllUnreadEmails("Jon");
//        
//        //emails.deleteEmail("Jon", 0);
}
