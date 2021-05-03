# RMIEmailServiceServer
# API Documentation

**This page contains detailed API reference Documentation.**

The building blocks of this email application consist of both server- side and client-side components to handle requests and responses.

To comprehend and facilitate easy usage, the client-side components are built using JavaFX.

1. **Creating a request**

on the client-side, a request sent to the server-side is handled by the request handler methods which communicate to the server-side through port. Java Remote Method Invocation (RMI) handles connectivity.

String registryPath = &quot;rmi://localhost:&quot; + portNum;

String EmailLabel = &quot;/EmailService&quot;;

String UserLabel = &quot;/UserService&quot;;

emailService = (RMIEmailInterface) Naming.lookup(registryPath + EmailLabel);

userService = (RMIUserInterface) Naming.lookup(registryPath + UserLabel);

on the server-side exist the EmailLabel service and the UserLabel service available on the provided port number and the rmi url.

1. **The RMI Interfaces**

These interfaces define the actions and what type of request can be sent to the services on the server-sides, The RMIEmailInterface contains methods that handles:

sendEmail(String sender, String sendDate, String subject, String content, ArrayList\&lt;String\&gt; recipients) throws RemoteException;

getAllUnreadEmails(String emailAddress) throws RemoteException.

getAllReceivedEmails(String emailAddress) throws RemoteException.

getAllSpamEmails(String emailAddress) throws RemoteException.

getAllSentEmails(String emailAddress) throws RemoteException.

getSpecificEmail(String emailAddress, String subject) throws RemoteException;

deleteEmail(String emailAddress, int selectedEmail) throws RemoteException;

markEmailSpam(String emailAddress, int selectedEmail) throws RemoteException;

deleteAllSpam(String emailAddress) throws RemoteException;

these interfaces stand as proxy that tells the server what is requested and what needs to be returned, these requests and responses are controlled.
