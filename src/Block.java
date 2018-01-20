import java.util.ArrayList;

public class Block {
	boolean isGoal; // to see if block is a goal
	int team; // to see what team the block is on
	double x; // the blocks x coordinate
	double y; // the blocks y coordinate
	int yLineS;
	int xLineS;
	Block hit; // to remember the block you hit
	// Blue is 0,0,200 Red is 200,0,0 Blue's Goal is 139,0,139 Red's Goal is 128,0,0
	int incrX;// to determine which direction the block moves
	int incrY;
	double destX; // where the block is going
	double destY;
	int counter;
	boolean moveing = false;
	int win = 0;
	int blockTypes;
	boolean dead;

	Block(boolean isGoalTemp, int i, int xTemp, int yTemp, int blockType) {
		blockTypes = blockType; // 0 = normal block, 2 = rock
		isGoal = isGoalTemp;
		team = i;
		x = xTemp;
		y = yTemp;
		destX = xTemp;
		destY = yTemp;
		//System.out.println(x);
		//System.out.println(y);
	}

	double getX() {
		return x;
	}

	int getincrX() {
		return incrX;
	}

	int getincrY() {
		return incrY;
	}

	void setX(double tempX) {
		x = tempX;
	}

	void setY(double tempY) {
		y = tempY;
	}

	void setMoveing(boolean temp) {
		moveing = temp;
	}

	boolean getMoveing() {
		return moveing;
	}

	double getY() {
		return y;
	}

	boolean getIsGoal() {
		return isGoal;
	}

	int getTeam() {
		return team;
	}

	double getDestX() {
		return destX;
	}

	double getDestY() {
		return destY;
	}

	void setHitNull() {
		hit = null;
	}

	Block getHitValue() { // block that is getting hit
		return hit;
	}

	void movementCalc(int incrXtemp, int incrYtemp, ArrayList Blocks) { // calculations for movement
		incrX = incrXtemp;
		incrY = incrYtemp;
		moveing = true;
		destX = x;
		destY = y;
		boolean done = true;
		while (done) {
		//	System.out.println("loop1");
			if (isOverlap(Blocks)) {
				destX = destX + incrX;
				destY = destY + incrY;
			//	System.out.println(destX + ",1 " + destY);
			} else {
				destX = destX + (incrX * -1); // if the destination is on a block it will flip the direction and
												// then
												// stop
				destY = destY + (incrY * -1);
				done = false;
				System.out.println(destX + ",1 " + destY + "--------------------");
				System.out.println(incrX + ",,, " + incrY);

			}
		}
	}

	boolean isOverlap(ArrayList Blocks) { // returns false if block touches other block
		while (counter != Blocks.size()) {
		//	System.out.println("loop2");
			Block temp = (Block) Blocks.get(counter);
		//	System.out.println(temp.getX() + " Block " + temp.getY() + "------------");
			if ((temp.getX() == destX) && (temp.getY() == destY) && !(x == temp.getX() && y == temp.getY())) {
				//System.out.println("hit");
				if (!temp.getIsGoal()) {
					hit = temp;
					return false;
				}
			}
			if ((destX > 7) || (destX < 1) || (destY > 7) || (destY < 1)) {
				return false;
			}
			counter++;
		}
		counter = 0;
		return true;

	}

	void setDead(boolean b) { // kill a goal
		dead = b;
		
	}
	boolean getDead() { // kill a block
		return dead;
		
	}

	void getHit(int directX, int directY, ArrayList Blocks) {
		destX = destX + directX;
		destY = destY + directY;
		if (!isOverlap(Blocks)) {
			destX = destX + (directX * -1);
			destY = destY + (directY * -1);
		}
	}

}
