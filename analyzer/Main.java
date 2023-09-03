package analyzer;

public class Main {
    public static void main(String[] args) {
        if (args.length == 3) {
            App app = new App(args[0], args[1], args[2]);
            app.run();
        }
    }
}
