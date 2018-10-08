
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;


public class GameBoard implements Cloneable {
   
  
    private int Current_Turn;
    private Maxconnect4.PLAYER_TYPE first_turn;
    private Maxconnect4.MODE game_mode;
	 private int[][] playBoard;
    private int pieceCount;
    public static final int MAX_PIECE_COUNT = 42;
	public static final int NUMBER_OF_COLUMNS = 7;
    public static final int NUMBER_OF_ROWS = 6;

	
	
	
 
                 public GameBoard(String inputFile) {
        this.playBoard = new int[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        this.pieceCount = 0;
        int counter = 0;
        BufferedReader input = null;
        String gameData = null;

        //to open the input file
        try {
            input = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.out.println("\nERROR opening the input file!\n Please Try again!" + "\n");
            e.printStackTrace();
        }

        // to read game data from input file
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            try {
                gameData = input.readLine();

                //to read each piece from the input file
                for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {

                    this.playBoard[i][j] = gameData.charAt(counter++) - 48;

                    
                    if (!((this.playBoard[i][j] == 0) || (this.playBoard[i][j] == Maxconnect4.ONE) || (this.playBoard[i][j] == Maxconnect4.TWO))) {
                        System.out.println("\nERROR\n The piece read " + "from the input file not  1, 2 or  0");
                        this.exit_function(0);
                    }

                    if (this.playBoard[i][j] > 0) {
                        this.pieceCount++;
                    }
                }
            } catch (Exception e) {
                System.out.println("\nERROR reading the input file \n" + "Please Try again\n");
                e.printStackTrace();
                this.exit_function(0);
            }

            // reset counter
            counter = 0;

        } // end for loop

        // read a line more to get the next players turn
        try {
            gameData = input.readLine();
        } catch (Exception e) {
            System.out.println("\nERROR reading the next turn!\n" + " Please Try again!!\n");
            e.printStackTrace();
        }

        this.Current_Turn = gameData.charAt(0) - 48;

        
        if (!((this.Current_Turn == Maxconnect4.ONE) || (this.Current_Turn == Maxconnect4.TWO))) {
            System.out.println("ERROR!\n The current turn read is not  " + "1 or  2!");
            this.exit_function(0);
        } else if (this.getCurrentTurn() != this.Current_Turn) {
            System.out.println("ERROR!\n the current turn read does not " + "correspond to the No. of pieces played!!!");
            this.exit_function(0);
        }
    }      // end GameBoard( String )

   
    public void setPieceValue() {
        if ((this.Current_Turn == Maxconnect4.ONE && first_turn == Maxconnect4.PLAYER_TYPE.COMPUTER)
            || (this.Current_Turn == Maxconnect4.TWO && first_turn == Maxconnect4.PLAYER_TYPE.HUMAN)) {
            Maxconnect4.COMPUTERPIECE = Maxconnect4.ONE;
            Maxconnect4.HUMANPIECE = Maxconnect4.TWO;
        } else {
            Maxconnect4.HUMANPIECE = Maxconnect4.ONE;
            Maxconnect4.COMPUTERPIECE = Maxconnect4.TWO;
        }
        
        System.out.println("Human plays: " + Maxconnect4.HUMANPIECE + " , Computer plays: " + Maxconnect4.COMPUTERPIECE);
        
    }
    
    
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

   
    public GameBoard(int masterGame[][]) {

        this.playBoard = new int[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        this.pieceCount = 0;

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                this.playBoard[i][j] = masterGame[i][j];

                if (this.playBoard[i][j] > 0) {
                    this.pieceCount++;
                }
            }
        }
    } 
    
    public int getScore(int player) {
        // reset Score
        int playerScore = 0;

        // check horizontally
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i][j + 1] == player)
                    && (this.playBoard[i][j + 2] == player) && (this.playBoard[i][j + 3] == player)) {
                    playerScore++;
                }
            }
        } // end horizontal

        // check vertically
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j] == player)
                    && (this.playBoard[i + 2][j] == player) && (this.playBoard[i + 3][j] == player)) {
                    playerScore++;
                }
            }
        } // end Vertical 

        // check diagonally 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j + 1] == player)
                    && (this.playBoard[i + 2][j + 2] == player) && (this.playBoard[i + 3][j + 3] == player)) {
                    playerScore++;
                }
            }
        }

        // check diagonally
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i + 3][j] == player) && (this.playBoard[i + 2][j + 1] == player)
                    && (this.playBoard[i + 1][j + 2] == player) && (this.playBoard[i][j + 3] == player)) {
                    playerScore++;
                }
            }
        }// end check player score 

        return playerScore;
    } // end getScore

    public int getUnBlockedThrees(int player) {
        
        // reset Score
        int playerScore = 0;

        // check horizontally
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i][j + 1] == player)
                    && (this.playBoard[i][j + 2] == player)
                    && (this.playBoard[i][j + 3] == player || this.playBoard[i][j + 3] == 0)) {
                    playerScore++;
                }
            }
        } // end horizontal

        // check vertically
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j] == player)
                    && (this.playBoard[i + 2][j] == player)
                    && (this.playBoard[i + 3][j] == player || this.playBoard[i + 3][j] == 0)) {
                    playerScore++;
                }
            }
        } // end Vertical 

        // check diagonally
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j + 1] == player)
                    && (this.playBoard[i + 2][j + 2] == player)
                    && (this.playBoard[i + 3][j + 3] == player || this.playBoard[i + 3][j + 3] == 0)) {
                    playerScore++;
                }
            }
        }

        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((this.playBoard[i + 3][j] == player) && (this.playBoard[i + 2][j + 1] == player)
                    && (this.playBoard[i + 1][j + 2] == player)
                    && (this.playBoard[i][j + 3] == player || this.playBoard[i][j + 3] == 0)) {
                    playerScore++;
                }
            }
        }// end

        return playerScore;
    }

    
    public int getCurrentTurn() {
        return (this.pieceCount % 2) + 1;
    }
    
    public int getPieceCount() {
        return this.pieceCount;
    }

  
    public int[][] getGameBoard() {
        return this.playBoard;
    }

   
    public boolean isValidPlay(int column) {

        if (!(column >= 0 && column < 7)) {
           
            return false;
        } else if (this.playBoard[0][column] > 0) {
            
            return false;
        } else {
            
            return true;
        }
    }

    

    boolean isBoardFull() {
        return (this.getPieceCount() >= GameBoard.MAX_PIECE_COUNT);
    }

    
    public boolean playPiece(int column) {

        //check if  a valid play
        if (!this.isValidPlay(column)) {
            return false;
        } else {

           
            for (int i = 5; i >= 0; i--) {
                if (this.playBoard[i][column] == 0) {
                    this.playBoard[i][column] = getCurrentTurn();
                    this.pieceCount++;
                    return true;
                }
            }
           
            System.out.println(" Error in  playPiece()");

            return false;
        }
    } 


    
    public void removePiece(int column) {

       
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            if (this.playBoard[i][column] > 0) {
                this.playBoard[i][column] = 0;
                this.pieceCount--;

                break;
            }
        }
    }
    public void displayGameBoard() {
        System.out.println("------------");

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            System.out.print(" | ");
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                System.out.print(this.playBoard[i][j] + " ");
            }

            System.out.println("| ");
        }

        System.out.println("------------");
    } 

    
    public void displayGameBoardToFile(String outputFile) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    output.write(this.playBoard[i][j] + 48);
                }
                output.write("\r\n");
            }

            // write the current turn
            output.write(this.getCurrentTurn() + "\r\n");
            output.close();

        } catch (IOException e) {
            System.out.println("\nError writing to  output file!\n" + "Try again.");
            e.printStackTrace();
        }
    }

    private void exit_function(int value) {
        System.out.println(" Exit from GameBoard.java!\n\n");
        System.exit(value);
    }
 
    //set first turn
    public void setFirstTurn(Maxconnect4.PLAYER_TYPE turn) {
      
        first_turn = turn;
        setPieceValue();
    }

    public Maxconnect4.PLAYER_TYPE getFirstTurn() {
      
        return first_turn;
    }
    //set game mode (interactive or one-move)
    public void setGameMode(Maxconnect4.MODE mode) {
      
        game_mode = mode;
    }

    public Maxconnect4.MODE getGameMode() {
       
        return game_mode;
    }

} // end
