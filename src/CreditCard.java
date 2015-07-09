
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreditCard implements Serializable{

	private static final long serialVersionUID = 1L;
	private Date expiration;
	private String accountNumber;
	
	/**
	 * Constructor
	 * @param accountNumber The account number for the credit card
	 * @param expiration The expiration date of the credit card
	 */
	public CreditCard(String accountNumber, Date expiration){
		this.expiration = expiration;
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the expiration
	 */
	public Date getExpiration() {
		return expiration;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	
	/** 
	 * Returns a string representation of this credit card
	 */
	@Override
	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yy");
		String expirationString = dateFormat.format(expiration);
		return "Card Number:" + accountNumber + ", Expiration Date:" + expirationString;
	}
}
