/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author TJ
 */
public interface RMIEmailInterface extends Remote {
    public ArrayList sendEmail(String sender, String sendDate, String subject, String content, ArrayList<String> recipients) throws RemoteException;
    public ArrayList getAllUnreadEmails(String emailAddress) throws RemoteException;
    public ArrayList getAllReceivedEmails(String emailAddress) throws RemoteException;
    public ArrayList getAllSpamEmails(String emailAddress) throws RemoteException;
    public ArrayList getAllSentEmails(String emailAddress) throws RemoteException;
    public ArrayList getSpecificEmail(String emailAddress, String subject) throws RemoteException;
    public boolean deleteEmail(String emailAddress, int selectedEmail) throws RemoteException;
    public boolean markEmailSpam(String emailAddress, int selectedEmail) throws RemoteException;
    public String deleteAllSpam(String emailAddress) throws RemoteException;
}
