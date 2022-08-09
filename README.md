# Client-Server-Networking
## Description
Set of reusable classes for implementing a general client-server model for connections over sockets, and to build a chat application to demonstrate how the classes can be used.  

Use the abstract Client and server classes are to create custome client-server behaviours. They implement the core features such as creating and accepting connections. Users can then implement methods to decide what the client and server should do during certain events such as when a packet is recieved.

## Features
NetServer abstract class for starting a server that listens for incoming connections.

NetClient abstarct class for starting a client and establishing a conenction to a server.

NetPacket a non-abstarct class that is used to pass messages between client and server.

## Installation & Usage
To create a simple client-server model across sockets for simple applications.

To use, include the NetClient, NetServer, and NetPacket classes in your project files, then create 2 classes, where one implements NetClient and the other NetServer.

Implement the abstract methods to create the behaviours for your model, then launch. 

## Acknowledgements
The AIML bot used is from program-ab from ALICE A.I. Foundation: https://code.google.com/archive/p/program-ab/
