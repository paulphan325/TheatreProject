import java.io.Serializable;

/**
 * Represents a single client
 * 
 * @author Dan Hanson
 * 
 */
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private int ID;
    private long balance = 0; // In cents. It's cleaner than using a double

    /**
     * Simple creation of a Client.  ID is generated automatically from the IDServer class
     * 
     * @param name client's name
     * @param address client's address
     * @param phone client's phone number
     */
    public Client(String name, String address, String phone) {
		this.setName(name);
		this.setAddress(address);
		this.setPhone(phone);
		this.ID = IDServer.instance().getClientID();
    }

    /**
     * @return the client's ID
     */
    public int getID() {
    	return ID;
    }

    /**
     * @return the client's name
     */
    public String getName() {
    	return name;
    }

    /**
     * @return the client's address
     */
    public String getAddress() {
    	return address;
    }

    /**
     * @return the client's phone number
     */
    public String getPhone() {
    	return phone;
    }

    /**
     * @param name the client's name to set
     */
    public void setName(String name) {
    	this.name = name;
    }

    /**
     * @param address the client's address to set
     */
    public void setAddress(String address) {
    	this.address = address;
    }

    /**
     * @param phone the client's phone number to set
     */
    public void setPhone(String phone) {
    	this.phone = phone;
    }

    /**
     * Returns a string with all of the client's data
     */
    @Override
    public String toString() {
		return "Name:" + name + ", Address:" + address + ", Phone Number:"
			+ phone + ", ID:" + ID;
    }
}
