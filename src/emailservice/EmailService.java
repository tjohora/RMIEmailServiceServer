/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailservice;

import Commands.Command;
import Commands.LoginCommand;
import Commands.RegisterCommand;
import ObjectStore.Email;
import ObjectStore.EmailStore;
import ObjectStore.UserStore;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
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
    private Utils reader = new Utils();
    private String name;
    private ThreadGroup group;

    private String breakingChar = "%%";
    private String breakingObjectChar = "¬¬";

    //EmailService newClient = new EmailService(group, clientLink.getInetAddress(), clientLink, emailstore, userstore);
    // public EmailService(ThreadGroup group, String name, Socket clientLink, EmailStore emailStore, UserStore userStore) {
    public EmailService(Socket clientLink, EmailStore emailStore, UserStore userStore) {
        //super(group, name);

        try {

            this.clientLink = clientLink;
            this.emailStore = emailStore;
            this.userStore = userStore;

            // Create the stream for reading from the Client
            input = new Scanner(new InputStreamReader(this.clientLink.getInputStream()));
            // Create the stream for writing to the Client
            output = new PrintWriter(this.clientLink.getOutputStream(), true);
        } catch (IOException e) {
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
                        response = userStore.login(components[1], components[2]);

                        break;
                    case "REGISTER":
                        response = userStore.register(components[1], components[2]);

                        break;
                    case "SEND_MAIL":
                        boolean sentStatus;

                        String[] recipients = components[5].split("¬¬");

                        System.out.println(recipients.toString());
                        System.out.println(recipients.length);
                        ArrayList validUsers = new ArrayList<>();
                        ArrayList<String> invalidUsers = new ArrayList<String>();
                        boolean validFlag = false;
                        boolean invalidFlag = false;

                        for (int i = 0; i <= recipients.length - 1; i++) {
                            if (userStore.getUsers().containsKey(recipients[i])) {
                                validUsers.add(recipients[i]);
                                validFlag = true;

                            } else {
                                invalidUsers.add(recipients[i]);

                                invalidFlag = true;
                            }
                        }

                        if (validFlag && !invalidFlag) {
                            //SUCCESS
                            sentStatus = emailStore.sendEmail(components[1], components[2], components[3], components[4], validUsers);
                            response = "SUCCESS";
                        } else if (validFlag && invalidFlag) {
                            String invalidUserRes = "";
                            invalidUserRes = invalidUsers.get(0);
                            if (invalidUsers.size() > 1) {
                                for (int i = 1; i < invalidUsers.size() - 1; i++) {
                                    invalidUserRes = invalidUserRes.concat(breakingObjectChar + invalidUsers.get(i));
                                }
                            }

                            response = "PARTIAL_SUCCESS" + breakingChar + invalidUserRes;
                            //PARTIAL_SUCCESS

                        } else {
                            //FAILED
                            response = "FAILED";
                        }

                        break;
                    case "GET_SENT_EMAILS":
                        ArrayList<Email> sentEmails = new ArrayList();
                        sentEmails = emailStore.getAllSentEmails(components[1]);
                        response = "GET_EMAILS%%";
                        if (sentEmails.size() == 0) {
                            response = "NO_EMAILS";
                        } else {
                            for (int i = 0; i < sentEmails.size(); i++) {
                                response = response.concat(sentEmails.get(i).toStringToclient());
                            }
                        }
                        System.out.println(response);
                        break;

                    case "GET_UNREAD_EMAILS":
                        ArrayList<Email> unreadEmails = new ArrayList();
                        unreadEmails = emailStore.getAllUnreadEmails(components[1]);
                        response = "GET_UNREAD_EMAILS%%";
                        if (unreadEmails.size() == 0) {
                            response = "NO_NEW_EMAILS";
                        } else {
                            for (int i = 0; i < unreadEmails.size(); i++) {
                                response = response.concat(unreadEmails.get(i).toStringToclient());
                            }
                        }

                        break;
                    case "GET_READ_EMAILS":
                        ArrayList<Email> readEmails = new ArrayList();
                        readEmails = emailStore.getAllReceivedEmails(components[1]);
                        response = "GET_EMAILS%%";
                        if (readEmails.size() == 0) {
                            response = "NO_EMAILS";
                        } else {
                            for (int i = 0; i < readEmails.size(); i++) {
                                response = response.concat(readEmails.get(i).toStringToclient());
                            }
                        }
                        System.out.println(response);
                        break;
                    case "GET_SPAM":
                        ArrayList<Email> spamEmails = new ArrayList();
                        spamEmails = emailStore.getAllSpamEmails(components[1]);
                        response = "GET_EMAILS%%";
                        if (spamEmails.size() == 0) {
                            response = "NO_EMAILS";
                        } else {
                            for (int i = 0; i < spamEmails.size(); i++) {
                                response = response.concat(spamEmails.get(i).toStringToclient());
                            }
                        }

                        break;
                    case "GET_SEARCH_EMAILS":
                        ArrayList<Email> searchEmails = new ArrayList();
                        searchEmails = emailStore.getSpecificEmail(components[1], components[2]);
                        response = "SEARCH_EMAILS%%";
                        if (searchEmails.size() == 0) {
                            response = "NO_EMAILS";
                        } else {
                            for (int i = 0; i < searchEmails.size(); i++) {
                                response = response.concat(searchEmails.get(i).toStringToclient());
                            }
                        }
                        break;
                    case "DELETE_EMAILS":
                        response = emailStore.deleteEmail(components[1], Integer.parseInt(components[2]));
                        break;
                    case "MARK_SPAM":
                        response = emailStore.markEmailSpam(components[1], Integer.parseInt(components[2]));
                        break;
                    case "DELETE_SPAM":
                        response = emailStore.deleteAllSpam(components[1]);
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
