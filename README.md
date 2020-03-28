# DNS-Simulator
Simulation of DNS server that responds to Client and Administrator requests.
Server can be accesed via Putty: https://www.putty.org/

Client:

Can only use LOOKUP command. 
Example: LOOKUP www.google.com


Administator:
Can use ADD and REMOVE command.

HASH is SHA256 Encryption

Hash is made of -> adminAction + adminActionDomain + adminActionIp + secretKey; 

ADD-> adminID ADD domain ip HASH

Example: Ajaks ADD singidunum.ac.rs 78.44.104.51 302a45bbf2d509adf8e0376d43b667b2aa00e8b284b85115008a6be13c722a9c

REMOVE -> adminId REMOVE domain HASH

Example: Ajaks REMOVE aaaa ada83f88d94e585502a07aebb084925621097a8cf189be370669b3865662f1db

Didnt spent lots of time on this project. Expect to bump into bugs often
