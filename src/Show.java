import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Corin Dennison
 * 
 * A class to represent shows for the theater program.  Has a name, start and end dates, 
 * and an ID associated with the client that runs it.  
 *
 */
public class Show implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Date startDate;
	private Date endDate;
	private int clientID;
	
	/**
	 * Creates a new show.  Note that shows with an end date the same or earlier than the
	 * start date are not valid and will throw an IllegalArgument exception.
	 * @param name
	 *   The name of the show
	 * @param startDate
	 *   The date this show will begin running
	 * @param endDate
	 *   The date this show will stop running
	 * @param clientID
	 *   The ID of the client that runs this show
	 * @throws IllegalArgumentException
	 *   If the dates given are invalid in that end is not after start
	 */
	public Show(String name, Date startDate, Date endDate, int clientID) throws IllegalArgumentException {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clientID = clientID;
		
		if (startDate.compareTo(endDate) >= 0){
			throw new IllegalArgumentException("Start date " + startDate + " must come before end date " + endDate);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the clientID
	 */
	public int getClientID() {
		return clientID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		if (startDate.compareTo(endDate) >= 0){
			throw new IllegalArgumentException("Start date " + startDate + " must come before end date " + endDate);
		}
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		if (startDate.compareTo(endDate) >= 0){
			throw new IllegalArgumentException("Start date " + startDate + " must come before end date " + endDate);
		}
	}
	
	/**
	 * Returns a string representing this show in the format:
	 * Name:My Show, Start Date:01-Jan-2015, End Date:01-Jan-2015, Client ID:123456
	 */
	@Override
	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String startString = dateFormat.format(startDate);
		String endString = dateFormat.format(endDate);
		return "Name:"+ name + ", Start Date:" + startString + ", End Date:" + endString + ", Client ID:" + clientID;
	}
	
}
