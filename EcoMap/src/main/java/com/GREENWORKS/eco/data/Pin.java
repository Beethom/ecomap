package com.GREENWORKS.eco.data;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.GREENWORKS.eco.Cred;

/***
 * The parent abstract class for all Pin related data. This class will not be directly 
 * instantiated but rather its behavior will be instantiated through its children. This 
 * is a data class that is meant to be the representatation of the map pins that are 
 * displayed on the UI. 
 * 
 * If there is a behavior that all Pins must have then this is the class where that 
 * behavior should be implemented. 
 */
@MappedSuperclass
public abstract class Pin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	protected Integer id;
	@Column(name="iconid", unique = false, nullable = true)
	protected Integer iconId;
	@Column(name="name", unique = false, nullable = true, length = 120)
    protected String locationName;

	@Column(name="street", unique = false, nullable = true, length = 120)
    protected String street;
	@Column(name="town", unique = false, nullable = true, length = 120)
    protected String town;
	@Column(name="state", unique = false, nullable = true, length = 8)
    protected String state;
	@Column(name="zip", unique = false, nullable = true, length = 5)
    protected String zipCode;

	@Column(name="coord", unique = false, nullable = true, length = 80)
    protected String coordinates;
	@Column(name="content", unique = false, nullable = true, columnDefinition="TEXT")
    protected String content; 
	@Column(name="dateStart", unique = false, nullable = true, columnDefinition="DATETIME")
    protected String startDate;
	@Column(name="dateEnd", unique = false, nullable = true, columnDefinition="DATETIME")
    protected String endDate;
	@Column(name="thumbnail", unique = false, nullable = true, length = 255)
	protected String thumbnail;
	@Column(name="link", unique = false, nullable = true, length = 255)
	protected String link;
	@Column(name="api", unique = false, nullable = true)
	protected Byte api;

	/***
     * Zero parameter constructor. 
     */
    public Pin() { 
    	
    }

	/***
	 * This method adds slashes to a String to preserve the backslashes in textual entries. 
	 * @param s The String that will be modified. 
	 * @return A String that has had additional backslashes added. 
	 */
    public String addSlashes(String s) {
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

	public String removeTags(String s) {
		String noHTMLString = s.replaceAll("\\<.*?\\>", "");
		return noHTMLString;
	}

	/***
	 * This method executes the removeTags() and the addSlashes() methods consecutively. 
	 * @param s The String that will be modified.
	 * @return A modified String that will have no HTML tags and its backslashes preserved. 
	 */
	public String cleanInput(String s) {
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
	public Integer getId() {
		return id;
	}

	/***
	 * Mutator method for assigning to the id instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param id The value to be assigned. 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/***
	 * Accessor method for the iconId instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */
	public Integer getIconId() {
		return iconId;
	}

	/***
	 * Mutator method for assigning to the iconId instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param iconId The value to be assigned. 
	 */
	public void setIconId(Integer iconId) {
		this.iconId = iconId;
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
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

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
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/***
	 * Accessor method for the locationName instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getLocationName() {
		return locationName;
	}

	/***
	 * Mutator method for assigning to the locationName instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param locationName The value to be assigned. 
	 */
	public void setLocationName(String locationName) {
		this.locationName = cleanInput(locationName);
	}

	public void setLocationAddress(String address) {
		DatabaseCleaner databaseCleaner = new DatabaseCleaner();
		ArrayList<String> addressList = databaseCleaner.getAddressAsArrayList(address);
		this.street = addressList.get(0);
		this.town = addressList.get(1);
		this.state = addressList.get(2);
		this.zipCode = addressList.get(3);
	}

	/***
	 * Accessor method that returns the entire address in a single string.
	 * @return Returns the contents of several instance variables in a string that makes sense. 
	 */	
	public String getLocationAddress() {
		return street + ", " + town + ", " + state + " " + zipCode;
	}

	/***
	 * Accessor method for the coordinates instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */	
	public String getCoordinates() {
		return coordinates;
	}

	/***
	 * Mutator method for assigning to the coordinates instance variable. Conducts
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
	 * Mutator method for assigning to the content instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param coordinates The value to be assigned. 
	 */
	public void setContent(String content) {
		this.content = cleanInput(content);
	}

	/***
	 * Mutator method for assigning to the content instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param coordinates The value to be assigned. 
	 */
	public void setContentNoClean(String content) {
		this.content = content;
	}

	/***
	 * Accessor method for the thumbnail instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */		
	public String getThumbnail() {
		return thumbnail;
	}

	/***
	 * Returns the thumbnail variable, formatted as an HTML string
	 * @return Returns the variable as an HTML string
	 */		
	public String getThumbnailHTML() {
		// Make sure it has a value
		if(thumbnail != "" && thumbnail != "null" && thumbnail != null)
		{
			if(thumbnail.contains("<img src")){
				return thumbnail;
			} else {
				return "<img src='" + Cred.BASE_URL + "/images/" + thumbnail + "' width='100px' height='100px' /><br /><br />";
			}
		}
		else
		{
			return "";
		}
	}

	/***
	 * Mutator method for assigning to the thumbnail instance variable. Conducts
	 * cleaning on the parameter. 
	 * @param thumbnail The value to be assigned. 
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/***
	 * Returns the link variable, formatted as an HTML string
	 * @return Returns the variable as an HTML string
	 */		
	public String getLinkHTML() {
		// Make sure it has a value
		if(link != "" && link != "null" && link != null)
		{
			if(link.contains("http://")) {
				return "<br /><br /><a href='" + link + "' target='_blank'>[View Website]</a>";
			} else if(link.contains("https://")){
				return "<br /><br /><a href='" + link + "' target='_blank'>[View Website]</a>";
			} else {
				return "<br /><br /><a href='http://" + link + "' target='_blank'>[View Website]</a>";
			}
		}
		else
		{
			return "";
		}
	}

	/***
	 * Accessor method for the link instance variable. 
	 * @return Returns the contents of the instance variable. 
	 */		
	public String getLink() {
		return link;
	}

	/***
	 * Mutator method for assigning to the thumbnail instance variable. 
	 * @param link The value to be assigned. 
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/***
	 * Accessor method for the api instance variable. 
	 * @return Returns the contents of the instance variable.
	 */
    public Byte getApi() {
		return api;
	}
    
    /***
     * Mutator method for assigning to the api instance variable.
     * @param api The value to be assigned. 
     */
	public void setApi(Byte api) {
		this.api = api;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/***
	 * This is used for copying one pin to another pin. Useful for when it is desirable to copy one
	 * pin type to a different pin type. 
	 * @param pin The Pin object to be copied. 
	 */
	public void copyPin(Pin pin) {
		this.id = pin.getId();
		this.iconId = pin.getIconId();
		this.locationName = pin.getLocationName();
		this.startDate = pin.getStartDate();
		this.endDate = pin.getEndDate();
		this.street = pin.street;
		this.town = pin.town;
		this.state = pin.state;
		this.zipCode = pin.zipCode;
		this.coordinates = pin.getCoordinates();
		this.content = pin.getContent();
		this.thumbnail = pin.getThumbnail();
		this.link = pin.getLink();
		this.api = pin.getApi();
	}
	
	/***
	 * This method is meant to generate a String. 
	 * @return Returns the string that will be used in the front-end. 
	 */
	@Deprecated
	public String getIndexString() {
		return id + "," + iconId + "," + street + ", " + town + ", " + state + " " + zipCode + "," + locationName + "," + coordinates + "," + startDate + "," + endDate + "," + content + "," + thumbnail + "," + link;
	}
	
	/***
	 * This method generates a delete query based on the id of the PinData. 
	 * @return Returns the generated delete SQL query. 
	 */
	@Deprecated
	public String getDeleteQuery() {
		return "DELETE FROM locations WHERE id = '" + id + "'";
	}
	
	/***
	 * Overridden hashCode() method. This is necessary for conducting object comparisons. 
	 */
	@Override
	public int hashCode() {
		int result = getId() != null ? getId().hashCode() : 0;
		result = 31 * result;
		return result;
	}
	
	/***
	 * Overridden equals() method. This is necessary for conducting object comparisons. 
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) 
			return true; // Are equal
		
		Pin otherPin = (Pin) o;
		
		if(this.getId() != null ? !this.getId().equals(otherPin.getId()) : otherPin.getId() != null)
			return false;
		
		return true;
	}
}