import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * 
 * Generates customer IDs & client IDs.
 * 
 * @author Brahma Dathan, Sarnath Ramnath, and Dan Hanson(Modifying)
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

public class IDServer implements Serializable {

    private static final long serialVersionUID = 1L;
    private int customerIdCounter;
    private int clientIdCounter;

    private static IDServer server;

    /*
     * Private constructor for singleton pattern
     */
    private IDServer() {
		customerIdCounter = 1;
		clientIdCounter = 1;
    }

    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static IDServer instance() {
		if (server == null) {
		    return (server = new IDServer());
		} else {
		    return server;
		}
    }

    /**
     * Returns the next valid customer ID number
     * 
     * @return ID of the customer
     */
    public int getCustomerID() {
    	return customerIdCounter++;
    }

    /**
     * Returns the next valid client ID number
     * 
     * @return id of the client
     */
    public int getClientID() {
    	return clientIdCounter++;
    }

    /**
     * String representation of the ID server
     * 
     */
    @Override
    public String toString() {
		return ("ID Server\n" + "Customer ID:" + customerIdCounter
			+ "\nClient ID:" + clientIdCounter);
    }

    /**
     * Retrieves the server object
     * 
     * @param input inputstream for deserialization
     */
    public static void retrieve(ObjectInputStream input) {
		try {
		    server = (IDServer) input.readObject();
		} catch (IOException IoException) {
		    System.out.println("Error deserializing: I/O Exception");
		} catch (ClassNotFoundException noClassException) {
		    System.out.println("Error deserializing: Class not found");
		}
    }

    /*
     * Supports serialization
     * 
     * @param output the stream to be written to
     */
    private void writeObject(java.io.ObjectOutputStream output) {
		try {
		    output.defaultWriteObject();
		    output.writeObject(server);
		} catch (IOException IoException) {
		    System.out.println("Error in serialization: I/O Exception");
		}
    }

    /*
     * Supports serialization
     * 
     * @param input the stream to be read from
     */
    private void readObject(java.io.ObjectInputStream input)
    		throws IOException, ClassNotFoundException{
		try {
		    input.defaultReadObject();
		    if (server == null) {
		    	server = (IDServer) input.readObject();
		    } else {
		    	input.readObject();
		    }
		} catch (ClassNotFoundException noClassException) {
		    System.out.println("Error in deserialization: Class not found");
	
		} catch (IOException IoException) {
		    System.out.println("Error in deserialization: I/O Exception");
		}
    }
    
}