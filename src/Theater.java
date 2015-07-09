import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Theater implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private CustomerList customers;
	private ClientList clients;
	private Schedule schedule;
    private static Theater theater;
	
    /**
     * Constructor
     */
     private Theater() {
    	 customers = CustomerList.instance();
    	 clients = ClientList.instance();
    	 schedule = Schedule.instance();
     }
    
     public static Theater instance(){
    	 if (theater == null){
    		 IDServer.instance();
    		 theater = new Theater();
    	 }
    	 return theater;
     }
     
     /**
      * Add Client to the client collection class
      * @param name String
      * @param address String
      * @param phoneNumber String
      * @return The Client that was successfully added, or null if it wasn't
      */
     public Client addClient(String name, String address, String phoneNumber) {
    	 return clients.add(new Client(name, address, phoneNumber));
     }
     
     /**
      * Remove client
      * Searches by id
      * Client List returns the client if could be removed or not
      * @param clientID int
      * @return The Client that has been successfully removed, or null if it hasn't 
      */
     public Client removeClient(int clientID) {
    	 if (schedule.scheduled(clientID)){
    		 return null;
    	 }
         return clients.remove(clientID);
     }
     
     /**
      * list all currently added clients
      * @return An Iterator<Client> for the list of clients
      */
     public Iterator<Client> listClients() {
    	 return clients.iterator();
     }
     
     /**
      * Adds a customer
      * @param name Name of the Customer
      * @param address Address of the Customer
      * @param phone Phone number of the Customer
      * @return The Customer that was added, or null if it wasn't
      */
     public Customer addCustomer(String name, String address, String phone, String cardNumber, Date expiration) {
	 if(isCardOnFile(cardNumber)){
	     System.out.println("Card is already on file");
	     return null;
	 }else{
    	 Customer customer = new Customer(name, address, phone, cardNumber, expiration);
    	 return customers.add(customer);
	 }
     }
     
     /**
      * Removes a customer
      * @param ID The ID of the customer to be removed
      * @return The Customer that was removed, or null if it wasn't
      */
     public Customer removeCustomer(int ID){
    	return customers.remove(ID); 
     }
     
     /**
      * checks whether the account number is already stored by one of the customers
      * @param accountNumber the account number we are looking for
      * @return
      */
     public boolean isCardOnFile(String accountNumber){
	 Iterator<Customer> customerIterator = customers.iterator();
	 while(customerIterator.hasNext()){
	     Iterator<CreditCard> cardIterator  = customerIterator.next().listCards();
	     while(cardIterator.hasNext()){
		 CreditCard card = cardIterator.next();
		 if(card.getAccountNumber().equalsIgnoreCase(accountNumber)){
		     return true;
		 }
	     }
	     
	 }
	 return false;
     }
     
     /**
      * Add a credit card to a customer
      * @param customerId The ID of the customer who gets a new card
      * @param accountNumber The account number of the new credit card
      * @param expiration THe expiration date of the new credit card
      * @return The CreditCard that was added, or null if it wasn't
      */
     public Customer addCreditCard(int customerId, String accountNumber, Date expiration) {
	 
                
         if(isCardOnFile(accountNumber)){
             System.out.println("Card is already on file");
             return null;
         }else{
             Customer customer = customers.get(customerId);  
             return customer.addCard(accountNumber, expiration);
         }
     }
     
     /**
      * Removed a credit card from a customer
      * @param customerId The ID of the customer who is losing a credit card
      * @param accountNumber The card number of the credit card to be removed
      * @return CreditCard The credit card that was removed, or null if it wasn't
      */
     public CreditCard removeCreditCard(int customerId, String accountNumber) {
    	 Customer customer = customers.get(customerId);
    	 return customer.removeCard(accountNumber);
     }
     
     /**
      * Get the entire list of customers
      * @return An Iterator<Customer> containing the list of Customers
      */
     public Iterator<Customer> listCustomers(){
    	 return customers.iterator();
     }
	
     /**
      * Schedule a new show
      * @param name The name of the show
      * @param startDate The date the show will begin running
      * @param endDate The date the show will stop running
      * @param clientId The ID of the CLient who is running the show
      * @return The show that has been scheduled, or null if it wasn't
      */
	public Show addShow(String name, Date startDate, Date endDate, int clientId) {
		   //Check if client id exists
        Client client = clients.get(clientId);
        if(client == null) {
        	return null;
        }

       	return schedule.add(new Show(name, startDate, endDate, clientId));
    }
	
	/**
	 * List all shows
	 * @return An Iterator<Show> containing all the shows
	 */
	public Iterator<Show> listShows() {
		return schedule.iterator();
	}

	/**
	 * Get a customer from the list
	 * @param ID THe ID of the Customer to get
	 * @return THe Customer that was found, or null if it wasn't
	 */
	public Customer getCustomer(int ID){
		return customers.get(ID);
	}

	/**
	 * Serializes the Library object
	 * @return true if the data could be saved
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("TheaterData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(theater);
			output.writeObject(IDServer.instance());
			output.close();
			return true;
		} catch(IOException ioe) {
			System.out.println("Error saving Theater");
			return false;
		}
	}

	/**
	 * Retrieves a deserialized version of the Theater from disk
	 * @return a Theater object
	 */
	public static Theater retrieve() {
		try {
			FileInputStream file = new FileInputStream("TheaterData");
			ObjectInputStream input = new ObjectInputStream(file);
			theater = (Theater) input.readObject();
			IDServer.retrieve(input);
			return theater;
		} catch(IOException ioe) {	
			return null;
		} catch(ClassNotFoundException cnfe) {
			return null;
		}
	}

	/**
	 * Writes the object to the output stream
	 * @param output the stream to be written to
	 */
	private void writeObject(java.io.ObjectOutputStream output) {
		try {
			output.defaultWriteObject();
			output.writeObject(theater);
		} catch(IOException ioe) {
			System.out.println("Error serializing");
		}
	}
	/**
	 * Reads the object from a given stream
	 * @param input the stream to be read
	 */
	private void readObject(java.io.ObjectInputStream input) {
		try {
			input.defaultReadObject();
			if (theater == null) {
				theater = (Theater) input.readObject();
			} else {
				input.readObject();
			}
		} catch(IOException ioe) {
			System.out.println("Error deserializing");
		} catch(Exception e) {
			System.out.println("Error deserializing");
		}
	}





}
