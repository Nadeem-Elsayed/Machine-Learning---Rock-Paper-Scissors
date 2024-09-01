package elsayed;

import javafx.scene.image.ImageView;
import javafx.scene.Group;
import java.util.Random;

public class PlayerChoice {
/*
 * this class is used to add the user's choice and the computer's choice
 * it adds the images to the main group
 * it also contains the machine learning algorithm that the computer uses
 */

	ImageView rock = new ImageView(Rock_Paper_Scissors_MachineLearning.rockImage);
	ImageView paper = new ImageView(Rock_Paper_Scissors_MachineLearning.paperImage);
	ImageView scissors = new ImageView(Rock_Paper_Scissors_MachineLearning.scissorsImage);
	protected Group visibleImage = new Group();
	/*
	 * constructor for the players
	 * there are also x and y coordinates attached to the images
	 */
	public PlayerChoice (double x, double y) {
		rock.resize(10, 10);
		rock.setPreserveRatio(true);
		rock.setFitWidth(100);
		rock.relocate(x, y);
		
		paper.resize(10, 10);
		paper.setPreserveRatio(true);
		paper.setFitWidth(100);
		paper.relocate(x, y);
		
		scissors.setPreserveRatio(true);
		scissors.setFitWidth(100);
		scissors.relocate(x, y-20);//move scissors up because they are bigger
	}
	/*
	 * imageType is a number from 0 to 2
	 * 0 is rock
	 * 1 is paper
	 * 2 is scissors
	 */
	public ImageView getChoiceImage(int imageType) {
		visibleImage.getChildren().clear();
		switch(imageType){
			case 0:
				return rock;
			case 1:
				return paper;
			case 2:
				return scissors;
			default:
				return null;
		}
	}
	public int workMagic() {
		String gameData = Rock_Paper_Scissors_MachineLearning.data;
		//find out the number of each choice
		int rockCount = 0;
		int paperCount = 0;
		int scissorCount = 0;
		int length = gameData.length();
		int userPick;
		Random rand = new Random();
		
		if (length>=50) {
			for (int i = 0; i<gameData.length(); i++) {
				if (gameData.charAt(i)==0) {
					rockCount++;
				} else if (gameData.charAt(i)==1) {
					paperCount++;
				} else {
					scissorCount++;
				}
			}
			//calculate most likely pick
			/*
			 * lots of different possibilities
			 * all could have the same number
			 * rock and paper can be equal (and greater than scissors)
			 * scissors and paper can be equal
			 * rock and scissors can be equal
			 * choice with highest picks gets countered by the AI
			 * 
			 */
			if (rockCount==paperCount && rockCount==scissorCount) {
				userPick = randomPick();
			} else if (rockCount == paperCount && rockCount >scissorCount) {
				userPick = rand.nextInt(1);
			} else if (scissorCount == paperCount && scissorCount >rockCount) {
				userPick = rand.nextInt(1) + 1;
			} else if (rockCount == scissorCount && rockCount >paperCount) {
				userPick = rand.nextInt(1);
				userPick = (userPick == 1)? 2: 0;//random now chooses either rock or scissors
			} else if (rockCount > paperCount && rockCount > scissorCount) {
				userPick = 0;
			} else if (paperCount > scissorCount) {
				userPick = 1;
			} else {
				userPick = 2;
			}
			
		} else {//if there are less than 50 played games then the bot chooses randomly
			userPick = randomPick();
			
		}
		// pick counters
		int aiPick;
		if (userPick == 0) {
			aiPick = 1;
		} else if (userPick == 1) {
			aiPick = 2;
		} else {
			aiPick = 0;
		}
		return aiPick;
	}
	public int randomPick() {
		Random rand = new Random();
		int pick = rand.nextInt(3);
		return pick;
	}
}