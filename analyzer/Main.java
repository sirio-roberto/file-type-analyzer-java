package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 4) {
            String algorithm = args[0];
            String filePath = args[1];
            String patternStr = getPattenFromArg(args[2]);
            String resultingType = args[3];

            try {
                long startTime = System.currentTimeMillis();

                String fileStr = new String(Files.readAllBytes(Paths.get(filePath)));

                if (isSubstring(patternStr, fileStr, isKMP(algorithm))) {
                    System.out.println(resultingType);
                } else {
                    System.out.println("Unknown file type");
                }
                long endTime = System.currentTimeMillis();
                System.out.printf("It took %.3f seconds\n", (endTime - startTime) / 1000.0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getPattenFromArg(String arg) {
        return arg.substring(arg.lastIndexOf('%'));
    }

    private static boolean isKMP(String algorithm) {
        return algorithm.toUpperCase().contains("KMP");
    }

    // isKMP will define it we will use KMP or naive algorithms
    private static boolean isSubstring(String pattern, String text, boolean isKMP) {
        if (pattern == null || text == null) {
            return false;
        }
        if (pattern.length() > text.length()) {
            return false;
        }
        if (text.length() == 0) {
            return true;
        }
        if (isKMP) {
            return findUsingKMP(pattern, text);
        } else {
            return findUsingNaive(pattern, text);
        }
    }

    private static boolean findUsingKMP(String pattern, String text) {
        int[] prefixFunc = getPrefixFunction(pattern);

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            if (text.charAt(i) == pattern.charAt(0)) {
                boolean isMatch = true;
                int k = i;
                for (int j = 0; j < pattern.length(); j++) {
                    if (pattern.charAt(j) != text.charAt(k)) {
                        isMatch = false;
                        i += j - 1 - prefixFunc[j - 1];
                        break;
                    }
                    k++;
                }
                if (isMatch) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int[] getPrefixFunction(String text) {
        int[] function = new int[text.length()];
        for (int i = 0; i < function.length; i++) {
            int maxPrefix = 0;
            String substring = text.substring(0, i + 1);
            for (int j = 0; j < substring.length(); j++) {
                if (substring.substring(0, j).equals(substring.substring(substring.length() - j))) {
                    maxPrefix = j;
                }
            }
            function[i] = maxPrefix;
        }
        return function;
    }

    private static boolean findUsingNaive(String pattern, String text) {
        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            if (text.charAt(i) == pattern.charAt(0)) {
                boolean isMatch = true;
                for (char c : pattern.toCharArray()) {
                    if (c != text.charAt(i)) {
                        isMatch = false;
                        break;
                    }
                    i++;
                }
                if (isMatch) {
                    return true;
                }
            }
        }
        return false;
    }
}
