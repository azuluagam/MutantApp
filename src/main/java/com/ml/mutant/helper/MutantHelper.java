package com.ml.mutant.helper;

import java.util.function.BiPredicate;

public class MutantHelper {

    private static final BiPredicate<Character, Character> equalChars = Character::equals;

    private MutantHelper(){}

    public static char[][] createDnaMatrix(String[] dnaChain) {
        char[][] dnaMatrix = new char[dnaChain.length][dnaChain.length];
        for (int i = 0; i < dnaChain.length; i++) {
            String dna = dnaChain[i];
            char[] dnaArray = dna.toCharArray();
            System.arraycopy(dnaArray, 0, dnaMatrix[i], 0, dnaArray.length);
        }
        return dnaMatrix;
    }

    public static boolean checkHorizontal(int i, int j, char[][] dnaMatrix) {
        int n = dnaMatrix.length;
        if (j + 1 < n && j + 2 < n && j + 3 < n) {
            return equalChars.test(dnaMatrix[i][j], dnaMatrix[i][j+1]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i][j+2]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i][j+3]);
        }
        return false;
    }

    public static boolean checkVertical(int i, int j, char[][] dnaMatrix) {
        int n = dnaMatrix.length;
        if (i + 1 < n && i + 2 < n && i + 3 < n) {
            return equalChars.test(dnaMatrix[i][j], dnaMatrix[i+1][j]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i+2][j]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i+3][j]);
        }
        return false;
    }

    public static boolean checkForwardDiagonal(int i, int j, char[][] dnaMatrix) {
        int n = dnaMatrix.length;
        if ((i + 1 < n && i + 2 < n && i + 3 < n) &&
                (j + 1 < n && j + 2 < n && j + 3 < n)) {
            return equalChars.test(dnaMatrix[i][j], dnaMatrix[i+1][j+1]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i+2][j+2]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i+3][j+3]);
        }
        return false;
    }

    public static boolean checkBackwardDiagonal(int i, int j, char[][] dnaMatrix) {
        int n = dnaMatrix.length;
        if ((i + 1 < n && i + 2 < n && i + 3 < n) &&
                (j - 1 >= 0 && j - 2 >= 0 && j - 3 >= 0)) {
            return equalChars.test(dnaMatrix[i][j], dnaMatrix[i+1][j-1]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i+2][j-2]) &&
                    equalChars.test(dnaMatrix[i][j], dnaMatrix[i+3][j-3]);
        }
        return false;
    }

}
