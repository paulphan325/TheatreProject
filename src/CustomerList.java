import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mohamad Hussain
 * 
 * Maintains a list of customers and provides custom functionality for adding,
 * removing, and searching the list for a customer via ID
 * 
 */

public class CustomerList implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Customer> customers = new ArrayList<Customer>();
    private static CustomerList customerList;

    private CustomerList(){
    	// Default private for singleton
    }
    
    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static CustomerList instance() {
		if (customerList == null) {
		    return (customerList = new CustomerList());
		} else {
		    return customerList;
		}
    }

    /**
     * Returns the customer with ID matching 'ID' or null if not found
     * 
     * @param ID unique identifier to find customer
     * @return the customer that was found or null if customer is not in customerList
     */
    public Customer get(int ID) {
		Iterator<Customer> iterator = customers.iterator();
		while (iterator.hasNext()) {
		    Customer customer = (Customer) iterator.next();
		    if (customer.getID() == ID) {
		    	return customer;
		    }
		}
		return null;
    }

    /**
     * Removes customer with ID from the list if present
     * 
     * @param ID unique identifier to find customer
     * @return the customer that was removed or null if not present
     */
    public Customer remove(int ID) {
		Customer customer = this.get(ID);
		if (customer != null) {
		    customers.remove(customer);
		}
		return customer;
    }

    /**
     * returns an iterator to the customerList
     * 
     * @return customerList's iterator
     */
    public Iterator<Customer> iterator() {
    	return customers.iterator();
    }

    /**
     * Adds a Customer to customerList if not already present in the list
     * 
     * @param customer Customer to be added
     * @return The customer that was added, or null if it wasn't
     */
    public Customer add(Customer customer) {

		if (this.get(customer.getID()) == null) {
		    customers.add(customer);
		    return customer;
		}
		return null;
    }

    /**
     * Supports serialization
     * 
     * @param output the stream to be written to
     */
    private void writeObject(java.io.ObjectOutputStream output)
		    throws IOException {
		try {
		    output.defaultWriteObject();
		    output.writeObject(customerList);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
    }

    /**
     * Supports serialization
     * 
     * @param input the stream to be read from
     */
    private void readObject(java.io.ObjectInputStream input)
		    throws IOException, ClassNotFoundException {
		try {
		    input.defaultReadObject();
		    if (customerList == null) {
		    	customerList = (CustomerList) input.readObject();
		    } else {
			input.readObject();
		    }
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
    }

    /**
     * Returns a string listing all of the customers and the total number of
     * customers
     */
    @Override
    public String toString() {
    	String newLine =  System.lineSeparator(); // So we aren't tied to Windows with /n
    	
		StringBuilder builder = new StringBuilder();
		builder.append(this.customers.size() + " customers on file:");
		Iterator<Customer> iterator = customerList.iterator();
		
		while (iterator.hasNext()) {
			builder.append(newLine);
		    builder.append(iterator.next().toString());
		}
		return builder.toString();
    }

}

