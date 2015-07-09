import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a single customer
 * 
 * @author Mohamad Hussain
 * 
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private List<CreditCard> creditCards;
    private int ID;
  
    /**
     *  creation of a Customer
     * 
     * @param name customer's name
     * @param address customer's address
     * @param phone customer's phone number
     * @param ID unique identifier
     */
    public Customer(String name, String address, String phone, String creditCardNumber, Date expiryDate) {
		this.setName(name);
		this.setAddress(address);
		this.setPhone(phone);
		this.creditCards = new ArrayList<CreditCard>();
		if (addCard(creditCardNumber, expiryDate) == null){
			throw new IllegalArgumentException("The credit card has already expired");
		}
		ID = IDServer.instance().getCustomerID();
    } 

    /**
     * Creates and adds a credit card to this customer.  Cards that have already expired will not be allowed.
     * @param creditCardNumber
     *   The number of the credit card
     * @param expiryDate
     *   The expiration date of the credit card
     * @return 
     *   The Customer that had a card added, or null if it wasn't
     */
    public Customer addCard(String creditCardNumber, Date expiryDate) throws IllegalArgumentException {
    	// Check if its already expired
    	Date rightNow = Calendar.getInstance().getTime();
    	System.out.println("now:" + rightNow);
    	System.out.println("exp:" + expiryDate);
		if (expiryDate.compareTo(rightNow) <= 0){
			return null;
		}
		
		// Add the card
    	CreditCard card = new CreditCard(creditCardNumber, expiryDate);
    	this.creditCards.add(card);
    	return this;
    }
    
    /**
     * Removed a credit card from the customer.
     * @param creditCardNumber
     *   The number of the card to be removed
     * @return
     *   TheCreditCard that was removed, or null if a card with the supplied number was not found.
     */
    public CreditCard removeCard(String creditCardNumber) {
    	Iterator<CreditCard> iterator = listCards();
    	CreditCard card;
    	if(this.creditCards.size()==1){
    	    return null;
    	}
    	while (iterator.hasNext()){
    		card = iterator.next();
    		if (card.getAccountNumber().equals(creditCardNumber)){
    			creditCards.remove(card);
    			return card;
    		}
    	}
    	return null;
    }
    
    /**
     * Gets the iterator for the list of credit cards this customer owns
     * @return
     *   The iterator for all this customer's credit cards
     */
    public Iterator<CreditCard> listCards(){
    	return creditCards.iterator();
    }
    
    /**
     * All getters properties to get the attributes of the class object
     */
    
    /**
     * @return the customer's ID
     */
    public int getID() {
    	return ID;
    }

    /**
     * @return the customer's name
     */
    public String getName() {
    	return name;
    }

    /**
     * @return the customer's address
     */
    public String getAddress() {
    	return address;
    }

    /**
     * @return the customer's phone number
     */
    public String getPhone() {
    	return phone;
    }
    
    /**
     * All Setter properties to set the attributes of the class object
     * 
     */

    /**
     * @param name the customer's name to set
     */
    public void setName(String name) {
    	this.name = name;
    }

    /**
     * @param address the customer's address to set
     */
    public void setAddress(String address) {
    	this.address = address;
    }

    /**
     * @param phone the customer's phone number to set
     */
    public void setPhone(String phone) {
    	this.phone = phone;
    }
    

    /**
     * Returns a string with all of the customer's data
     */
    @Override
    public String toString() {
    	String newLine = System.lineSeparator();
    	StringBuilder answer = new StringBuilder();
    	
    	answer.append("Name:" + name + ", Address:" + address + ", Phone Number:"
    			+ phone + ", ID:" + ID);
    	answer.append(newLine + "  Credit Cards:");
    	
    	Iterator<CreditCard> iterator = listCards();
    	while(iterator.hasNext()){
    		answer.append(newLine + "  " + iterator.next());
    	}
    	
    	return answer.toString();
    }
}

