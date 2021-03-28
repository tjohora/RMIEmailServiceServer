/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailservice;

import ObjectStore.EmailStore;
import ObjectStore.UserStore;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author TJ
 */
public class EmailServer {
    public static void main(String[] args){
        final int MY_PORT = 16000;
        boolean continueRunning = true;
        
        EmailStore emailstore = new EmailStore();
        UserStore userstore = new UserStore();
        
        try{
            ServerSocket connectionSocket = new ServerSocket(MY_PORT);
            ThreadGroup group = new ThreadGroup("Client threads");
            group.setMaxPriority(Thread.currentThread().getPriority() - 1);
            
            
            System.out.println("Now ready to accept requests.");
            
            while (continueRunning){
                Socket clientLink = connectionSocket.accept();
                //EmailService newClient = new EmailService(clientLink, emailstore, userstore);
                //EmailService newClient = new EmailService(group, clientLink.getInetAddress()+"", clientLink, emailstore, userstore);
                EmailService newClient = new EmailService(clientLink, emailstore, userstore);
                //newClient.start();
                Thread clientWrapper = new Thread(newClient);
                clientWrapper.start();
            }
            
            connectionSocket.close();
        }catch (SocketException ex)
        {
            System.out.println("A problem occurred when creating the socket on port " + MY_PORT);
            System.out.println(ex.getMessage());
        } catch (IOException ex)
        {
            System.out.println("A problem occurred when reading in a message from the stream.");
            System.out.println(ex.getMessage());
        }
    }
}
