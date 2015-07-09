/**
 * 
 * @author Brahma Dathan, Sarnath Ramnath, and Dan Hanson (Modifying)
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * 
 * This class implements the user interface for the Theater project. The
 * commands are encoded as integers using a number of static final variables. A
 * number of utility methods exist to make it easier to parse the input.
 * 
 */
public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Theater theater;
    private static boolean changesMade = false;
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int REMOVE_CLIENT = 2;
    private static final int LIST_CLIENTS = 3;
    private static final int ADD_CUSTOMER = 4;
    private static final int REMOVE_CUSTOMER = 5;
    private static final int ADD_CREDIT_CARD = 6;
    private static final int REMOVE_CREDIT_CARD = 7;
    private static final int LIST_CUSTOMERS = 8;
    private static final int ADD_SHOW = 9;
    private static final int LIST_SHOWS = 10;
    private static final int SAVE = 11;
    private static final int RETRIEVE = 12;
    private static final int HELP = 13;
    
    private static final String newLine = System.lineSeparator();

    /**
     * Made private for singleton pattern. Conditionally looks for any saved
     * data Otherwise, it gets a singleton Theater object.
     */
    private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
		    retrieve();
		} else {
		    theater = Theater.instance();
	
		}
    }

    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static UserInterface instance() {
		if (userInterface == null) {
		    return userInterface = new UserInterface();
		} else {
		    return userInterface;
		}
    }

    /**
     * Gets a token after prompting
     * 
     * @param prompt - whatever the user wants as prompt
     * @return - the token from the keyboard
     * 
     */
    private String getToken(String prompt) {
		do {
		    try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\r\f");
				if (tokenizer.hasMoreTokens()) {
				    return tokenizer.nextToken();
				}
		    } catch (IOException inputOutputException) {
		    	System.exit(0);
		    }
		} while (true);
    }

    /**
     * Queries for a yes or no and returns true for yes and false for no
     * 
     * @param prompt The string to be prepended to the yes/no prompt
     * @return true for yes and false for no
     * 
     */
    private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
		    return false;
		}
		return true;
    }

    /**
     * Converts the string to a number
     * 
     * @param prompt the string for prompting
     * @return the integer corresponding to the string
     * 
     */
    private int getNumber(String prompt) {
		do {
		    try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
		    } catch (NumberFormatException numberFormatExcecption) {
		    	System.out.println("Please input a number ");
		    }
		} while (true);
    }

    /**
     * Prompts for a date and gets a date object
     * 
     * @param prompt the prompt
     * @return the data as a Calendar object
     */
    private Calendar getDate(String prompt) {
		do {
		    try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
		    } catch (Exception exception) {
		    	System.out.println("Please input a date as MM/DD/YY");
		    }
		} while (true);
    }

    /**
     * Prompts for a command from the keyboard
     * 
     * @return a valid command
     * 
     */
    private int getCommand() {
		do {
		    try {
				int value = Integer.parseInt(getToken(newLine + "Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
				    return value;
				}
		    } catch (NumberFormatException nfe) {
		    	System.out.println("Enter a valid number");
		    }
		} while (true);
    }

    /**
     * Displays the help screen
     * 
     */
    private void help() {
	System.out
		.println("Enter a number between 0 and 13 as explained below:");
	System.out.println(EXIT + " to Exit");
	System.out.println(ADD_CLIENT + " to add a client");
	System.out.println(REMOVE_CLIENT + " to remove a client");
	System.out.println(LIST_CLIENTS + " to list all clients");
	System.out.println(ADD_CUSTOMER + " to add a customer");
	System.out.println(REMOVE_CUSTOMER + " to remove a customer");
	System.out.println(ADD_CREDIT_CARD + " to add a credit card");
	System.out.println(REMOVE_CREDIT_CARD + " to remove a credit card");
	System.out.println(LIST_CUSTOMERS + " to list all customers");
	System.out.println(ADD_SHOW + " to add a show");
	System.out.println(LIST_SHOWS + " to list all shows");
	System.out.println(SAVE + " to save data");
	System.out.println(RETRIEVE + " to  retrieve data");
	System.out.println(HELP + " for help");
    }

    /**
     * Method to be called for saving the Theater object. Uses the appropriate
     * Theater method for saving.
     * 
     */
    private void save() {
		if (Theater.save()) {
		    System.out.println(" The Theater has been successfully saved in the file TheaterData");
		} else {
		    System.out.println(" There has been an error in saving");
		}
    }

    /**
     * Method to be called for retrieving saved data. Uses the appropriate
     * Theater method for retrieval.
     * 
     */
    private void retrieve() {
		if (!changesMade) {
		    try {
				Theater tempTheater = Theater.retrieve();
				if (tempTheater != null) {
				    System.out.println("The Theater has been successfully retrieved from the file TheaterData");
				    theater = tempTheater;
				} else {
				    System.out.println("File doesn't exist; creating new Theater");
				    theater = Theater.instance();
				}
		    } catch (Exception missingClassException) {
		    	System.out.println("Class Not Found Exception");
		    }
		} else {
			System.out.println("You cannot retrieve data once changes to the current data model have been made");
		}
    }

    /**
     * Orchestrates the whole process. Calls the appropriate method for the
     * different functionalities.
     * 
     */
    private void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
		    switch (command) {
	
			    case ADD_CLIENT:
				addClient();
				break;
		
			    case REMOVE_CLIENT:
				removeClient();
				break;
		
			    case LIST_CLIENTS:
				listClients();
				break;
		
			    case ADD_CUSTOMER:
				addCustomer();
				break;
		
			    case REMOVE_CUSTOMER:
				removeCustomer();
				break;
		
			    case ADD_CREDIT_CARD:
				addCreditCard();
				break;
		
			    case REMOVE_CREDIT_CARD:
				removeCreditCard();
				break;
		
			    case LIST_CUSTOMERS:
				listCustomers();
				break;
		
			    case ADD_SHOW:
				addShow();
				break;
		
			    case LIST_SHOWS:
				listShows();
				break;
		
			    case SAVE:
				save();
				break;
		
			    case RETRIEVE:
				retrieve();
				break;
		
			    case HELP:
				help();
				break;
		    }
		}
        save();
    }

    /**
     * Method to be called for adding a client. Prompts the user for the
     * appropriate values and uses the appropriate Theater method for adding the
     * client.
     * 
     */
    private void addClient() {
		String name = getToken("Enter client name:");
		String address = getToken("Enter address:");
		String phone = getToken("Enter phone number:");
		
		Client result = theater.addClient(name, address, phone);
		if (result == null) {
		    System.out.println("Could not add client");
		} else {
		    changesMade = true;
		    System.out.println("The following client was added to the system:" + newLine + result);
		}
    }

    /**
     * Method to be called for Removing a client. Prompts the user for a client
     * ID to remove from the system and removes the client with the
     * corresponding client ID if it exists in the system
     */
    private void removeClient() {
		int clientID = getNumber("Enter client ID:");
		Client result = theater.removeClient(clientID);
		if (result == null) {
		    System.out.println("Client currently has a show scheduled or no clients with that ID");
		} else {
		    changesMade = true;
		    System.out.println("The following client was removed:\n" + result);
		}
    }

    /**
     * Method to be called for listing all clients. Displays all of the
     * theater's clients along with the clients' IDs, names, addresses, phone
     * numbers, and balances.
     */
    private void listClients() {
		Iterator<Client> iterator = theater.listClients();
		System.out.println("Clients:");
		while (iterator.hasNext()) {
		    System.out.println(iterator.next());
		}
    }

    /**
     * Method to be called for adding a customer. Prompts the user for name,
     * address, phone number, credit card number, and card expiration date
     */
    private void addCustomer() {

		String name = getToken("Enter the customer's name:");
		String address = getToken("Enter the customer's address:");
		String phoneNumber = getToken("Enter the customer's phone number:");
		String cardNumber = getToken("Enter the customer's credit card number:");
		Calendar expirationDate = getDate("Enter the expiration date:");
		
		try {
			Customer result = theater.addCustomer(name, address, phoneNumber, cardNumber, expirationDate.getTime());
			if (result == null) {
			    System.out.println("The customer could not be added to the system");
			} else {
			    changesMade = true;
			    System.out.println("The following customer was added to the system:" + newLine + result);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
    }

    /**
     * Method to be called for removing a customer from the system. Prompts the
     * user for a customer ID to remove from the system and removes a customer
     * with matching customer ID if it exists in the system
     */
    private void removeCustomer() {
		int customerID = getNumber("Enter customer ID:");
		Customer result = theater.removeCustomer(customerID);
		if (result == null) {
		    System.out.println("No customers with that ID");
		} else {
		    changesMade = true;
		    System.out.println("The following customer was removed:\n" + result);
		}
    }

    /**
     * Method to be called for adding a customer credit card. Prompts user for
     * customer ID and attempts to retrieve that customer. If the customer is
     * found system asks the user to confirm it is the correct customer and then
     * prompts the user for credit card number and expiration date
     */
    private void addCreditCard() {
		int customerID = getNumber("Enter the customer ID:");
		Customer result = theater.getCustomer(customerID);
		if (result == null) {
		    System.out.println("No customers with that ID");
		} else {
		    boolean correctCustomer = yesOrNo("Is this the correct customer?" + newLine + result + newLine);
		    if (correctCustomer) {
				String accountNumber = getToken("Enter the credit card number:");
				Calendar expirationDate = getDate("Enter the expiration date as MM/DD/YY:");
				// conform to MM/YY format
				expirationDate.set(Calendar.DAY_OF_MONTH, 1);
				result = theater.addCreditCard(customerID, accountNumber, expirationDate.getTime());
				if (result == null) {
				    System.out.println("Credit card could not be added");
				} else {
				    changesMade = true;
				    System.out.println("Credit card successfully added:" + newLine + result);
				}
		    }
		}
    }

    /**
     * Method for removing a customer's credit card. Prompts user for customer
     * ID and retrieves customer associated with that ID. System asks user to
     * confirm the correct customer has been found and prompts for credit card
     * number and date. The system attempts to remove the card, if there is more
     * than one card on file for the user.
     */
    private void removeCreditCard() {
		int customerID = getNumber("Enter the customer ID:");
		Customer result = theater.getCustomer(customerID);
		if (result == null) {
		    System.out.println("No customers with that ID");
		} else {
		    boolean confirmation = yesOrNo("Is this the correct customer?\n" + result);
		    if (confirmation) {
				String accountNumber = getToken("Enter the credit card number to be removed:");
				CreditCard removedCard = theater.removeCreditCard(customerID,accountNumber);
				if (removedCard == null) {
				    System.out.println("There was an error, the card was not removed. Is this the only card on file?");
				} else {
				    changesMade = true;
				    System.out.println("the following card was removed successfully:" + newLine + removedCard);
				}
		    }
		}
    }

    /**
     * Method for listing all customers. Lists all of the customers and their
     * information.
     */
    private void listCustomers() {
		Iterator<Customer> iterator = theater.listCustomers();
		System.out.println("Customers:");
		while (iterator.hasNext()) {
		    System.out.println(iterator.next());
		}
    }

    /**
     * Method for adding a show. Prompts the user for a client ID, show name,
     * start date, and end date. The system attempts to add the show for the
     * given duration.
     */
    private void addShow() {
		int clientID = getNumber("Enter the client ID:");
		String name = getToken("Enter the name of the show:");
		Calendar startDate = getDate("Enter your desired start date as MM/DD/YY:");
		Calendar endDate = getDate("Enter your desired end date as MM/DD/YY:");
		while (startDate.after(endDate)) {
		    System.out.println("Start date must be before end date");
		    startDate = getDate("Enter your desired start date as MM/DD/YY:");
		    endDate = getDate("Enter your desired end date as MM/DD/YY:");
		}
		try{
			Show result = theater.addShow(name, startDate.getTime(), endDate.getTime(), clientID);
			if (result == null) {
			    System.out.println("That client could not be found");
			} else {
			    changesMade = true;
			    System.out.println("The following show was added:" + newLine + result);
			}
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
    }

    /**
     * Method for listing shows. Lists all of the currently scheduled shows
     */
    private void listShows() {
		Iterator<Show> iterator = theater.listShows();
		System.out.println("Shows:");
		while (iterator.hasNext()) {
		    System.out.println(iterator.next());
		}
    }

    /**
     * The method to start the application. Simply calls process().
     * 
     * @param args not used
     */
    public static void main(String[] args) {
    	UserInterface.instance().process();
    }
}