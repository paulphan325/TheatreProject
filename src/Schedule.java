import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Calendar;

/**
 * 
 * @author Corin Dennison
 * A singleton container class to hold Show objects for the Theater program.
 * Also has some intelligence to do some minor scheduling so shows won't overlap.
 */
public class Schedule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Show> shows = new ArrayList<Show>();
	private static Schedule schedule;
	
	private Schedule(){
		// Default constructor is private
	}
	
	/**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
	public static Schedule instance(){
		if (schedule == null){
			return (schedule = new Schedule());
		} else {
			return schedule;
		}
	}
	
	/**
	 * Query this Schedule to see if a specific client has any shows scheduled
	 * now or in the future.
	 * @param clientID
	 *   The ID of the client whose shows we want to query
	 * @return
	 *   True if there are any shows scheduled now or in the future for the client
	 */
	public boolean scheduled(int clientID){
		Iterator<Show> iterator = shows.iterator();
		Date rightNow = Calendar.getInstance().getTime();
		
		while (iterator.hasNext()){
			Show show = iterator.next();
			// If this show belongs to the right client and is scheduled sometime in the future (or present)
			if(show.getClientID() == clientID && show.getEndDate().compareTo(rightNow) >= 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Queries the Schedule to see if any shows overlap with given start and end dates.
	 * @param start
	 *   The start date to query against
	 * @param end
	 *   The end date to query against
	 * @return
	 *   Null if there are no shows that conflict with the given dates. Otherwise the
	 *   first conflicting show discovered is returned.  
	 */
	public Show anyShowScheduled(Date start, Date end){
		Iterator<Show> iterator = shows.iterator();
		
		while (iterator.hasNext()){
			Show show = iterator.next();
			Date showStart = show.getStartDate();
			Date showEnd = show.getEndDate();
			
			// if param start is later than show end or param end is before show start then there is no conflict
			// otherwise there is a conflict
			if (!(start.compareTo(showEnd) > 0 || end.compareTo(showStart) < 0)){
				return show;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns an iterator to iterate over the list of shows in this Schedule
	 * @return
	 */
	public Iterator<Show> iterator(){
		return shows.iterator();
	}
	
	/**
	 * Adds a show to this Schedule.  If it conflicts with a show already in the
	 * Schedule an IllegalArgumentEsception will be thrown
	 * @param show
	 *   The show to be added
	 * @return
	 *   The show that was added
	 * @throws IllegalArgumentException
	 *   If the supplied show has a scheduling conflict with another show.
	 */
	public Show add(Show show){
		Show conflict = anyShowScheduled(show.getStartDate(), show.getEndDate());
		if(conflict == null){
			shows.add(show);
			return show;
		}
		else{
			throw new IllegalArgumentException("The show could not be added because it conflicts with show [" + conflict + "]");
		}
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
		    output.writeObject(schedule);
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
		    if (schedule == null) {
		    	schedule = (Schedule) input.readObject();
		    } else {
		    	input.readObject();
		    }
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
    }
    
    /**
     * Returns a string listing all of the shows and the total number of
     * shows
     */
    @Override
    public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.shows.size() + " shows on file:\n");
		Iterator<Show> iterator = shows.iterator();
		while (iterator.hasNext()) {
		    builder.append(iterator.next().toString());
		}
	
		return builder.toString();
    }
}
