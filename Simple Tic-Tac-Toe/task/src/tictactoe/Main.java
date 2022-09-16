package tictactoe;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static String[][] grid;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String game = "_________";
        grid = createGrid(game);
        processGame(grid);
       while (true) {
            String result = evaluateGame(grid);
            if (result.equals("Impossible") || result.equals("X wins") || result.equals("O wins") || result.equals("Draw")) {
                System.out.println(result);
                break;
            }
            makeMove(scanner);
        }
    }

    private static void makeMove(Scanner scanner) {
        while (true) {
            try {
                String[] line = scanner.nextLine().split(" ");
                int column = Integer.parseInt(line[0]) - 1;
                int row = Integer.parseInt(line[1]) - 1;
                if (!isCoordinateInGrid(column, row)) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    if (cellOcuupied(column, row)) {
                        System.out.println("This cell is occupied! Choose another one!");
                    } else {
                        addMoveToGrid(grid, column, row);
                        break;
                    }
                }
            } catch (InputMismatchException e ) {
                System.out.println("You should enter numbers!");
            }catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        }
        processGame(grid);
    }

    private static boolean isCoordinateInGrid(int column, int row) {
        if (!(column >= 0 && column < 3)) {
            return false;
        }
        if (!(row >= 0 && row < 3)) {
            return false;
        }
        return true;
    }

    private static void addMoveToGrid(String[][] grid, int column, int row) {
        String letter = isOnMove(grid);
        grid[column][row] = letter;
    }

    private static String isOnMove(String[][] grid) {
        int helper = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].equals("X") || grid[i][j].equals("O")) {
                    helper++;
                }
            }
        }
        if (helper % 2 == 0) {
            return "X";
        } else {
            return "O";
        }
    }

    private static boolean cellOcuupied(int collum, int row) {
        if (grid[collum][row].equals("X") || grid[collum][row].equals("O")) {
            return true;
        }
        return false;
    }

    private static void processGame(String[][] grid) {
        StringBuilder sb = new StringBuilder();
        sb.append(printGame(grid));
        //sb.append("\n" + evaluateGame(grid));
        System.out.println(sb);
    }

    private static String evaluateGame(String[][] grid) {
        boolean threeX = hasThreeOfSameLetter(grid, "X");
        boolean three0 = hasThreeOfSameLetter(grid, "O");
        if (isImpossible(grid) || threeX && three0) {
            return "Impossible";
        } else if (threeX && !three0) {
            return "X wins";
        } else if (three0 && !threeX) {
            return "O wins";
        } else if (!threeX && !three0) {
            if (gameIsRunning(grid)) {
                return "Game not finished";
            } else {
                return "Draw";
            }
        }
        return "Unknown";
    }

    private static boolean gameIsRunning(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (Objects.equals(grid[i][j], "_") || Objects.equals(grid[i][j], " ")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasThreeOfSameLetter(String[][] grid, String letter) {
        return checkRows(grid, letter) || checkColumn(grid, letter) || (checkDiagonals(grid, letter));
    }

    private static boolean checkDiagonals(String[][] grid, String letter) {
        if (grid[0][0].equals(letter) && grid[1][1].equals(letter) && grid[2][2].equals(letter)) {
            return true;
        }

        if (grid[0][2].equals(letter) && grid[1][1].equals(letter) && grid[2][0].equals(letter)) {
            return true;
        }

        return false;
    }

    private static boolean checkRows(String[][] grid, String letter) {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0].equals(letter) && grid[i][1].equals(letter) && grid[i][2].equals(letter)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumn(String[][] grid, String letter) {
        for (int i = 0; i < 3; i++) {
            if (grid[0][i].equals(letter) && grid[1][i].equals(letter) && grid[2][i].equals(letter)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isImpossible(String[][] game) {
        int x = 0;
        int o = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game[i][j].equals("X")) {
                    x++;
                } else if (game[i][j].equals("O")) {
                    o++;
                }
            }
        }

        return (x > o + 1 || o > x + 1);
    }

    private static String[][] createGrid(String game) {
        String[][] gameGrid = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameGrid[i][j] = game.substring(3 * i + j, 3 * i + j + 1);
            }
        }
        return gameGrid;
    }

    private static String printGame(String[][] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("---------" + "\n");

        for (int i = 0; i < array.length; i++) {
            sb.append("| ");
            for (int j = 0; j < array[0].length; j++) {
                if (!array[i][j].equals("_")) {
                    sb.append(array[i][j] + " ");
                } else {
                    sb.append("  ");
                }
            }
            sb.append("|" + "\n");
        }
        sb.append("---------");
        return sb.toString();
    }
}
