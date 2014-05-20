
/**
 * An object representing a single node in a Twenty Questions game tree.
 * @author Mac Mason <mac@cs.duke.edu>
 */
public class AnimalNode {
	private AnimalNode myYesChild;
	private AnimalNode myNoChild;
	private String myData;
	public AnimalNode(String data, AnimalNode yes, AnimalNode no) {
		myYesChild = yes;
		myNoChild = no;
		myData = data;
	}
	
	public String toString(){
		return myData;
	}
	
	public AnimalNode getYes() { 
		return myYesChild;
	}
	
	public AnimalNode getNo() {
		return myNoChild;
	}
	
	public void setNextYes(AnimalNode newValue) {
		this.myYesChild = newValue;      
    } 
	
	public void setNextNo(AnimalNode newValue) {
		this.myNoChild = newValue;	       
    } 
	
	
	/*
	 * You may need to add setter functions. Go right ahead.
	 */
}
