/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Callback_Support.RMIClientInterface;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import java.rmi.RemoteException;

/**
 *
 * @author TJ
 */
public class UserStore extends UnicastRemoteObject implements RMIUserInterface {

    private final HashMap<String, User> USERS = new HashMap<>();
    private final HashMap<String, RMIClientInterface> clientList = new HashMap<>();

    public UserStore() throws RemoteException {

    }

    public HashMap<String, User> getUsers() {
        return this.USERS;
    }

//    public ArrayList<RMIClientInterface> getObservers() {
//        return this.clientList;
//    }
    /**
     * Adds user to the UserStore
     * Method takes in emailAddress and password from the client. The Map is checked
     * if the email address is unique. If the Users.get is null, it means it 
     * is unique, so add the new user to the Map. Otherwise, return false.
     * @param emailAddress
     * @param password
     * @return boolean
     * @throws RemoteException 
     */
    @Override
    public boolean register(String emailAddress, String password) throws RemoteException {
        EmailStore emails = new EmailStore();
        synchronized (USERS) {
            if (USERS.get(emailAddress) != null) {
                return false;
            } else {
                User user = new User(emailAddress, password);
                USERS.put(emailAddress, user);
                emails.initalizeHashMaps(emailAddress);
                return true;
            }
        }

    }
    

    /**
     * Logs user in.
     * Method that takes in an email address, password and the client interface
     * object of that user. If a user object can be made, the name is present in
     * in the system. If the password provided matched the one in the system,
     * user is authenticated and is added to the Map of observers. Otherwise, return
     * false.
     * @param emailAddress
     * @param password
     * @param client
     * @return boolean
     * @throws RemoteException 
     */
    @Override
    public boolean login(String emailAddress, String password, RMIClientInterface client) throws RemoteException {
        try {
            User u = USERS.get(emailAddress);
            if (u != null) { //confirm matching user found
                if (u.getPassword().equals(password)) {//checking password
                    registerForCallback(emailAddress, client);
                    return true;
                }
            }
            return false;//no match found
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        return false;//no match found or error occurred
    }

    /**
     * Logs user out. 
     * @param emailAddress
     * @param client
     * @return
     * @throws RemoteException 
     */
    @Override
    public synchronized String logout(String emailAddress, RMIClientInterface client) throws RemoteException {
        unregisterForCallback(emailAddress, client);
        return "Logging out";
    }
    
    public RMIClientInterface checkObserver(String emailAddress){
        RMIClientInterface client = clientList.get(emailAddress);
        if (client != null) { 
            return client;
        }
        return null;
    }
    
    

    @Override
    public boolean registerForCallback(String emailAddress, RMIClientInterface client) throws RemoteException {
        synchronized (clientList) {
            if (client != null && !clientList.containsKey(client)) {
                clientList.put(emailAddress, client);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean unregisterForCallback(String emailAddress, RMIClientInterface client) throws RemoteException {
        synchronized (clientList) {
            if (client != null && clientList.containsKey(emailAddress)) {
                clientList.remove(emailAddress);
                return true;
            }
            return false;
        }
    }

    public void ReadFromFile() {

        //code that will pull data out of a file and save to the map
    }

    public void WriteToFile() {
        //code that will save the map data to a file when server is closed
    }
}
