'''
left: running productsData script at 00:00

'''


import socket
import select 


clients_data = [] # map between client details to saved baskets

server_socket = socket.socket()
server_socket.bind((‘0.0.0.0’, 6666))
server_socket.listen(5) 

open_client_sockets = [] 
messages_recieved = []


while True: 
	if datetime.time.now() == time(): # when time is 00:00:00
		#need to: closing socket temporarily for requests while updating
		'''run readProductsData.js script #update products details'''
		#while script execution not done:

	rlist, wlist, xlist = select.select( [server_socket] + open_client_sockets, [], [] )
	for current_socket in rlist: 
		if current_socket is server_socket:
			(new_socket, address) = server_socket.accept()
			open_client_sockets.append(new_socket)
		else:
			data = current_socket.recv(16384); #16KB
			messages_recieved.append(current_socket,data);

			
	for message in messages_recieved:
		(socket,data) = message
		if data=="" or data=="end":
			open_client_sockets.remove(socket);
			print "connection with client closed";
		else:
			if socket in wlist:
				data_to_send = parse_data(data);
				socket.send(data_to_send);
				#socket.send("end")
				socket.close()
				
def parse_data(data): # data fromat: first line- query description. next lines: client details(&basket data if created)
	query = data.readline()
	username = data.readline()
	pas = data.readline()
	c_details = (username,pas)
	if query == 'registeration':
		clients_data.append((c_details,[]))
		return "registeration completed"
	if query == 'log-in':
		if clients_data.get(c_details) == None:
			return "details not exist in the system";
		else: 
			file = open('ProductsTextData.txt')
			products_details = file.read()
			return products_details
			#transfer productsDetails file
			
	if query == 'bring saved baskets':
		c_unit = clients_data.get(c_details)
		(c_det, bask_details) = c_unit
		return bask_details
			
	if query == 'add basket to saved':
		c_unit = clients_data.get(c_details)
		(c_det, bask_details) = c_unit
		bask_details.append(data.read())
		return "succesfully added new basket"
		
		return "unrecognized message"

			