/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectStore;

import java.util.HashMap;

/**
 *
 * @author TJ
 */
public class UserStore {
    public static HashMap<String, User> users;
    
    public UserStore() {
        ReadFromFile();
    }
    
    static{
        users = new HashMap<>();
    }
    
    //need to use synchronise keyword

    public boolean register(String emailAddress, String password) {
        User user = new User(emailAddress, password);
        users.put(emailAddress, user);
        //check if emailAddress (Key) exists
        //if Key does exist, it is unique, add user to map, and add all email maps (sent, recieved, new, spam). return true
        //else return false     
        
        //true will either automatically login() the user or just send a message saying "successful", meaning user will have to manually login()
        //false will make client get error saying register failed, try again
        return false;
    }
    
    public boolean login(String emailAddress, String password){
        User user = new User(emailAddress, password);
        users.put(emailAddress, user);
        
        //check if emailAddress (Key) exists
        //if Key does exist, check password value matches
        //else return false
        //if password matches, return true
        //else return false
        
        
        //true return allows user to access all email options and information based on the address
        //false will make client get error saying login failed, try again
        return false;
    }
    
    public void test(){
        System.out.println(users.toString());
    }
    
    public void logout(){
        //will close the system with a goodbye message
    }
    
    public void ReadFromFile(){
        
        //code that will pull data out of a file and save to the map
    }
    
    public void WriteToFile(){
        //code that will save the map data to a file when server is closed
    }
}
