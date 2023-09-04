package analyzer;

public class Pattern implements Comparable<Pattern> {
    private final int priority;
    private final String code;
    private final String type;
    private int[] prefixFunction;

    public Pattern(int priority, String code, String type) {
        this.priority = priority;
        this.code = code;
        this.type = type;
        setPrefixFunction();
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public int[] getPrefixFunction() {
        return prefixFunction;
    }

    private void setPrefixFunction() {
        prefixFunction = new int[code.length()];
        for (int i = 0; i < prefixFunction.length; i++) {
            int maxPrefix = 0;
            String substring = code.substring(0, i + 1);
            for (int j = 0; j < substring.length(); j++) {
                if (substring.substring(0, j).equals(substring.substring(substring.length() - j))) {
                    maxPrefix = j;
                }
            }
            prefixFunction[i] = maxPrefix;
        }
    }

    @Override
    public int compareTo(Pattern o) {
        return Integer.compare(o.priority, this.priority);
    }
}
