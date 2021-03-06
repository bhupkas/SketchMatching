'''
    Simple socket server using threads
'''
import socket
import sys
#import thread
from thread import *
HOST = ''   # Symbolic name meaning all available interfaces
PORT = 8080 # Arbitrary non-privileged port
cnt = 0
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#Bind socket to local host and port
try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print('Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1])
    sys.exit()
         
print('Socket bind complete')
#Start listening on socket
s.listen(10)
print('Socket now listening')
#Function for handling connections. This will be used to create threads
def clientthread(conn,cnt):
    #Sending message to connected client
    #conn.send('Welcome to the server. Type something and hit enter\n') #send only takes string
    #infinite loop so that function do not terminate and thread do not end.
    fname = "img" + str(cnt) + ".jpg"
    f = open(fname,'wb')
    while True:
         
        #Receiving from client
        data = conn.recv(1024)
	f.write(data)
        if not data:
            break
     
    f.close();
	
    #came out of loop
    conn.close()

def clientthread2(conn):
    data = conn.recv(1024)
    if data == "send val":
	conn.send("1\n")
 
#now keep talking with the client
while 1:
    #wait to accept a connection - blocking call
    conn, addr = s.accept()
    cnt += 1
    print('Connected with ' + addr[0] + ':' + str(addr[1]))
    #start new thread takes 1st argument as a function name to be run, second is the tuple of arguments to the function.
    if cnt%3 == 0 :
	start_new_thread(clientthread2 ,(conn,))
    else:
    	start_new_thread(clientthread ,(conn,cnt,))
 
s.close()
