package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

public class Searcher implements Callable<String> {
    private final File file;
    private final Pattern[] PATTERNS;

    public Searcher(File file, Pattern[] patterns) {
        this.file = file;
        PATTERNS = patterns;
    }

    @Override
    public String call() {
        try {
            String fileStr = new String(Files.readAllBytes(file.toPath()));

            for (Pattern p : PATTERNS) {
                if (searchSubstring(p, fileStr)) {
                    return file.getName() + ": " + p.getType();
                }
            }
            return file.getName() + ": " + "Unknown file type";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean searchSubstring(Pattern pattern, String text) {
        if (pattern == null || text == null) {
            return false;
        }
        if (pattern.getCode().length() > text.length()) {
            return false;
        }
        if (text.length() == 0) {
            return true;
        }
        return findUsingRabinKarp(pattern, text);
    }

    private boolean findUsingKMP(Pattern pattern, String text) {
        String code = pattern.getCode();
        int len = code.length();
        for (int i = 0; i < text.length() - len + 1; i++) {
            if (text.charAt(i) == code.charAt(0)) {
                boolean isMatch = true;
                int k = i;
                for (int j = 0; j < len; j++) {
                    if (code.charAt(j) != text.charAt(k)) {
                        isMatch = false;
                        i += j - 1 - pattern.getPrefixFunction()[j - 1];
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

    private boolean findUsingRabinKarp(Pattern pattern, String text) {
        String code = pattern.getCode();
        int len = code.length();
        long patternHash = getPolynomialHash(code);
        for (int i = text.length() - len; i >= 0; i--) {
            String substring = text.substring(i, i + code.length());
            long subHash = getPolynomialHash(substring);
            if (patternHash == subHash && code.equals(substring)) {
                return true;
            }
        }
        return false;
    }

    private long getPolynomialHash(String code) {
        final int base = 256;
        final int prime = 101;
        long result = 0;
        for (int i = 0; i < code.length(); i++) {
            result += (int) code.charAt(i) * ((long) Math.pow(base, i));
        }
        return result % prime;
    }
}
