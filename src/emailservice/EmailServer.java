/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailservice;

import Business.Email;
import Business.EmailStore;
import Business.User;
import Business.UserStore;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TJ
 */
public class EmailServer {

    public static void main(String[] args) throws RemoteException {
        try {
            UserStore userService = new UserStore();
            EmailStore emailService = new EmailStore(userService);
            

            int portNum = 16000;
            startRegistry(portNum);

            String registryPath = "rmi://localhost:" + portNum;
            String EmailLabel = "/EmailService";
            String UserLabel = "/UserService";

            Naming.rebind(registryPath + EmailLabel, emailService);
            Naming.rebind(registryPath + UserLabel, userService);

        } catch (RemoteException ex) {
            Logger.getLogger(EmailServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(EmailServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();

        } catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);

            // Create a registry on the given port number
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }

    }
}
