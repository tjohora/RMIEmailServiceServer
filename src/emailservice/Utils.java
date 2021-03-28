/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailservice;

import ObjectStore.Email;
import ObjectStore.EmailStore;
import ObjectStore.User;
import ObjectStore.UserStore;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TJ
 */
public class Utils {
    //Creating Variables for reading and writing to file
    private static String filePath = new File(".").getAbsoluteFile().toString();
    private static UserStore users = new UserStore();
    private static EmailStore emails = new EmailStore();
    private static HashMap<String, User> userToWrite;
    private static HashMap<String, ArrayList<Email>> recivedToWrite;
    private static HashMap<String, ArrayList<Email>> spamMailToWrite;

    public Utils() {
    }
    
    /**
     * Takes user data in from file
     * @return ArrayList<User>
     */
    public ArrayList<User> readUsers() {
        ArrayList<User> usersToAdd = new ArrayList();
        try {

            BufferedReader in = new BufferedReader(new FileReader(filePath + "\\src\\Data\\Data.txt"));

            String line;
            while ((line = in.readLine()) != null) {
                User u = new User();
                String[] val = line.split("%%");
                u.setEmailAddress(val[0]);
                u.setPassword(val[1]);
                usersToAdd.add(u);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usersToAdd;
    }
    /**
     * Takes in all new Email's from File
     * @return ArrayList<Email>
     */
    public ArrayList<Email> getNewEmails() {
        ArrayList<Email> emailToAdd = new ArrayList();
        try {

            BufferedReader in = new BufferedReader(new FileReader(filePath + "\\src\\Data\\newMails.txt"));

            String line;
            while ((line = in.readLine()) != null) {
                Email e = new Email();
                String[] val = line.split("%%");
                e.setSender(val[0]);
                e.setSendDate(val[1]);
                e.setSubject(val[2]);
                e.setContent(val[3]);
                e.setRecepiant(val[4]);

                emailToAdd.add(e);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emailToAdd;
    }
    /**
     * Gets all Received email's from file
     * @return ArrayList<Email>
     */
    public ArrayList<Email> gerRecEmails(){
        ArrayList<Email> emailToAdd = new ArrayList();
        try {

            BufferedReader in = new BufferedReader(new FileReader(filePath + "\\src\\Data\\recEmail.txt"));

            String line;
            while ((line = in.readLine()) != null) {
                Email e = new Email();
                String[] val = line.split("%%");
                e.setSender(val[0]);
                e.setSendDate(val[1]);
                e.setSubject(val[2]);
                e.setContent(val[3]);
                e.setRecepiant(val[4]);

                emailToAdd.add(e);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emailToAdd;
    }

    public ArrayList<Email> getSpamEmails() {
        ArrayList<Email> emailToAdd = new ArrayList();
        try {

            BufferedReader in = new BufferedReader(new FileReader(filePath + "\\src\\Data\\recEmail.txt"));

            String line;
            while ((line = in.readLine()) != null) {
                Email e = new Email();
                String[] val = line.split("%%");
                e.setSender(val[0]);
                e.setSendDate(val[1]);
                e.setSubject(val[2]);
                e.setContent(val[3]);
                e.setRecepiant(val[4]);
                emailToAdd.add(e);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return emailToAdd;
    }

    public void writeToFile() {
        try {
            FileWriter spamWriter = new FileWriter(filePath + "\\src\\Data\\spamMail.txt");
            FileWriter newMailWriter = new FileWriter(filePath + "\\src\\Data\\recEmail.txt");
            FileWriter userWriter = new FileWriter(filePath + "\\src\\Data\\Data.txt");

            spamMailToWrite = emails.getSpamMail();
            userToWrite = users.getUsers();
            recivedToWrite = emails.getRecEmails();
            Iterator hmIterator = userToWrite.entrySet().iterator();
            Iterator hmIterator2 = spamMailToWrite.entrySet().iterator();
            Iterator hmIterator3 = recivedToWrite.entrySet().iterator();

            while (hmIterator.hasNext()) {
                Map.Entry mapElement = (Map.Entry) hmIterator.next();
                User user = (User) mapElement.getValue();
                userWriter.write(user.toString() + "\r\n");
            }

            while (hmIterator2.hasNext()) {
                Map.Entry mapElement = (Map.Entry) hmIterator2.next();
                ArrayList<Email> emails = (ArrayList<Email>) mapElement.getValue();
                for (int i = 0; i < emails.size(); i++) {
                   

                    spamWriter.write(emails.get(i).toStringWrite() + "\r\n");
                }

            }

            while (hmIterator3.hasNext()) {
                Map.Entry mapElement = (Map.Entry) hmIterator3.next();
                ArrayList<Email> emails = (ArrayList<Email>) mapElement.getValue();
                for (int i = 0; i < emails.size(); i++) {
                    

                    newMailWriter.write(emails.get(i).toStringWrite() + "\r\n");
                }
            }

            newMailWriter.close();
            spamWriter.close();
            userWriter.close();

        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
