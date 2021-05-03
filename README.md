# RMIEmailServiceServer
# API Documentation

**This page contains detailed API reference Documentation.**

The building blocks of this email application consist of both server- side and client-side components to handle requests and responses.

To comprehend and facilitate easy usage, the client-side components are built using JavaFX.

![alt text](https://github.com/tjohora/RMIEmailServiceServer/blob/master/EmailRMIDesign.png "Logo Title Text 1")

1. **Creating a request**

on the client-side, a request sent to the server-side is handled by the request handler methods which communicate to the server-side through port. Java Remote Method Invocation (RMI) handles connectivity.
```java
 String registryPath = "rmi://localhost:" + portNum;
            String EmailLabel = "/EmailService";
            String UserLabel = "/UserService";

            emailService = (RMIEmailInterface) Naming.lookup(registryPath + EmailLabel);
            userService = (RMIUserInterface) Naming.lookup(registryPath + UserLabel);
```
on the server-side exist the EmailLabel service and the UserLabel service available on the provided port number and the rmi url.

1. **The RMI Interfaces**

These interfaces define the actions and what type of request can be sent to the services on the server-sides, The RMIEmailInterface contains methods that handles:

```java
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
```

these interfaces stand as proxy that tells the server what is requested and what needs to be returned, these requests and responses are controlled.

On the RMIUserInterface, the interface handles registration and login.

```java
public interface RMIUserInterface extends Remote {
    //methods handles authentications and authorization remotetly
    public boolean register(String emailAddress, String password) throws RemoteException;
      //handles login and logout
    public boolean login(String emailAddress, String password, RMIClientInterface client) throws RemoteException;
    public String logout(String emailAddress, RMIClientInterface client) throws RemoteException;
    //call_backs notifies users of new emails when the logged in
    public boolean registerForCallback(String emailAddress, RMIClientInterface client) throws RemoteException;
    public boolean unregisterForCallback(String emailAddress, RMIClientInterface client) throws RemoteException;
}
```

