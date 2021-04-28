/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Callback_Support.RMIClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author TJ
 */
public interface RMIUserInterface extends Remote {
    public boolean register(String emailAddress, String password) throws RemoteException;
    public boolean login(String emailAddress, String password, RMIClientInterface client) throws RemoteException;
    public String logout(String emailAddress, RMIClientInterface client) throws RemoteException;
    
    public boolean registerForCallback(String emailAddress, RMIClientInterface client) throws RemoteException;
    public boolean unregisterForCallback(String emailAddress, RMIClientInterface client) throws RemoteException;
}
