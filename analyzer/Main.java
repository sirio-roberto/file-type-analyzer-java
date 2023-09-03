package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length == 4) {
            String algorithm = args[0];
            String filePath = args[1];
            String patternStr = args[2];
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
        return true;
    }

    private static boolean findUsingNaive(String pattern, String text) {
        for (int i = 0; i < text.length() - pattern.length(); i++) {
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
