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

    static {
        users = new HashMap<>();
    }

    //need to use synchronise keyword
    public synchronized String register(String emailAddress, String password) {

        boolean checkUserName = false;
        String message = "";
        EmailStore emails = new EmailStore();

        //Checking if the user already exists in the userStore
        if (users.get(emailAddress) != null) {
            checkUserName = true;
            message = "Email Adress already exists";
        } else {
            User user = new User(emailAddress, password);
            users.put(emailAddress, user);
            emails.initalizeHashMaps(emailAddress);
            message = "Account created";
        }

        //check if emailAddress (Key) exists
        //if Key does exist, it is unique, add user to map, and add all email maps (sent, recieved, new, spam). return true
        //else return false     
        //true will either automatically login() the user or just send a message saying "successful", meaning user will have to manually login()
        //false will make client get error saying register failed, try again
        return message;
    }

    public synchronized String login(String emailAddress, String password) {
        String message = "";

        String emailCheck;
        String passCheck;
        User userCheck;
        try {
            userCheck = users.get(emailAddress);
            emailCheck = userCheck.getEmailAddress();
            passCheck = userCheck.getPassword();

            if (emailCheck.equals(emailAddress) && passCheck.endsWith(password)) {
                message = "Login Successful";
            } else {
                message = "Invalid credentials please try again";
            }
        }catch(NullPointerException  npe){
            message="User Not found";
        }

        //check if emailAddress (Key) exists
        //if Key does exist, check password value matches
        //else return false
        //if password matches, return true
        //else return false
        //true return allows user to access all email options and information based on the address
        //false will make client get error saying login failed, try again
        return message;
    }

    public void test() {
        System.out.println(users.toString());
    }

    public synchronized String logout() {
        return "Logging out";
    }

    public void ReadFromFile() {

        //code that will pull data out of a file and save to the map
    }

    public void WriteToFile() {
        //code that will save the map data to a file when server is closed
    }
}
