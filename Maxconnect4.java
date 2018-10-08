import java.util.Scanner;



public class Maxconnect4 {

    
    public static final int ONE = 1;
    public static int COMPUTERPIECE;
	public static Scanner input_stream = null;
    public static GameBoard currentGame = null;
    public static AIPlayer aiPlayer = null;
    public static int INVALID = 99;
    public static final String FILEPATH_PREFIX = "..../";
    public static final String COMPUTER_OP_FILE = "computer.txt";
    public static final String HUMAN_OP_FILE = "human.txt";
	 public static final int TWO = 2;
    public static int HUMANPIECE;

               

			   public enum MODE {
        INTERACTIVE,
        ONE_MOVE
    };

    public enum PLAYER_TYPE {
        HUMAN,
        COMPUTER
    };

    public static void main(String[] args) throws CloneNotSupportedException {
       
        if (args.length != 4) {
            System.out.println("Four command-line argument needed:\n"
                + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function(0);
        }

        
        String game_mode = args[0].toString(); 
        String inputFilePath = args[1].toString(); 
        int depthLevel = Integer.parseInt(args[3]); 

        //  initialize the game board
        currentGame = new GameBoard(inputFilePath);

        // the AI Player
        aiPlayer = new AIPlayer(depthLevel, currentGame);

        if (game_mode.equalsIgnoreCase("interactive")) {
            currentGame.setGameMode(MODE.INTERACTIVE);
            if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) {
                
                currentGame.setFirstTurn(PLAYER_TYPE.COMPUTER);
                MakeComputerPlayInteractive();
            } else if (args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")){
                currentGame.setFirstTurn(PLAYER_TYPE.HUMAN);
                MakeHumanPlay();
            } else {
                System.out.println("\n" + "value for 'next turn' not recognized.  \n try again!! \n");
                exit_function(0);
            }

            if (currentGame.isBoardFull()) {
                System.out.println("\nThe Board is Full so I can't play \n\nGame Over.");
                exit_function(0);
            }

        } else if (!game_mode.equalsIgnoreCase("one-move")) {
            System.out.println("\n" + game_mode + "  game mode not recognized \n try again. \n");
            exit_function(0);
        } else {
            //  one-move mode //
            currentGame.setGameMode(MODE.ONE_MOVE);
            String outputFileName = args[2].toString(); // output file 
            MakeComputerPlayOneMove(outputFileName);
        }
    } // end of main()
    
    
    private static void MakeComputerPlayOneMove(String outputFileName) throws CloneNotSupportedException {
       
        int playColumn = 99; 
        boolean playMade = false; 

        System.out.print("\n MaxConnect4 game:\n");

        System.out.print("Game state before move:\n");

        
        currentGame.displayGameBoard();

        // print current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
            + "\n ");

        if (currentGame.isBoardFull()) {
            System.out.println("\nThe Board is Full so i can't play \nGame Over.");
            return;
        }

       //computer play 
        int current_player = currentGame.getCurrentTurn();

        // AI play
        playColumn = aiPlayer.FindBestPlay(currentGame);

        if (playColumn == INVALID) {
            System.out.println("\nThe Board is Full so i can't play \nGame Over.");
            return;
        }

        
        currentGame.playPiece(playColumn);

       
        System.out.println("move " + currentGame.getPieceCount() + ": Player " + current_player + ", column "
            + (playColumn + 1));

        System.out.print("Game state after move:\n");

      
        currentGame.displayGameBoard();

        
        System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
            + "\n ");

        currentGame.displayGameBoardToFile(outputFileName);

       

    }

    
    private static void MakeComputerPlayInteractive() throws CloneNotSupportedException {

        displayBoardAndScore();

        System.out.println("\n Computer's turn:\n");

        int playColumn = INVALID; // the players choice of column to play

        // AI play
        playColumn = aiPlayer.FindBestPlay(currentGame);

        if (playColumn == INVALID) {
            System.out.println("\n The Board is Full so i can't play \nGame Over.");
            return;
        }

        // play the piece
        currentGame.playPiece(playColumn);

        System.out.println("move: " + currentGame.getPieceCount() + " , Player: Computer , Column: " + (playColumn + 1));

        currentGame.displayGameBoardToFile(COMPUTER_OP_FILE);

        if (currentGame.isBoardFull()) {
            displayBoardAndScore();
            displayResult();
        } else {
            MakeHumanPlay();
        }
    }

   
    
    private static void displayResult() {
        int human_score = currentGame.getScore(Maxconnect4.HUMANPIECE);
        int comp_score = currentGame.getScore(Maxconnect4.COMPUTERPIECE);
        
        System.out.println("\n Final Result:");
        if(human_score > comp_score){
            System.out.println("\n Kudos!! You won."); 
        } else if (human_score < comp_score) {
            System.out.println("\n You lost!!");
        } else {
            System.out.println("\n Game is a tie!!");
        }
    }

    
    private static void MakeHumanPlay() throws CloneNotSupportedException {

        displayBoardAndScore();

        System.out.println("\n Humans turn:\n  play your move here(1-7):");

        input_stream = new Scanner(System.in);

        int playColumn = INVALID;

        do {
            playColumn = input_stream.nextInt();
        } while (!isValidPlay(playColumn));

        // play the piece
        currentGame.playPiece(playColumn - 1);

        System.out.println("move: " + currentGame.getPieceCount() + " , Player: Human , Column: " + playColumn);
        
        currentGame.displayGameBoardToFile(HUMAN_OP_FILE);

        if (currentGame.isBoardFull()) {
            displayBoardAndScore();
            displayResult();
        } else {
            MakeComputerPlayInteractive();
        }
    }

	
	
	
                private static boolean isValidPlay(int playColumn) {
        if (currentGame.isValidPlay(playColumn - 1)) {
            return true;
        }
        System.out.println("Invalid column , enter column value between 1 to 7.");
        return false;
    }

    
    public static void displayBoardAndScore() {
        System.out.print("Game state :\n");

        // print the current game board
        currentGame.displayGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentGame.getScore(ONE) + ", Player-2 = " + currentGame.getScore(TWO)
            + "\n ");
    }


    private static void exit_function(int value) {
        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }
} 
