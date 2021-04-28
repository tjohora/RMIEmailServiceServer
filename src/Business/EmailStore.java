/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Callback_Support.RMIClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TJ
 */
public class EmailStore extends UnicastRemoteObject implements RMIEmailInterface {

    private final HashMap<String, ArrayList<Email>> SENT = new HashMap<>();
    private final HashMap<String, ArrayList<Email>> RECEIVED = new HashMap<>();
    private final HashMap<String, ArrayList<Email>> SPAM = new HashMap<>();
    private final HashMap<String, ArrayList<Email>> NEW_EMAILS = new HashMap<>();

    private UserStore userStore;

    //private static HashMap<String, RMIClientInterface> observers;
    public EmailStore(UserStore userStore) throws RemoteException {
        this.userStore = userStore;
    }

    public EmailStore() throws RemoteException {

    }

    /**
     * Sends Email to any valid recipients Method take in all values of an email
     * made by the client. The array of recipients is looped through. If the
     * user exists in the UserStore, then an Email is made and added to the
     * NEW_EMAILS Map of the recipient. If the user is currently logged in, then
     * push a notification to them. If the user does not exist, then add the
     * email address to the invalidUsers ArrayList. If the Invalid ArrayList is
     * not the same size as the recipient ArrayList, meaning at least one user
     * was valid, then add the email to the senders SENT Map. If all recipients
     * are invalid, then email will not be sent.
     *
     * @param sender
     * @param sendDate
     * @param subject
     * @param content
     * @param recipients
     * @return ArrayList
     */
    @Override
    public ArrayList sendEmail(String sender, String sendDate, String subject, String content, ArrayList<String> recipients) throws RemoteException {
        if (userStore.checkObserver(sender) != null) {
            ArrayList<String> invalidUserList = new ArrayList<>();
            synchronized (NEW_EMAILS) {
                for (String r : recipients) {
                    if (userStore.getUsers().containsKey(r)) {
                        Email email = new Email(sender, sendDate, subject, content, r);
                        NEW_EMAILS.get(r).add(email);
                        //if user is also in observer list, notify them
                        if (userStore.checkObserver(r) != null) {
                            try{
                                notifyObserver(email, userStore.checkObserver(r));
                            }catch(RemoteException e){
                                System.out.println("Removing observer.");
                                userStore.unregisterForCallback(r, userStore.checkObserver(r));
                            }
                            
                        }
                    } else {
                        invalidUserList.add(r);
                    }
                }
            }
            if (!(invalidUserList.size() == recipients.size())) {
                Email emailSent = new Email(sender, sendDate, subject, content, sender);
                SENT.get(sender).add(emailSent);
            }
            return invalidUserList;
        }
        return null;
    }

    /**
     * Get all unread Email. Method adds all Email from NEW_EMAILS of the
     * emailAddress given into an ArrayList. All NEW_EMAILS are relocated to
     * RECEIVED Map. NEW_EMAILS of that user are then cleared of all content.
     * Returns all the new email that was found.
     *
     * @param emailAddress
     * @return ArrayList
     */
    @Override
    public ArrayList<Email> getAllUnreadEmails(String emailAddress) {
        if (userStore.checkObserver(emailAddress) != null) {
            ArrayList<Email> emails = new ArrayList(NEW_EMAILS.get(emailAddress));
            for (int i = 0; i < emails.size(); i++) {
                RECEIVED.get(emailAddress).add(emails.get(i));
            }

            NEW_EMAILS.get(emailAddress).clear();
            return emails;
        }
        return null;
    }

    /**
     * Gets all Received email of a user.
     *
     * @param emailAddress
     * @return ArrayList
     */
    @Override
    public ArrayList<Email> getAllReceivedEmails(String emailAddress) {
        if (userStore.checkObserver(emailAddress) != null) {
            return RECEIVED.get(emailAddress);
        }
        return null;
    }

    /**
     * Gets all Spam email of a user.
     *
     * @param emailAddress
     * @return ArrayList
     */
    @Override
    public ArrayList<Email> getAllSpamEmails(String emailAddress) {
        if (userStore.checkObserver(emailAddress) != null) {
            return SPAM.get(emailAddress);
        }
        return null;
    }

    /**
     * Gets all Sent email of a user.
     *
     * @param emailAddress
     * @return ArrayList
     */
    @Override
    public ArrayList<Email> getAllSentEmails(String emailAddress) {
        if (userStore.checkObserver(emailAddress) != null) {
            return SENT.get(emailAddress);
        }
        return null;
    }

