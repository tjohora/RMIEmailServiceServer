/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailservice;

import ObjectStore.Email;
import ObjectStore.EmailStore;
import ObjectStore.User;
import ObjectStore.UserStore;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author TJ
 */
public class EmailServer {
    
    public static void main(String[] args) {
        final int MY_PORT = 16000;
        boolean continueRunning = true;
        
        EmailStore emailstore = new EmailStore();
        UserStore userstore = new UserStore();
        Utils reader = new Utils();
        ArrayList<User> usersToRead = new ArrayList();
        ArrayList<Email> newMailToRead = new ArrayList();
        ArrayList<Email> spamToRead = new ArrayList();
        ArrayList<Email> recEmailToRead = new ArrayList();
        ArrayList<Email> sentEmailToRead = new ArrayList();
        
        try {
            ServerSocket connectionSocket = new ServerSocket(MY_PORT);
            ThreadGroup group = new ThreadGroup("Client threads");
            group.setMaxPriority(Thread.currentThread().getPriority() - 1);
            usersToRead = reader.readUsers();
            newMailToRead = reader.getNewEmails();
            spamToRead = reader.getSpamEmails();
            recEmailToRead = reader.gerRecEmails();

            
            for (int i = 0; i < usersToRead.size(); i++) {
                userstore.register(usersToRead.get(i).getEmailAddress(), usersToRead.get(i).getPassword());
            }
            
            for (int i = 0; i < newMailToRead.size(); i++) {
                emailstore.writeEmailToHashMap(newMailToRead.get(i).getSender(), newMailToRead.get(i).getSendDate(), newMailToRead.get(i).getSubject(), newMailToRead.get(i).getContent(), newMailToRead.get(i).getRecepiant());
            }
            
            for (int i = 0; i < spamToRead.size(); i++) {
                emailstore.ReadSpamFromFile(spamToRead.get(i).getRecepiant(), spamToRead.get(i));
            }
            
            for (int i = 0; i < recEmailToRead.size(); i++) {
                emailstore.writeInEmails(recEmailToRead.get(i).getRecepiant(), recEmailToRead.get(i));
            }
            

            
            
            System.out.println(emailstore.getAllSentEmails("Sean"));
            System.out.println(emailstore.getAllSentEmails("Jim"));
            System.out.println("Now ready to accept requests.");
          
           
            
            while (continueRunning) {
                Socket clientLink = connectionSocket.accept();
                //EmailService newClient = new EmailService(clientLink, emailstore, userstore);
                //EmailService newClient = new EmailService(group, clientLink.getInetAddress()+"", clientLink, emailstore, userstore);
                EmailService newClient = new EmailService(clientLink, emailstore, userstore);
                //newClient.start();
                Thread clientWrapper = new Thread(newClient);
                clientWrapper.start();
            }
             
            connectionSocket.close();
            reader.writeToFile();
            
        } catch (SocketException ex) {
            System.out.println("A problem occurred when creating the socket on port " + MY_PORT);
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("A problem occurred when reading in a message from the stream.");
            System.out.println(ex.getMessage());
        }
    }
}
