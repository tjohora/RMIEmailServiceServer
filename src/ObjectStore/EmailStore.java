/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author TJ
 */
public class EmailStore {
    private static HashMap<String, ArrayList<Email>> sent;
    private static HashMap<String, ArrayList<Email>> received;
    private static HashMap<String, ArrayList<Email>> spam;
    private static HashMap<String, ArrayList<Email>> newEmails;
    
    static{
        sent = new HashMap<>();
        received = new HashMap<>();
        spam = new HashMap<>();
        newEmails = new HashMap<>();
    }
    
    public EmailStore() {
        ReadFromFile();
    }
    
    public synchronized boolean sendEmail(String sender, String sendDate, String subject, String content, String receiver){
        //SEND%%(email stuff)%%sender%%receiver
        //todo: loop to send to each receiver, catch is receiver does not exist and put into array
        Email email = new Email(sender, sendDate, subject, content);
        sent.computeIfAbsent(sender, k -> new ArrayList<>()).add(email);
        //for each receiver, add new email(){}
        newEmails.computeIfAbsent(receiver, k -> new ArrayList<>()).add(email);
        return true;
    }
    
    public void test(){
        System.out.println(sent.toString());
        System.out.println(newEmails.toString());
    }
    
    public ArrayList<Email> getAllUnreadEmails(String emailAddress){
        ArrayList<Email> emails = new ArrayList(newEmails.get(emailAddress));
        //System.out.println("Test: "+emails.toString());
        received.computeIfAbsent(emailAddress, k -> new ArrayList<>()).addAll(emails);
        newEmails.get(emailAddress).clear();
        return emails;
    }
    
    public ArrayList<Email> getAllReceivedEmails(String emailAddress){
        return received.get(emailAddress);
    }
    
    public ArrayList<Email> getAllSpamEmails(String emailAddress){
        return spam.get(emailAddress);      
    }
    
    public Email getSpecificEmail(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public synchronized boolean deleteEmail(String emailAddress, int selectedEmail){
        received.get(emailAddress).remove(selectedEmail);
        return true;
    }
    
    public synchronized boolean markEmailSpam(String emailAddress, int selectedEmail){
        spam.computeIfAbsent(emailAddress, k -> new ArrayList<>()).add(received.get(emailAddress).get(selectedEmail));
        received.get(emailAddress).remove(selectedEmail);
        return true;
    }
    
    public synchronized int deleteAllSpam(String emailAddress){
        int spamSize = spam.get(emailAddress).size();
        spam.get(emailAddress).clear();
        return spamSize;
    }
    
    
    public void ReadFromFile(){
        //code that will pull data out of a file and add them to the maps
    }
    
    public void WriteToFile(){
        //code that will save the maps data to a file when server is closed
    }
    
    
}
