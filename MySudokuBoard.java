/*
   Sudoku #2 Solution
   CS 143
   HW Core Topics: Sets, Maps, Efficiency, Boolean Zen

   This program implements a Sudoku board validator that checks if a board
   follows the rules of Sudoku and determines if the puzzle is solved.
   It verifies that rows, columns, and 3x3 mini-squares contain no duplicates
   (except spaces), and checks if all numbers 1-9 appear exactly 9 times.
*/

import java.util.*;
import java.io.*;

public class MySudokuBoard {
   public final int SIZE = 9;
   protected char[][] myBoard;

   public MySudokuBoard(String theFile) {
      myBoard = new char[SIZE][SIZE];
      try {
         Scanner file = new Scanner(new File(theFile));
         for(int row = 0; row < SIZE; row++) {
            String theLine = file.nextLine();
            for(int col = 0; col < theLine.length(); col++) {
               myBoard[row][col] = theLine.charAt(col);
            }
         }
      } catch(Exception e) {
         System.out.println("Something went wrong :(");
         e.printStackTrace();
      }
   }

   public String toString() {
      String result = "My Board:\n\n";
      for(int row = 0; row < SIZE; row++) {
         for(int col = 0; col < SIZE; col++) {
            result += (myBoard[row][col]);
         }
         result += ("\n");
      }
      return result;
   }

   // Part 1: isValid method
   // Returns true if all Sudoku constraints are satisfied, false otherwise
   public boolean isValid() {
      return isValidData() && isValidRows() && isValidColumns() && isValidMiniSquares();
   }

   // Helper method: Check that all data in the board is either 1-9 or a space
   private boolean isValidData() {
      HashSet<Character> validChars = new HashSet<Character>();
      validChars.add('1');
      validChars.add('2');
      validChars.add('3');
      validChars.add('4');
      validChars.add('5');
      validChars.add('6');
      validChars.add('7');
      validChars.add('8');
      validChars.add('9');
      validChars.add('0'); // representing empty space
      validChars.add(' ');

      for(int row = 0; row < SIZE; row++) {
         for(int col = 0; col < SIZE; col++) {
            if(!validChars.contains(myBoard[row][col])) {
               return false;
            }
         }
      }
      return true;
   }

   // Helper method: Check that no row contains duplicate values 1-9
   private boolean isValidRows() {
      for(int row = 0; row < SIZE; row++) {
         HashSet<Character> rowValues = new HashSet<Character>();
         for(int col = 0; col < SIZE; col++) {
            char cell = myBoard[row][col];
            // Skip spaces/empty cells
            if(cell != '0' && cell != ' ') {
               if(rowValues.contains(cell)) {
                  return false; // Duplicate found
               }
               rowValues.add(cell);
            }
         }
      }
      return true;
   }

   // Helper method: Check that no column contains duplicate values 1-9
   private boolean isValidColumns() {
      for(int col = 0; col < SIZE; col++) {
         HashSet<Character> colValues = new HashSet<Character>();
         for(int row = 0; row < SIZE; row++) {
            char cell = myBoard[row][col];
            // Skip spaces/empty cells
            if(cell != '0' && cell != ' ') {
               if(colValues.contains(cell)) {
                  return false; // Duplicate found
               }
               colValues.add(cell);
            }
         }
      }
      return true;
   }

   // Helper method: Check that no mini-square contains duplicate values 1-9
   private boolean isValidMiniSquares() {
      for(int spot = 1; spot <= 9; spot++) {
         char[][] mini = miniSquare(spot);
         HashSet<Character> miniValues = new HashSet<Character>();
         for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 3; c++) {
               char cell = mini[r][c];
               // Skip spaces/empty cells
               if(cell != '0' && cell != ' ') {
                  if(miniValues.contains(cell)) {
                     return false; // Duplicate found
                  }
                  miniValues.add(cell);
               }
            }
         }
      }
      return true;
   }

   // Helper method: Extract a 3x3 mini-square from the board
   private char[][] miniSquare(int spot) {
      char[][] mini = new char[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            mini[r][c] = myBoard[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }

   // Part 2: isSolved method
   // Returns true if the board is solved (all constraints met AND all cells filled with 1-9)
   public boolean isSolved() {
      // First check if the board is valid
      if(!isValid()) {
         return false;
      }

      // Count occurrences of each digit 1-9
      Map<Character, Integer> digitCount = new HashMap<Character, Integer>();
      for(char digit = '1'; digit <= '9'; digit++) {
         digitCount.put(digit, 0);
      }

      // Count all digits in the board
      for(int row = 0; row < SIZE; row++) {
         for(int col = 0; col < SIZE; col++) {
            char cell = myBoard[row][col];
            if(cell >= '1' && cell <= '9') {
               digitCount.put(cell, digitCount.get(cell) + 1);
            }
         }
      }

      // Check that each digit 1-9 appears exactly 9 times
      for(char digit = '1'; digit <= '9'; digit++) {
         if(digitCount.get(digit) != 9) {
            return false;
         }
      }

      return true;
   }
}

/*
Paste the output from JGrasp here.
*/