    /**
     * Gets all email of a user based on subject name. Method takes in the email
     * address of the user and their input of subject name. If the subject name
     * provided matches the Received email subject name, then add it to an
     * ArrayList. Returns all matching email.
     *
     * @param emailAddress
     * @return ArrayList
     */
    @Override
    public ArrayList<Email> getSpecificEmail(String emailAddress, String subject) {
        if (userStore.checkObserver(emailAddress) != null) {
            ArrayList<Email> emails = new ArrayList();
            ArrayList<Email> emailsOut = new ArrayList();
            emails = RECEIVED.get(emailAddress);

            for (int i = 0; i < emails.size(); i++) {
                if (emails.get(i).getSubject().equalsIgnoreCase(subject)) {
                    emailsOut.add(emails.get(i));
                }
            }

            return emailsOut;
        }
        return null;
    }

    /**
     * Deletes a specified email.
     *
     * @param emailAddress
     * @param selectedEmail
     * @return boolean
     */
    @Override
    public boolean deleteEmail(String emailAddress, int selectedEmail) {
        if (userStore.checkObserver(emailAddress) != null) {
            synchronized (RECEIVED) {
                ArrayList emails = RECEIVED.get(emailAddress);
                if (emails.get(selectedEmail) != null) {
                    if (selectedEmail >= 0 && selectedEmail < emails.size()) {
                        emails.remove(selectedEmail);
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Marks Email as spam.
     *
     * @param emailAddress
     * @param selectedEmail
     * @return
     */
    @Override
    public boolean markEmailSpam(String emailAddress, int selectedEmail) {
        if (userStore.checkObserver(emailAddress) != null) {
            String output;
            Email spamMail = RECEIVED.get(emailAddress).get(selectedEmail);
            synchronized (SPAM) {
                if (SPAM.get(emailAddress).add(spamMail)) {
                    RECEIVED.get(emailAddress).remove(selectedEmail);
                    return true;
                }
                return false;
            }
        }
        return false;

    }

    /**
     * Deletes all spam.
     *
     * @param emailAddress
     * @return String
     */
    @Override
    public String deleteAllSpam(String emailAddress) {
        if (userStore.checkObserver(emailAddress) != null) {
            synchronized (SPAM) {
                int spamSize = SPAM.get(emailAddress).size();
                SPAM.get(emailAddress).clear();
                return "Deleted " + spamSize + " spam email(s)";
            }
        }
        return null;

    }

    /**
     * Notifies any currently logged in users of a new email sent.
     *
     * @param newEmail
     * @param observer
     */
    private void notifyObserver(Email newEmail, RMIClientInterface observer) throws RemoteException {
        try {
            observer.newEmailNotification(newEmail);
        } catch (RemoteException e) {
            System.out.println("Exception occurred. Removing Observer.");
            userStore.unregisterForCallback(newEmail.getRecepiant(), observer);
        }
    }

    public void writeEmailToHashMap(String sender, String sendDate, String subject, String content, String receiver) {

        Email email = new Email(sender, sendDate, subject, content, receiver);
        SENT.computeIfAbsent(sender, k -> new ArrayList<>()).add(email);

        NEW_EMAILS.computeIfAbsent(receiver, k -> new ArrayList<>()).add(email);
    }

    public void initalizeHashMaps(String emailAddress) {
        ArrayList<Email> sentMails = new ArrayList();
        ArrayList<Email> revcivedMails = new ArrayList();
        ArrayList<Email> spamMails = new ArrayList();
        ArrayList<Email> newMails = new ArrayList();
        SENT.put(emailAddress, sentMails);
        RECEIVED.put(emailAddress, revcivedMails);
        SPAM.put(emailAddress, spamMails);
        NEW_EMAILS.put(emailAddress, newMails);

    }

    public HashMap<String, ArrayList<Email>> getNewMail() {
        return this.NEW_EMAILS;
    }

    public HashMap<String, ArrayList<Email>> getSpamMail() {
        return this.SPAM;
    }

    public HashMap<String, ArrayList<Email>> getRecEmails() {
        return this.RECEIVED;
    }

    public boolean ReadSpamFromFile(String emailAddress, Email spamToAdd) {
        if (SPAM.get(emailAddress).add(spamToAdd)) {
            return true;
        }

        return false;
    }

    public void writeInEmails(String emailAddr, Email emailToAdd) {
        RECEIVED.get(emailAddr).add(emailToAdd);
    }

    public void emailToAddSent(String emailAddr, Email emailToAdd) {
        SENT.get(emailAddr).add(emailToAdd);
    }

}
