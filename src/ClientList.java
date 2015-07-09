import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Maintains a list of clients and provides custom functionality for adding,
 * removing, and searching the list for a client via ID
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 * 
 *            Redistribution and use with or without modification, are permitted
 *            provided that the following conditions are met:
 * 
 *            - the use is for academic purpose only - Redistributions of source
 *            code must retain the above copyright notice, this list of
 *            conditions and the following disclaimer. - Neither the name of
 *            Brahma Dathan or Sarnath Ramnath may be used to endorse or promote
 *            products derived from this software without specific prior written
 *            permission.
 * 
 *            The authors do not make any claims regarding the correctness of
 *            the code in this module and are not responsible for any loss or
 *            damage resulting from its use.
 */

public class ClientList implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Client> clients = new ArrayList<Client>();
    private static ClientList clientList;

    private ClientList(){
    	// Default private for singleton
    }
    
    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static ClientList instance() {
		if (clientList == null) {
		    return (clientList = new ClientList());
		} else {
		    return clientList;
		}
    }

    /**
     * Returns the client with ID matching 'ID' or null if not found
     * 
     * @param ID unique identifier to find client
     * @return the client that was found or null if client is not in clientList
     */
    public Client get(int ID) {
		Iterator<Client> iterator = clients.iterator();
		while (iterator.hasNext()) {
		    Client client = iterator.next();
		    if (client.getID() == ID) {
		    	return client;
		    }
		}
		return null;
    }

    /**
     * Removes client with ID from the list if present
     * 
     * @param ID unique identifier to find client
     * @return the client that was removed or null if not present
     */
    public Client remove(int ID) {
		Client client = this.get(ID);
		if (client != null) {
		    clients.remove(client);
		}
	
		return client;
    }

    /**
     * returns an iterator to the clientList
     * 
     * @return clientList's iterator
     */
    public Iterator<Client> iterator() {
    	return clients.iterator();
    }

    /**
     * Adds a client to clientList if not already present in the list
     * 
     * @param client Client to be added
     * @return
     *   The Client that was added, or null if it wasn't
     */
    public Client add(Client client) {
	
		if (this.get(client.getID()) == null) {
		    clients.add(client);
		    return client;
		}
		return null;
    }

    /**
     * Supports serialization
     * 
     * @param output the stream to be written to
     */
    public void writeObject(java.io.ObjectOutputStream output)
		    throws IOException {
		try {
		    output.defaultWriteObject();
		    output.writeObject(clientList);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
    }

    /**
     * Supports serialization
     * 
     * @param input the stream to be read from
     */
    public void readObject(java.io.ObjectInputStream input)
		    throws IOException, ClassNotFoundException {
		try {
		    input.defaultReadObject();
		    if (clientList == null) {
		    	clientList = (ClientList) input.readObject();
		    } else {
		    	input.readObject();
		    }
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
    }

    /**
     * Returns a string listing all of the clients and the total number of
     * clients
     */
    @Override
    public String toString() {
    	String newLine =  System.lineSeparator(); // So we aren't tied to Windows with /n
		StringBuilder builder = new StringBuilder();
		builder.append(this.clients.size() + " clients on file:");
		Iterator<Client> iterator = clientList.iterator();
		while (iterator.hasNext()) {
			builder.append(newLine);
		    builder.append(iterator.next().toString());
		}
	
		return builder.toString();
    }

}
