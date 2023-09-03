package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

public class Searcher implements Callable<String> {
    private File file;
    private String patternStr;
    private String resultingType;
    private int[] prefixFunc;

    public Searcher(File file, String patternStr, String resultingType, int[] prefixFunc) {
        this.file = file;
        this.patternStr = patternStr;
        this.resultingType = resultingType;
        this.prefixFunc = prefixFunc;
    }

    @Override
    public String call() throws Exception {
        try {
            String fileStr = new String(Files.readAllBytes(file.toPath()));

            if (searchSubstring(patternStr, fileStr)) {
                return file.getName() + ": " + resultingType;
            } else {
                return file.getName() + ": " + "Unknown file type";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean searchSubstring(String pattern, String text) {
        if (pattern == null || text == null) {
            return false;
        }
        if (pattern.length() > text.length()) {
            return false;
        }
        if (text.length() == 0) {
            return true;
        }
        return findUsingKMP(pattern, text);
    }

    private boolean findUsingKMP(String pattern, String text) {
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
}
