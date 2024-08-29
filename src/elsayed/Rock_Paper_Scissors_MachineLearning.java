/*
 * Created by Nadeem Elsayed as a passion project
 * -and to experiment a little with machine learning and logic-
 * this is a javafx application, make sure you have the correct libraries installed
 * Images found from pngwing and pngimg
 */
package elsayed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Rock_Paper_Scissors_MachineLearning extends Application {

	/*
	 * Class Variables that are used in more than one location
	 */
	
	/*
	 * Images
	 */
	Image robotImage = new Image("file:resources/robot.png");
	int counterPick;
	
	//these are only accessible by classes in the same folder and are static
	//i.e they are unchanged for each class
	protected static Image rockImage = new Image("file:resources/rock.png");
	protected static Image paperImage = new Image("file:resources/paper.png");
	protected static Image scissorsImage = new Image("file:resources/scissors.png");
	/*
	 * Fonts
	 */
	//uses system font
	Font regularFont = Font.loadFont("file:resources/Montserrat-regular.ttf", 16);
	Font titleFont = Font.loadFont("file:resources/Montserrat-Medium.ttf", 25);
	
	//game data is the same for each class
	static String data = "";
	static double winPercent = 0;
	static int gamesPlayed = 0;
	@Override
	public void start(Stage stage) throws Exception {
		
		/*
		 * Title Scene, this has instructions and buttons to go to main scene
		 */
		Group titleGroup = new Group();
		
		//Title text
		Text titleText = new Text("Machine Learning - Rock Paper Scissors");
		titleText.relocate(30, 60);
		titleText.setFont(titleFont);
		
		//Regular Text
		String textContent = 
				  "This application is a rock paper scissors game with a twist, "
				+ "usually a rock paper scissors game has a 33% chance of winning, "
				+ "33% chance of losing and 33% chance of drawing at random odds, "
				+ "this bot though takes a look at the users previous games "
				+ "and achieves a higher win accuracy, the more you use this bot, "
				+ "the better it gets. Good luck!";
		
		Text regularText = new Text(textContent);
		regularText.setFont(regularFont);
		regularText.relocate(50, 150);
		regularText.setWrappingWidth(450);
		
		//start button
		Button startButton = new Button("Start");
		startButton.setFont(regularFont);
		startButton.relocate(225, 400);
		startButton.setPrefSize(100, 40);
		
		//Robot image
		ImageView homeRobot = new ImageView(robotImage);
		homeRobot.setPreserveRatio(true);
		homeRobot.setFitWidth(130);
		homeRobot.relocate(0, 390);
		
		titleGroup.getChildren().addAll(titleText, regularText, startButton, homeRobot);
		//on button press switch to mainGroup
		//set root method
		
		/*
		 * Main Scene, this is where the game is
		 * Set all the stuff for the stage
		 * Rock, Paper and Scissors Images
		 * Buttons
		 */
		//----Rock Image
		ImageView rock = new ImageView(rockImage);
		rock.resize(10, 10);
		rock.setPreserveRatio(true);
		rock.setFitWidth(100);
		rock.relocate(70, 50);
		
		//----Rock Button
		Button rockButton = new Button("Rock");
		rockButton.relocate(70,150);
		rockButton.setPrefSize(100, 40);
		rockButton.setFont(regularFont);
		
		//----Paper Image
		ImageView paper = new ImageView(paperImage);
		paper.resize(10, 10);
		paper.setPreserveRatio(true);
		paper.setFitWidth(100);
		paper.relocate(220, 50);
		
		//----Paper Button
		Button paperButton = new Button("Paper");
		paperButton.relocate(220,150);
		paperButton.setPrefSize(100, 40);
		paperButton.setFont(regularFont);
		
		//----Scissors Image
		ImageView scissors = new ImageView(scissorsImage);
		scissors.setPreserveRatio(true);
		scissors.setFitWidth(100);
		scissors.relocate(370, 30); //move scissors a bit up because it is bigger
		
		//----Scissors Button
		Button scissorsButton = new Button("Scissors");
		scissorsButton.relocate(370,150);
		scissorsButton.setPrefSize(100, 40);
		scissorsButton.setFont(regularFont);
		
		//Robot Image
		ImageView robot = new ImageView(robotImage);
		robot.setPreserveRatio(true);
		robot.setFitWidth(130);
		robot.relocate(220, 390);
		
		//Back Button
		Button backButton = new Button("Back");
		backButton.setFont(regularFont);
		backButton.relocate(20, 440);
		backButton.setPrefSize(100, 40);
		
		Button nextButton = new Button("Next");
		nextButton.setFont(regularFont);
		nextButton.relocate(430,440);
		nextButton.setPrefSize(100, 40);
		nextButton.setDisable(true);
		
		//Player Choices
		PlayerChoice userChoice = new PlayerChoice(145, 220);
		PlayerChoice aiChoice = new PlayerChoice(295, 220);
		
		//set the stage
		Scene scene = new Scene(titleGroup, 550, 500);
		Group mainGroup = new Group();
		stage.setScene(scene);
		
		/*
		 * WHAT THE BUTTONS DO!!!
		 * The following code sets the button actions
		 */
		startButton.setOnAction(any -> 
		{
			scene.setRoot(mainGroup);
			//reset the game
			mainGroup.getChildren().clear();
			mainGroup.getChildren().addAll(rock, rockButton, paper, paperButton, scissors, scissorsButton, robot, backButton, nextButton);
			rockButton.setDisable(false);
			paperButton.setDisable(false);
			scissorsButton.setDisable(false);
			nextButton.setDisable(true);
		});
		
		backButton.setOnAction(any -> 
		{
			scene.setRoot(titleGroup);
			mainGroup.getChildren().clear();
			mainGroup.getChildren().addAll(rock, rockButton, paper, paperButton, scissors, scissorsButton, robot, backButton, nextButton);
		});
		
		rockButton.setOnAction(e -> 
		{
			mainGroup.getChildren().clear();
			mainGroup.getChildren().addAll(rock, rockButton, paper, paperButton, scissors, scissorsButton, robot, backButton, nextButton);
			mainGroup.getChildren().add(userChoice.getChoiceImage(0));
			counterPick = aiChoice.workMagic();
			mainGroup.getChildren().add(aiChoice.getChoiceImage(counterPick));
			rockButton.setDisable(true);
			paperButton.setDisable(true);
			scissorsButton.setDisable(true);
			nextButton.setDisable(false);
			writeResults(0);
		});
		paperButton.setOnAction(e -> 
		{
			mainGroup.getChildren().clear();
			mainGroup.getChildren().addAll(rock, rockButton, paper, paperButton, scissors, scissorsButton, robot, backButton, nextButton);
			mainGroup.getChildren().add(userChoice.getChoiceImage(1));
			counterPick = aiChoice.workMagic();
			mainGroup.getChildren().add(aiChoice.getChoiceImage(counterPick));
			rockButton.setDisable(true);
			paperButton.setDisable(true);
			scissorsButton.setDisable(true);
			nextButton.setDisable(false);
			writeResults(1);
		});
		scissorsButton.setOnAction(e -> 
		{
			mainGroup.getChildren().clear();
			mainGroup.getChildren().addAll(rock, rockButton, paper, paperButton, scissors, scissorsButton, robot, backButton, nextButton);
			mainGroup.getChildren().add(userChoice.getChoiceImage(2));
			counterPick = aiChoice.workMagic();
			mainGroup.getChildren().add(aiChoice.getChoiceImage(counterPick));
			rockButton.setDisable(true);
			paperButton.setDisable(true);
			scissorsButton.setDisable(true);
			nextButton.setDisable(false);
			writeResults(2);
		});
		nextButton.setOnAction(e -> 
		{
			mainGroup.getChildren().clear();
			mainGroup.getChildren().addAll(rock, rockButton, paper, paperButton, scissors, scissorsButton, robot, backButton, nextButton);
			rockButton.setDisable(false);
			paperButton.setDisable(false);
			scissorsButton.setDisable(false);
			nextButton.setDisable(true);
			//TODO, get rid of the AI choice image
		});
		
		//let the show begin
		stage.setTitle("Rock Paper Scissors - Machine Learning");
		stage.setResizable(false);//only designed for one width/height
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
	/*
	 * Integer can be a number from 0 to 2
	 * 0 is rock
	 * 1 is paper
	 * 2 is scissors
	 * adds the given playerChoice to the known player data
	 */
	public void writeResults(int playerChoice) {
		File gameData = new File("resources/GameData.txt");
		
		//reading the file
		data = readResults()+playerChoice;
		
		//writing
		PrintWriter p;
		try {
			p = new PrintWriter(gameData);
			p.print(data);
			p.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * retrieves data from the GameData file
	 */
	public static String readResults() {
		File userData = new File("resources/GameData.txt");
		data = "";
		//reading the file
		try {
			Scanner reader = new Scanner(userData);
			data = data + reader.nextLine();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	/*
	 * 0 for player loss
	 * 1 for player win
	 * winPercent is based on how much the player wins
	 */
	public void updateWinPercent(int winOrLoss){
		File userData = new File("resources/GameData.txt");
		double factor;
		double wins;
		try {
			Scanner reader = new Scanner(userData);
			reader.nextLine();//skip the gameData
			winPercent = Integer.parseInt(reader.nextLine());
			gamesPlayed = Integer.parseInt(reader.nextLine());
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (gamesPlayed == 0) {
			if (winOrLoss ==0) {
				winPercent = 0;
			} else {
				winPercent = 100;
			}
		} else {
			if (winOrLoss == 0) {
				factor = gamesPlayed/100;
				wins = winPercent*factor;
				gamesPlayed++;
				winPercent = wins/gamesPlayed;
			} else {
				factor = gamesPlayed/100;
				wins = winPercent*factor;
				gamesPlayed++;
				wins++;
				winPercent = wins/gamesPlayed;
			}
		}
	}
}
