import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {
	int sizeX = 600;
	int sizeY = 600; // the x and y for the window
	int lineSpaceingX = sizeX / 7; // How far apart the grid lines are
	int lineSpaceingY = sizeY / 7;
	double acceleration = .1; // how fast the blocks move
	Block temp; // used to go through arrays
	int counter = 0; // used for going through arrays
	int turn = 1; // to see what turn it is 1 = blue 2 = red 3 = green
	int players = 2;
	ArrayList<Block> Blocks = new ArrayList<Block>(); // the array list of all the Blocks
	ArrayList<Block> Goals = new ArrayList<Block>(); // the array list of all the Goals
	Block highlight = null; // to know what block is highlighted
	int winTeam = 0;
	PImage draw;
	PImage background;
	int backgroundNum;
	int secondaryCounter;
	int blueGoals;
	int redGoals;
	int greenGoals;
	int blueGoalsTemp;
	int redGoalsTemp;
	int greenGoalsTemp;

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	public void settings() {
		size(sizeX, sizeY);
	}

	public void setup() {
		System.out.println("What map would you like to play on 1,2,3,4");
		Scanner scan = new Scanner(System.in);
		backgroundNum = scan.nextInt();
		System.out.println("how many players max 2 - 3");
		players = scan.nextInt();
		scan.close();
		// is Goal, isBlue(int), x, y, blockType
		// isBlue 1 = blue 2 = Red 0 = no team
		// blockType 0 = normal Block 1 = rock
		// doing blue team
		addBlock(true, 1, 4, 2, 0);

		addBlock(false, 1, 2, 1, 0);
		addBlock(false, 1, 3, 1, 0);
		addBlock(false, 1, 7, 1, 0);
		// doing red team
		addBlock(true, 2, 4, 6, 0);

		addBlock(false, 2, 2, 7, 0);
		addBlock(false, 2, 3, 7, 0);
		addBlock(false, 2, 7, 7, 0);

		// doing green team
		addBlock(true, 3, 1, 1, 0);
		
		addBlock(false, 3, 7, 4, 0);
		addBlock(false, 3, 6, 4, 0);
	}

	public void draw() {
		clearSpace();
		advanceBlocks();
		checkHit();
		checkGoals();
		if (winTeam == 0) {
			drawBlocks();
			drawLines();
		} else if (winTeam == 2) {
			fill(128, 0, 0);
			rect(0, 0, sizeX, sizeY);
		} else if (winTeam == 1) {
			fill(0, 0, 200);
			rect(0, 0, sizeX, sizeY);
		} else if (winTeam == 3) {
			fill(0, 200, 0);
			rect(0, 0, sizeX, sizeY);
		}

	}

	void drawLines() {
		fill(0, 0, 0);
		// drawing vertical lines
		line(lineSpaceingX, 0, lineSpaceingX, sizeY);
		line(lineSpaceingX * 2, 0, lineSpaceingX * 2, sizeY);
		line(lineSpaceingX * 3, 0, lineSpaceingX * 3, sizeY);
		line(lineSpaceingX * 4, 0, lineSpaceingX * 4, sizeY);
		line(lineSpaceingX * 5, 0, lineSpaceingX * 5, sizeY);
		line(lineSpaceingX * 6, 0, lineSpaceingX * 6, sizeY);
		// drawing horizontal lines
		line(0, lineSpaceingY, sizeX, lineSpaceingY);
		line(0, lineSpaceingY * 2, sizeX, lineSpaceingY * 2);
		line(0, lineSpaceingY * 3, sizeX, lineSpaceingY * 3);
		line(0, lineSpaceingY * 4, sizeX, lineSpaceingY * 4);
		line(0, lineSpaceingY * 5, sizeX, lineSpaceingY * 5);
		line(0, lineSpaceingY * 6, sizeX, lineSpaceingY * 6);
	}

	void clearSpace() {
		if (backgroundNum == 1) {
			background = loadImage("sprites/background1.png");
		} else if (backgroundNum == 2) {
			background = loadImage("sprites/background2.png");
		}
		image(background, 0, 0, sizeX, sizeY);
	}

	void addBlock(boolean isGoal, int color, int x, int y, int blockType) {// so the Block gets put into the arrayList
		Block temp = new Block(isGoal, color, x, y, blockType);
		if (isGoal) {
			Goals.add(temp);
			if (color == 1) {
				blueGoals++;
				System.out.println("------------------------ " + blueGoals);
			} else if (color == 2) {
				redGoals++;
			} else if (color == 3) {
				greenGoals++;
			}
		}
		Blocks.add(temp);
	}

	void drawBlocks() {
		while (counter != Blocks.size()) {
			double tempX = Blocks.get(counter).getX(); // temporary x and y for the block to draw it
			double tempY = Blocks.get(counter).getY();
			int tempTeam = Blocks.get(counter).getTeam();
			boolean tempIsGoal = Blocks.get(counter).getIsGoal();
			boolean tempDead = Blocks.get(counter).getDead();
			if (tempIsGoal) {
				System.out.println(tempDead);
			}
			if (tempTeam == 1 && tempIsGoal && !tempDead) {
				draw = loadImage("sprites/blueGoal.png"); // blue's goal
			} else if (tempTeam == 1 && !tempIsGoal) {
				draw = loadImage("sprites/blueBlock.png");
			} else if (tempTeam == 2 && tempIsGoal && !tempDead) {
				draw = loadImage("sprites/redGoal.png");
			} else if (tempTeam == 2 && !tempIsGoal) {
				draw = loadImage("sprites/redBlock.png");
			} else if (tempTeam == 3 && tempIsGoal && !tempDead) {
				draw = loadImage("sprites/greenGoal.png");
			} else if (tempTeam == 3 && !tempIsGoal) {
				draw = loadImage("sprites/greenBlock.png");
			} else {
				draw = null;
			}
			if (draw != null) {
				image(draw, (int) ((tempX - 1) * lineSpaceingX), (int) ((tempY - 1) * lineSpaceingY), lineSpaceingX,
						lineSpaceingY);
			}
			if (Blocks.get(counter) == highlight) {
				draw = loadImage("sprites/blueBorder.png");
				image(draw, (int) ((tempX - 1) * lineSpaceingX), (int) ((tempY - 1) * lineSpaceingY), lineSpaceingX,
						lineSpaceingY);
			}

			counter++; // Updating counter
		}
		counter = 0; // Reseting counter
	}

	Block checkBlockHigh() {// checks what block your mouse is over
		while (counter != Blocks.size()) {
			highlight = Blocks.get(counter);
			if (((highlight.getX() - 1) * lineSpaceingX < mouseX) && (highlight.getX() * lineSpaceingX > mouseX)) {
				if (((highlight.getY() - 1) * lineSpaceingY < mouseY) && (highlight.getY() * lineSpaceingY > mouseY)) {
					if (highlight.getTeam() == turn && !highlight.getIsGoal()) {
						return highlight;
					}
				}

			}
			counter++;
		}
		highlight = null;
		return null;

	}

	void advanceBlocks() { // move a block towards there destination
		while (counter != Blocks.size()) {
			temp = Blocks.get(counter);
			if (temp.getDestX() > temp.getX()) {
				temp.setX(temp.getX() + acceleration);
			} else if (temp.getDestX() < temp.getX()) {
				temp.setX(temp.getX() - acceleration);
			} else if (temp.getDestY() > temp.getY()) {
				temp.setY(temp.getY() + acceleration);
			} else if (temp.getDestY() < temp.getY()) {
				temp.setY(temp.getY() - acceleration);
			} else if ((temp.getX() == temp.getDestX()) && (temp.getY() == temp.getDestY())) {
				if (temp.getHitValue() != null) {
					temp.getHitValue().getHit(temp.getincrX(), temp.getincrY(), Blocks);
					temp.setHitNull();
				}
				temp.setMoveing(false);
			}
			// System.out.println(
			// "dest " + temp.getDestX() + ", " + temp.getDestY() + " normal " + temp.getX()
			// + " ," + temp.getY());
			//
			// System.out.println(temp.getX() + ", " + (temp.getX() * 100) + .01 + ", "
			// + Math.floor((temp.getX() * 100) + 1) + ", " + Math.floor((temp.getX() * 100)
			// + .01) / 100);
			// making the position to decimals
			temp.setX(Math.floor((temp.getX() * 100) + .01) / 100);
			temp.setY(Math.floor((temp.getY() * 100) + .01) / 100);
			System.out.println(
					"dest " + temp.getDestX() + ", " + temp.getDestY() + " normal " + temp.getX() + " ," + temp.getY());
			counter++;
		}
		counter = 0;

	}

	public void mouseClicked() {
		highlight = checkBlockHigh();
	}

	public void keyPressed() {
		if (highlight != null) {
			if (!highlight.getMoveing()) {
				if (keyCode == UP) {
					System.out.println("UP");
					highlight.movementCalc(0, -1, Blocks);
				} else if (keyCode == DOWN) {
					System.out.println("DOWN");
					highlight.movementCalc(0, 1, Blocks);
				} else if (keyCode == LEFT) {
					System.out.println("LEFT");
					highlight.movementCalc(-1, 0, Blocks);
				} else if (keyCode == RIGHT) {
					System.out.println("RIGHT");
					highlight.movementCalc(1, 0, Blocks);
				}
			}
			highlight = null;
			passTurn();
		}
	}

	void passTurn() {
		if (players > turn) {
			turn++;
		} else {
			turn = 1;
		}
	}

	void checkHit() {
		while (secondaryCounter != Goals.size()) {
			// System.out.println("----------------------");
			// System.out.println(Goals.get(secondaryCounter).getX() + ", " +
			// Goals.get(secondaryCounter).getY());
			// System.out.println("----------------------");
			while (counter != Blocks.size()) {
				// System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" +
				// Blocks.get(counter).getX() + ", "
				// + Blocks.get(counter).getY());
				if (Blocks.get(counter).getX() == Goals.get(secondaryCounter).getX() // seeing is a block hit a goal
						&& Blocks.get(counter).getY() == Goals.get(secondaryCounter).getY()) {
					// System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
					if (!Blocks.get(counter).getIsGoal()) {
						Goals.get(secondaryCounter).setDead(true);
						System.out.println("looooooooooooooooooooooooook");
						System.out.println(Blocks.get(Blocks.indexOf(Goals.get(secondaryCounter))).getDead());
						System.out.println("looooooooooooooooooooooooook");
						// System.out.println("-----------------------------------------------------------"
						// + Goals.get(secondaryCounter).getDead());
					}
				}
				counter++;
			}
			counter = 0;
			secondaryCounter++;
		}
		secondaryCounter = 0;
	}

	void checkGoals() {
		blueGoalsTemp = blueGoals;
		redGoalsTemp = redGoals;
		greenGoalsTemp = greenGoals;
		while (counter != Goals.size()) {

			if (Goals.get(counter).getTeam() == 1) {
				if (Goals.get(counter).getDead()) {
					blueGoalsTemp--;
					// System.out.println("--------------------------- hit blue goal " +
					// blueGoalsTemp);
				}
			} else if (Goals.get(counter).getTeam() == 2) {
				if (Goals.get(counter).getDead()) {
					redGoalsTemp--;
				}
			} else if (Goals.get(counter).getTeam() == 3) {
				if (Goals.get(counter).getDead()) {
					greenGoalsTemp--;
				}
			}
			counter++;
		}
		counter = 0;
		if (players == 2) {
			if (blueGoalsTemp > 0 && redGoalsTemp <= 0) {
				winTeam = 1;
			} else if (blueGoalsTemp <= 0 && redGoalsTemp > 0) {
				// System.out.print("red win");
				winTeam = 2;
			}
		} else if (players == 3) {
			if (blueGoalsTemp > 0 && redGoalsTemp <= 0 && greenGoalsTemp <= 0) {
				winTeam = 1;
			} else if (blueGoalsTemp <= 0 && redGoalsTemp > 0 && greenGoalsTemp <= 0) {
				winTeam = 2;
			} else if (blueGoalsTemp <= 0 && redGoalsTemp <= 0 && greenGoalsTemp > 0) {
				winTeam = 3;
			}
		}
	}
}
