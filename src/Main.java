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
	int secondaryCounter = 0;
	boolean isBlueTurn = false; // to see hows turn it is
	ArrayList<Block> Blocks = new ArrayList<Block>(); // the array list of all the Blocks
	ArrayList<Block> Goals = new ArrayList<Block>(); // the array list of all the Goals
	Block highlight = null; // to know what block is highlighted
	Boolean isBlueWin = null;
	PImage draw;
	PImage background;
	int backgroundNum;

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

	}

	public void draw() {
		clearSpace();
		checkWin();
		if (isBlueWin == null) {
			drawBlocks();
			advanceBlocks();
			drawLines();
		} else if (isBlueWin == false) {
			fill(128, 0, 0);
			rect(0, 0, sizeX, sizeY);
		} else if (isBlueWin == true) {
			fill(0, 0, 200);
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

	void addBlock(boolean isGoal, int isBlue, int x, int y, int blockType) {// so the Block gets put into the arrayList
		if (isGoal) {
			Goals.add(new Block(isGoal, isBlue, x, y, blockType));
		}
		Blocks.add(new Block(isGoal, isBlue, x, y, blockType));
	}

	void drawBlocks() {
		while (counter != Blocks.size()) {
			double tempX = Blocks.get(counter).getX(); // temporary x and y for the block to draw it
			double tempY = Blocks.get(counter).getY();
			int tempIsBlue = Blocks.get(counter).getIsBlue();
			boolean tempIsGoal = Blocks.get(counter).getIsGoal();
			if (tempIsBlue == 1 && tempIsGoal) {
				draw = loadImage("sprites/blueGoal.png"); // blue's goal
			} else if (tempIsBlue == 1) {
				draw = loadImage("sprites/blueBlock.png");
			} else if (!(tempIsBlue == 1) && tempIsGoal) {
				draw = loadImage("sprites/redGoal.png");
			} else if (!(tempIsBlue == 1)) {
				draw = loadImage("sprites/redBlock.png");
			}
			image(draw, (int) ((tempX - 1) * lineSpaceingX), (int) ((tempY - 1) * lineSpaceingY), lineSpaceingX,
					lineSpaceingY);
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
					if ((highlight.getIsBlue() == 1) == isBlueTurn && !highlight.getIsGoal()) {
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
			System.out.println(
					"dest " + temp.getDestX() + ", " + temp.getDestY() + " normal " + temp.getX() + " ," + temp.getY());
			//
			System.out.println(temp.getX() + ", " + (temp.getX() * 100) + .01 + ", "
					+ Math.floor((temp.getX() * 100) + 1) + ", " + Math.floor((temp.getX() * 100) + .01) / 100);
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
		isBlueTurn = !isBlueTurn;
	}

	void checkWin() {
		while (secondaryCounter != Goals.size()) {
			while (counter != Blocks.size()) {
				if (Blocks.get(counter).getX() == Goals.get(secondaryCounter).getX() // seeing is a block hit a goal
						&& Blocks.get(counter).getY() == Goals.get(secondaryCounter).getY()) {
					if (!Blocks.get(counter).getIsGoal()) {
						if (Goals.get(secondaryCounter).getIsBlue() == 1) {
							isBlueWin = false;
						} else if (!(Goals.get(secondaryCounter).getIsBlue() == 1)) {
							isBlueWin = true;
						}
					}
				}
				counter++;
			}
			counter = 0;
			secondaryCounter++;
		}
		secondaryCounter = 0;
	}

}