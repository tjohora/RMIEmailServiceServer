/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Callback_Support;

import Business.Email;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author TJ
 */
public interface RMIClientInterface extends Remote {
    public void newEmailNotification(Email newEmail) throws RemoteException; 
}
