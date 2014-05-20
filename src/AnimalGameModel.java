/*
 * AnimalGameModel.java
 * TODO: YOUR NAME HERE.
 * 
 * Implementation of IAnimalGameModel.
 */
import java.io.*;
import java.util.*;

public class AnimalGameModel implements IAnimalModel {
	
	// In Model-View systems, the Model & View must be able to communicate;
	// this pointer to the view lets you tell the view to do things (show messages,
	// for example).
	private AnimalGameViewer myView;
	private AnimalNode myCurrent;
	private AnimalNode myRoot;
	private ArrayList<String> answers;
	private AnimalNode myPrevious;
	private ArrayList<AnimalNode> questionsTrack;
	private AnimalNode correctAnimal; 
    
	@Override
	public void addNewKnowledge(String question) {
		// TODO Auto-generated method stub
		if (answers.get(answers.size()-2)=="NO"){
			AnimalNode added= new AnimalNode(question, myPrevious.getNo(), correctAnimal);
			myPrevious.setNextNo(added);
			
		}
		if (answers.get(answers.size()-2)=="YES"){
			AnimalNode added= new AnimalNode(question, myPrevious.getYes(), correctAnimal);
			myPrevious.setNextYes(added);
		}
	}

	@Override
	public void addNewQuestion(String noResponse) {
		// TODO Auto-generated method stub
		correctAnimal= new AnimalNode (noResponse, null, null);
	}

	@Override
	public void initialize(Scanner s) {
		// TODO Auto-generated method stub
		myView.setEnabled(true);
		myRoot=readHelper(s);
		newGame();
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		myView.setEnabled(true);
		myCurrent=myRoot;
		answers= new ArrayList<String>();
		questionsTrack= new ArrayList<AnimalNode>(); 
		questionsTrack.add(myCurrent);
		StringBuilder sb = new StringBuilder();
		sb.append(myCurrent+"\n");  // Note the use of "\n" for a newline!
		myView.update(sb.toString());

	}
	

	@Override
	public void processYesNo(boolean yes) {
		// TODO Auto-generated method stub
		if(yes==true && isLeaf(myCurrent)){
			myView.showDialog("I win!");
			newGame();
			return;
		}
		if(yes==false && isLeaf(myCurrent)){
			answers.add("NO");
			myPrevious=questionsTrack.get(questionsTrack.size()-2);
			printHistory();
			myView.getNewInfoLeaf();
			myView.update("What is a 'yes or no' question whose answer reveals information that differentiates between your animal and my guess? Specifically, the answer 'yes' to the question should correspond to my guess, and the answer 'no' should correpsond to the animal you are thinking of.");
			myView.getDifferentiator();
			newGame();
			return;
		}
		if(yes==true){
			myCurrent=myCurrent.getYes();
			answers.add("YES");
		}
		if(yes==false){
			myCurrent=myCurrent.getNo();
			answers.add("NO");
		}
		questionsTrack.add(myCurrent);
		StringBuilder sb = new StringBuilder();
		sb.append(myCurrent+"\n");  // Note the use of "\n" for a newline!
		myView.update(sb.toString());

	}
	
	public boolean isLeaf(AnimalNode current){
		if (current.getYes()==null && current.getNo()==null){
			return true;
		}
		return false;
	}
	
	public void printHistory(){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<answers.size(); i++){
			sb.append("You said "+answers.get(i)+" to "+questionsTrack.get(i)+"\n");  // "\n" again...
		}
		myView.update("Your path so far:\n"+sb.toString()+"\n"+"What is the question I should have asked?\n");
	}
	

	@Override
	public void setView(AnimalGameViewer view) {
		myView = view;
	}

	@Override
	public void write(FileWriter writer){
		writeHelper(myRoot, writer);
		
	}
	
	public void writeHelper(AnimalNode current, FileWriter writer){
		if (current==null){
			return;
		}
		try {
			writer.write(current.toString()+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeHelper(current.getYes(), writer);
		writeHelper(current.getNo(), writer);
	
	}
	
	public AnimalNode readHelper(Scanner s) {
		String line = s.nextLine();
		if (!line.startsWith("#Q:")) {
			AnimalNode leaf= new AnimalNode(line, null, null);
			return leaf;
		}
		if (line.startsWith("//")){
			line=s.nextLine();
		} 
		AnimalNode myYesChild= readHelper(s);
		AnimalNode myNoChild= readHelper(s);
		AnimalNode internalNode= new AnimalNode(line.substring(3), myYesChild, myNoChild);
		return internalNode;		
	}
}
