package tetris;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Tetris {

    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final char EMPTY_CELL = '.';
    private static final char FILLED_CELL = '#';

    private char[][] board;
    private int currentRow;
    private int currentCol;
    private char[][] currentPiece;

    public Tetris() {
        board = new char[BOARD_HEIGHT][BOARD_WIDTH];
        initializeBoard();

        // 시작 위치 설정
        currentRow = 0;
        currentCol = BOARD_WIDTH / 2 - 1;

        // 랜덤한 모양의 블록 선택
        currentPiece = generateRandomPiece();
    }

    private void initializeBoard() {
        for (char[] row : board) {
            Arrays.fill(row, EMPTY_CELL);
        }
    }

    private char[][] generateRandomPiece() {
        Random random = new Random();
        int pieceType = random.nextInt(7); // 7가지 모양의 블록

        switch (pieceType) {
            case 0:
                return new char[][]{{FILLED_CELL, FILLED_CELL, FILLED_CELL, FILLED_CELL}};
            case 1:
                return new char[][]{{FILLED_CELL, FILLED_CELL}, {FILLED_CELL, FILLED_CELL}};
            case 2:
                return new char[][]{{FILLED_CELL, FILLED_CELL, FILLED_CELL}, {EMPTY_CELL, EMPTY_CELL, FILLED_CELL}};
            // ... 다른 모양의 블록들 추가
            default:
                return new char[][]{{FILLED_CELL}};
        }
    }

    private void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isValidMove(int rowOffset, int colOffset) {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                int newRow = currentRow + rowOffset + i;
                int newCol = currentCol + colOffset + j;

                // 보드 범위를 벗어나는지 확인
                if (newRow < 0 || newRow >= BOARD_HEIGHT || newCol < 0 || newCol >= BOARD_WIDTH) {
                    return false;
                }

                // 이미 채워진 셀과 겹치는지 확인
                if (currentPiece[i][j] == FILLED_CELL && board[newRow][newCol] == FILLED_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mergePiece() {
        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                if (currentPiece[i][j] == FILLED_CELL) {
                    board[currentRow + i][currentCol + j] = FILLED_CELL;
                }
            }
        }
    }

    private void clearRows() {
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isRowFilled = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    isRowFilled = false;
                    break;
                }
            }

            if (isRowFilled) {
                // 현재 행을 지우고 위의 행들을 한 칸씩 아래로 이동
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k - 1], 0, board[k], 0, BOARD_WIDTH);
                }
                // 맨 위의 행은 빈 행으로 초기화
                Arrays.fill(board[0], EMPTY_CELL);
            }
        }
    }

    private void rotatePiece() {
        char[][] rotatedPiece = new char[currentPiece[0].length][currentPiece.length];

        for (int i = 0; i < currentPiece.length; i++) {
            for (int j = 0; j < currentPiece[i].length; j++) {
                rotatedPiece[j][currentPiece.length - 1 - i] = currentPiece[i][j];
            }
        }

        if (isValidMove(0, 0, rotatedPiece)) {
            currentPiece = rotatedPiece;
        }
    }

    private boolean isValidMove(int rowOffset, int colOffset, char[][] piece) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                int newRow = currentRow + rowOffset + i;
                int newCol = currentCol + colOffset + j;

                // 보드 범위를 벗어나는지 확인
                if (newRow < 0 || newRow >= BOARD_HEIGHT || newCol < 0 || newCol >= BOARD_WIDTH) {
                    return false;
                }

                // 이미 채워진 셀과 겹치는지 확인
                if (piece[i][j] == FILLED_CELL && board[newRow][newCol] == FILLED_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();
            System.out.println("명령을 입력하세요 (a: 좌, d: 우, s: 하, r: 회전, q: 종료): ");
            String command = scanner.next();

            switch (command) {
                case "a":
                    if (isValidMove(0, -1)) {
                        currentCol--;
                    }
                    break;
                case "d":
                    if (isValidMove(0, 1)) {
                        currentCol++;
                    }
                    break;
                case "s":
                    if (isValidMove(1, 0)) {
                        currentRow++;
                    } else {
                        mergePiece();
                        clearRows();
                        // 새로운 블록 생성
                        currentRow = 0;
                        currentCol = BOARD_WIDTH / 2 - 1;
                        currentPiece = generateRandomPiece();
                        // 새로운 블록이 생성되었을 때 이동이 가능한지 확인
                        if (!isValidMove(0, 0)) {
                            System.out.println("게임 오버!");
                            return;
                        }
                    }
                    break;
                case "r":
                    rotatePiece();
                    break;
                case "q":
                    System.out.println("게임을 종료합니다
