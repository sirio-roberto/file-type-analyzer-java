package analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    private final int N_THREADS;
    private final List<Future<String>> FUTURES;
    private String folderPath;
    private String patternStr;
    private String resultingType;

    public App(String folderPath, String patternStr, String resultingType) {
        N_THREADS = Runtime.getRuntime().availableProcessors();
        FUTURES = new ArrayList<>();
        this.folderPath = folderPath;
        this.patternStr = patternStr;
        this.resultingType = resultingType;
    }

    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);

        File folder = new File(folderPath);
        File[] folderFiles = folder.listFiles();
        if (folderFiles != null) {
            for (File file : folderFiles) {
                Searcher searcher = new Searcher(file, patternStr, resultingType, getPrefixFunction(patternStr));
                FUTURES.add(executor.submit(searcher));
            }
            executor.shutdown();
        }

        printFutures();
    }

    private void printFutures() {
        try {
            for (Future<String> f : FUTURES) {
                System.out.println(f.get());
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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
}
