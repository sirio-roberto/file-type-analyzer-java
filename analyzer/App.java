package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    private final int N_THREADS;
    private final Pattern[] PATTERNS;
    private final List<Future<String>> FUTURES;
    private final String folderPath;

    public App(String folderPath, String patternsPath) {
        N_THREADS = Runtime.getRuntime().availableProcessors();
        PATTERNS = getPatternsFromFile(patternsPath);
        FUTURES = new ArrayList<>();
        this.folderPath = folderPath;
    }

    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);

        File folder = new File(folderPath);
        File[] folderFiles = folder.listFiles();
        if (folderFiles != null) {
            for (File file : folderFiles) {
                Searcher searcher = new Searcher(file, PATTERNS);
                FUTURES.add(executor.submit(searcher));
            }
            executor.shutdown();
        }

        printFutures();
    }

    private Pattern[] getPatternsFromFile(String patternsPath) {
        Set<Pattern> patterns = new HashSet<>();
        try (Scanner fileScan = new Scanner(new File(patternsPath))) {
            while (fileScan.hasNextLine()) {
                String[] fields = fileScan.nextLine().replaceAll("\"", "").split(";");
                int priority = Integer.parseInt(fields[0]);
                String pattern = fields[1];
                String type = fields[2];
                patterns.add(new Pattern(priority, pattern, type));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return patterns.stream().sorted().toArray(Pattern[]::new);
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
}
