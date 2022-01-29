package com.GREENWORKS.eco.data;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/***
 * The parent abstract class for PinData. This class will not be directly instantiated
 * but rather its behavior will be instantiated through its children. This is a data 
 * class that is meant to be the representatation of the map pins that are displayed
 * on the UI. 
 */
@MappedSuperclass
public abstract class Pin implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	@Column(name="icon_id")
	protected String iconId;
	@Column(name="location_name")
    protected String locationName;
	@Column(name="location_address")
    protected String locationAddress;
	@Column(name="coordinates")
    protected String coordinates;
	@Column(name="content")
    protected String content; 
	@Column(name="start_date")
    protected String startDate;
	@Column(name="end_date")
    protected String endDate;
    
	private static final long serialVersionUID = 1L;
	
    /***
     * Zero parameter constructor. 
     */
    public Pin() { }
    
    
    
	public Pin(int id, String iconId, String startDate, String endDate, String locationName,
			String locationAddress, String coordinates, String content) {
		super();
		this.id = id;
		this.iconId = iconId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.locationName = locationName;
		this.locationAddress = locationAddress;
		this.coordinates = coordinates;
		this.content = content;
	}



	/***
	 * This method adds slashes to a String to preserve the backslashes in textual entries. 
	 * @param s The String that will be modified. 
	 * @return A String that has had additional backslashes added. 
	 */
    public String addSlashes(String s)
	{
        s = s.replaceAll("\\\\", "\\\\\\\\");
        s = s.replaceAll("\\n", "\\\\n");
        s = s.replaceAll("\\r", "\\\\r");
        s = s.replaceAll("\\00", "\\\\0");
        s = s.replaceAll("'", "\\\\'");
        return s;
    }

	/***
	 * This method removes HTML tags from a String. It removes the tags <> and what
	 * is contained within the tags. 
	 * @param s The String that will be modified. 
	 * @return A modified String that will have no HTML tags. 
	 */

	public String removeTags(String s)
	{
		String noHTMLString = s.replaceAll("\\<.*?\\>", "");
		return noHTMLString;
	}

	/***
	 * This method executes the removeTags() and the addSlashes() methods consecutively. 
	 * @param s The String that will be modified.
	 * @return A modified String that will have no HTML tags and its backslashes preserved. 
	 */
	public String cleanInput(String s)
	{
		if(s != null) {
			String returnS = addSlashes(removeTags(s));
			return returnS;
		}
		return "";
	}

	/***
	 * Accessor method for the id instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */
	public int getId() {
		return id;
	}

	/***
	 * Mutator method for assigning to to the id instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param id The value to be assigned. 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/***
	 * Accessor method for the iconId instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */
	public String getIconId() {
		return iconId;
	}

	/***
	 * Mutator method for assigning to to the iconId instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param iconId The value to be assigned. 
	 */
	public void setIconId(String iconId) {
		this.iconId = cleanInput(iconId);
	}

	/***
	 * Accessor method for the startDate instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getStartDate() {
		return startDate;
	}

	/***
	 * This mutator is unimplemented because it is implemented 
	 * in the child classes: Location and Event. 
	 * @param startDate The value to be assigned. 
	 */
	public void setStartDate(String startDate) {}

	/***
	 * Accessor method for the endDate instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getEndDate() {
		return endDate;
	}

	/***
	 * This mutator is unimplemented because it is implemented 
	 * in the child classes: Location and Event. 
	 * @param endDate The value to be assigned. 
	 */
	public void setEndDate(String endDate) {}

	/***
	 * Accessor method for the locationName instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getLocationName() {
		return locationName;
	}

	/***
	 * Mutator method for assigning to to the locationName instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param locationName The value to be assigned. 
	 */
	public void setLocationName(String locationName) {
		this.locationName = cleanInput(locationName);
	}

	/***
	 * Accessor method for the locationAddress instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getLocationAddress() {
		return locationAddress;
	}

	/***
	 * Mutator method for assigning to to the locationAddress instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param locationAddress The value to be assigned. 
	 */
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = cleanInput(locationAddress);
	}

	/***
	 * Accessor method for the coordinates instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getCoordinates() {
		return coordinates;
	}

	/***
	 * Mutator method for assigning to to the coordinates instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param coordinates The value to be assigned. 
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = cleanInput(coordinates);
	}
	
	/***
	 * Accessor method for the content instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */		
	public String getContent() {
		return content;
	}
	
	/***
	 * Mutator method for assigning to to the content instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param coordinates The value to be assigned. 
	 */
	public void setContent(String content) {
		this.content = cleanInput(content);
	}

	/***
	 * This methods generates an update SQL query that is populated with the 
	 * instance variables. 
	 * @return Returns the generated update SQL query. 
	 */	
	public String getUpdateQuery() {
		return "UPDATE locations SET iconid = '" + iconId + "', address = '" + locationAddress + 
											  "', name = '" + locationName + "', coord = '" + coordinates + 
											  "', dateStart = " + startDate + ", dateEnd = " + endDate + 
											  ", content = '" + content + "' WHERE id = '" + id + "'";
	}
	
	/***
	 * This method is meant to be overridden in each of it's child classes. 
	 * @return Returns a string. 
	 */
	public String getInsertQuery() {
		return "";
	}
	
	/***
	 * This method generates a delete query based on the id of the PinData. 
	 * @return Returns the generated delete SQL query. 
	 */
	public String getDeleteQuery() {
		return "DELETE FROM locations WHERE id = '" + id + "'";
	}



	@Override
	public String toString() {
		return "PinData [id=" + id + ", iconId=" + iconId + ", locationName=" + locationName + ", locationAddress="
				+ locationAddress + ", coordinates=" + coordinates + ", content=" + content + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	
	
}