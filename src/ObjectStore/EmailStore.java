/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectStore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author TJ
 */
public class EmailStore {
    HashMap<String, ArrayList<Email>> sent;
    HashMap<String, ArrayList<Email>> recieved;
    HashMap<String, ArrayList<Email>> spam;
    HashMap<String, ArrayList<Email>> newEmails;
    
    public EmailStore() {
        ReadFromFile();
    }
    
    public boolean sendEmail(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList<Email> getAllUnreadEmails(String emailAddress){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void ReadFromFile(){
        //code that will pull data out of a file and add them to the maps
    }
    
    public void WriteToFile(){
        //code that will save the maps data to a file when server is closed
    }
    
    
}
