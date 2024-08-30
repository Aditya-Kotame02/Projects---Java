import java.util.InputMismatchException;
import java.util.Scanner;



public class TicTacToe {
	public static Scanner userInputScanner = new Scanner(System.in);
	public enum Status{ONGOING, DRAW, PLAYER_1_WIN, PLAYER_2_WIN}
	char[][] board;
	int numMoves;
	char[] players;
	Status status;
	
	public TicTacToe() {
		this.reset();
	}

	public void reset() {
		char[][] emptyBoard = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
		board = emptyBoard;
		char [] default_players = {'X', 'O'};
		players = default_players;
		numMoves = -1;
		status = Status.ONGOING;	
	}
	
	private char getPiece(int x, int y) {
		return this.board[y][x];
	}
	
	public boolean checkForWin() {
		boolean result = false;
		int[][][] possibilities = {{{0,0},{0,1},{0,2}},{{1,0},{1,1},{1,2}},{{2,0},{2,1},{2,2}},{{0,0},{1,0},{2,0}},{{0,1},{1,1},{2,1}},{{0,2},{1,2},{2,2}},{{0,0},{1,1},{2,2}},{{2,0},{1,1},{0,2}}};
		for (int i = 0; i < possibilities.length; i++) {
			char first_piece = this.getPiece(possibilities[i][0][0], possibilities[i][0][1]);
			char second_piece = this.getPiece(possibilities[i][1][0], possibilities[i][1][1]);
			char third_piece = this.getPiece(possibilities[i][2][0], possibilities[i][2][1]);
			if (first_piece == second_piece && second_piece == third_piece && third_piece != ' ') {
				result = true;
			}
		}
		return result;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public char[] getPlayers() {
		return players;
	}

	public void setPlayers(char[] players) {
		this.players = players;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public char getPlayer2() {
		return this.getPlayers()[1];
	}

	public char getPlayer1() {
		return this.getPlayers()[0];
	}

	public char currentPlayer() {
		return this.getPlayers()[this.numMoves % 2];
	}
	
	public String toString() {
		return String.format(" %s | %s | %s \n-----------\n %s | %s | %s \n-----------\n %s | %s | %s ", this.getBoard()[0][0], this.getBoard()[0][1], this.getBoard()[0][2], this.getBoard()[1][0], this.getBoard()[1][1], this.getBoard()[1][2], this.getBoard()[2][0], this.getBoard()[2][1], this.getBoard()[2][2] );
	}

	public void displayInstructions() {
		System.out.println("This is the game of Tic Tac Toe");
	}
	
	public boolean isEmpty(int square) {
		int y = ((square -1) / 3);
		int x = ((square + 2) % 3);
		System.out.println(String.format("y = %d, x = %d" ,y ,x));
		if (this.board[y][x] == ' ') {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean move() {
		if (this.status != Status.ONGOING) {
			return false;
		}
		this.numMoves += 1;
		boolean validInput = false;
		int input = 0;
		System.out.println(this.toString());
		System.out.println(String.format("Player %s enter a move", this.currentPlayer()));
        while (!validInput) {
        	System.out.println("Enter a valid square number:");
        	try {
                input = userInputScanner.nextInt();
                if (input < 10 && input > 0 && this.isEmpty(input)) {
                	validInput = true;
                }
                else {
                	System.out.println("Number out of range or previously filled.");
                }
            } catch (InputMismatchException e) {
            	System.out.println("Invalid Entry");
                validInput = false;
                userInputScanner.nextLine();
            }
        }
        this.setSquare(input, this.players[this.numMoves % 2]);
        if (checkForWin()) {
        	if (this.currentPlayer() == 'X') {
        		System.out.println("X wins");
        		this.status = Status.PLAYER_1_WIN;
        	}
        	else {
        		System.out.println("O wins");
        		this.status = Status.PLAYER_2_WIN;
        	}
        }
        else if (this.numMoves == 8) {
        	System.out.println("Draw");
        	this.status = Status.DRAW;
        }
		return true;
	}

	public void setSquare(int square, char currentPlayer) {
		int y = ((square -1) / 3);
		int x = ((square + 2) % 3);
		this.getBoard()[y][x] = currentPlayer;
	}
	
	public boolean playChoice() {
		boolean result = false;
		String input;
		boolean validInput = false;
		while (!validInput) {
        	System.out.println("Would you like to play again? (y/n)");
        	try {
                input = userInputScanner.next();
                System.out.println(String.format("%s", input));
                if (input.equals("yes") || input.equals("Yes") || input.equals("y")) {
                	result = true;
                	validInput = true;
                }
                else if (input.equals("no") || input.equals("No") || input.equals("n")) {
                	result = false;
                	validInput = true;
                }
                else {
                	System.out.println("Invalid Entry");
                }
            } catch (InputMismatchException e) {
            	System.out.println("Invalid Entry");
                validInput = false;
                userInputScanner.nextLine();
            }
        }
		return result;
	}
	
	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		game.displayInstructions();
		boolean playAgain = true;
		while (playAgain) {
			game.reset();
			while (game.status == Status.ONGOING) {
				game.move();
			}
			playAgain = game.playChoice();
		}
		userInputScanner.close();
	}
}
