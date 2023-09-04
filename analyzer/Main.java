package analyzer;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            App app = new App(args[0], args[1]);
            app.run();
        }
    }
}
