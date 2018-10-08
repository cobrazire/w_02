import java.util.*;



public class AIPlayer {

    public int depthLevel;
    public GameBoard GameBoard_shallow;

   
    public AIPlayer(int depth, GameBoard Current_Game) {
        this.depthLevel = depth;
        this.GameBoard_shallow = Current_Game;
    }

   
    public int FindBestPlay(GameBoard Current_Game) throws CloneNotSupportedException {
		
		 
                 int playChoice = Maxconnect4.INVALID;
        if (Current_Game.getCurrentTurn() == Maxconnect4.ONE) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.NUMBER_OF_COLUMNS; i++) {
                if (Current_Game.isValidPlay(i)) {
                    GameBoard nextMoveBoard = new GameBoard(Current_Game.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = ComputeMaxUtility(nextMoveBoard, depthLevel, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v > value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        } else {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.NUMBER_OF_COLUMNS; i++) {
                if (Current_Game.isValidPlay(i)) {
                    GameBoard nextMoveBoard = new GameBoard(Current_Game.getGameBoard());
                    nextMoveBoard.playPiece(i);
                    int value = ComputeMinUtility(nextMoveBoard, depthLevel, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v < value) {
                        playChoice = i;
                        v = value;
                    }
                }
            }
        }
        return playChoice;
    }

 

            private int ComputeMinUtility(GameBoard gameBoard, int depthLevel, int alphaValue, int betaValue)
        throws CloneNotSupportedException {
      
        if (!gameBoard.isBoardFull() && depthLevel > 0) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.NUMBER_OF_COLUMNS; i++) {
                if (gameBoard.isValidPlay(i)) {
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = ComputeMaxUtility(board4NextMove, depthLevel - 1, alphaValue, betaValue);
                    if (v > value) {
                        v = value;
                    }
                    if (v <= alphaValue) {
                        return v;
                    }
                    if (betaValue > v) {
                        betaValue = v;
                    }
                }
            }
            return v;
        } else {
            //terminal state
            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
        }
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    private int ComputeMaxUtility(GameBoard gameBoard, int depthLevel, int alphaValue, int betaValue)
        throws CloneNotSupportedException {
        
        if (!gameBoard.isBoardFull() && depthLevel > 0) {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.NUMBER_OF_COLUMNS; i++) {
                if (gameBoard.isValidPlay(i)) {
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = ComputeMinUtility(board4NextMove, depthLevel - 1, alphaValue, betaValue);
                    if (v < value) {
                        v = value;
                    }
                    if (v >= betaValue) {
                        return v;
                    }
                    if (alphaValue < v) {
                        alphaValue = v;
                    }
                }
            }
            return v;
        } else {
            //terminal state
            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
        }
    }

}
