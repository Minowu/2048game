package game2048;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

public class model {
	private int SIZE;
	private int score=0;
	private int[][] gameField ;
	public model(int SIZE) {
		this.SIZE=SIZE;
		gameField= new int[SIZE][SIZE];
		createGame();
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSIZE() {
		return SIZE;
	}


	public int[][] getGameField() {
		return gameField;
	}
	private void createGame() {
    	createNewNumber();
    	createNewNumber();	
    }	
	private void rotateClockwise() {
        int[][] result = new int[this.SIZE][this.SIZE];
        for (int i = 0; i < this.SIZE; i++) {
            for (int j = 0; j < this.SIZE; j++) {
                result[j][this.SIZE - 1 - i] = gameField[i][j];
            }
        }
        gameField = result;
    }
	
	public boolean compressRow(int[] row) {//{0,0,2,0}->{2,0,0,0}
		int insertPosition = 0;
        boolean result = false;
        for (int x = 0; x < this.SIZE; x++) {
            if (row[x] > 0) {
                if (x != insertPosition) {
                    row[insertPosition] = row[x];
                    row[x] = 0;
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }
	public boolean mergeRow(int[] row){
        boolean result =false;
        for(int x = 0; x < this.SIZE - 1; x++){
            if(row[x]==row[x+1]&& row[x]!=0){
                row[x]*=2;
                row[x+1]=0;
                result=true;
                this.score+=row[x];
            }
        }
        return result;
    } 
	public void moveUp() {
		rotateClockwise();
		rotateClockwise();
		rotateClockwise();
		moveLeft();
		rotateClockwise();
    }
    public void moveDown() {
    	rotateClockwise();
    	moveLeft();
    	rotateClockwise();
    	rotateClockwise();
    	rotateClockwise();
    }
    public void moveRight() {
    	rotateClockwise();
    	rotateClockwise();
    	moveLeft();
    	rotateClockwise();
    	rotateClockwise();
    	
    }
    public void moveLeft() {
    	boolean isNewNumberNeeded = false;
        for (int[] row : this.gameField) {
            boolean wasCompressed = compressRow(row);
            boolean wasMerged = mergeRow(row);
            if (wasMerged) {
                compressRow(row);
            }
            if (wasCompressed || wasMerged) {
                isNewNumberNeeded = true;
            }
        }
        if (isNewNumberNeeded) {
            createNewNumber();
        }
    }
    
    private void createNewNumber() {
    	boolean isCreated = false;
    	Random rd = new Random();
    	do {
	    	int x= rd.nextInt(SIZE);
	    	int y= rd.nextInt(SIZE);
	    	if(this.gameField[x][y]==0) {
	    		this.gameField[x][y]=rd.nextInt(9)<9?2:4;
	    		isCreated=true;
	    	}
    	}while(!isCreated);
    }
    public int getMaxValue() {
    	int max= this.gameField[0][0];
    	for(int i=0;i<this.SIZE;i++) {
    		for(int j=0;j<this.SIZE;j++) {
    			if(max<this.gameField[i][j]) {
    				max=gameField[i][j];
    			}
    		}
    	}
    	return max;
    }
    public boolean canUserMove() {
    	for(int i=0;i<this.SIZE;i++) {
    		for(int j=0;j<this.SIZE;j++) {
    			if(this.gameField[i][j]==0) {
    				return true;
    			}
    			if (j < this.SIZE - 1 && gameField[i][j] == gameField[i][j+1]) {
                    return true;
    			}
    			if ((i < this.SIZE - 1) && gameField[i][j] == gameField[i+1][j]) {
                    return true;
                }
    		}
    	}
    	return false;
    }
    public void save() {
		String s = this.score+"";
		try {
			FileWriter f = new FileWriter("D:\\Java\\eclipse-workspace\\Xephinh\\bin\\game2048\\game2048.txt");
			f.write(s);
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public int load_bestScore() {
        int BEST = 0;
        try {
            FileReader fr = new FileReader("D:\\Java\\eclipse-workspace\\Xephinh\\bin\\game2048\\game2048.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            if ((line = br.readLine()) != null) {
                BEST = Integer.parseInt(line);
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BEST;
    }


}
