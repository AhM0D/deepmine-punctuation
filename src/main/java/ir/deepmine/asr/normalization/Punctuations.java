package ir.deepmine.asr.normalization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Punctuations {

    private static Punctuations instance = null;
    private final HashMap<String, Tuple2<String, Pattern>> map;
    private final ArrayList<Tuple2<String, Pattern>> patterns;
    private final boolean fixLineSeparator;
    private final String lineSeparator;
    private final Pattern spacePattern;
    private final Pattern newLinePattern;

    private Punctuations() {
        fixLineSeparator = !System.lineSeparator().equals("\n");
        lineSeparator = System.lineSeparator();
        map = new HashMap<>();
        patterns = new ArrayList<>(32);
        spacePattern = Pattern.compile(" +");
        newLinePattern = Pattern.compile(" *\n *");
    }

    public static synchronized Punctuations getInstance() {
        if (instance == null) {
            instance = new Punctuations();
        }
        return instance;
    }

    public boolean add(String wordSequence, String punctuation){
        // wordSequence = wordSequence.replace("‌", " ");
        Tuple2<String, Pattern> value = map.get(wordSequence);
        if (value != null && value.Item1.equals(punctuation)) {
            return false;
        }
        if (value != null) {
            patterns.remove(value);
        }
        StringBuilder builder = new StringBuilder(wordSequence.length());
        String[] words = wordSequence.split(" ");
        builder.append(" ?");
        builder.append(words[0]);
        for (int i = 1; i < words.length; i++) {
            builder.append("( |‌)?");
            builder.append(words[i]);
        }
        builder.append(" ?");
        Tuple3<String, Pattern, String> tuple = new Tuple3<>(punctuation, Pattern.compile(builder.toString()),
                wordSequence);
        int lowerIdx = -1, upperIdx = patterns.size();
        for (int i = 0; i < patterns.size(); i++) {
            Tuple3<String, Pattern, String> t = (Tuple3<String, Pattern, String>) patterns.get(i);
            if (t.Item3.contains(wordSequence))
                lowerIdx = i;
            if (wordSequence.contains(t.Item3) && upperIdx > i)
                upperIdx = i;
        }
        assert lowerIdx < upperIdx;
        patterns.add(lowerIdx + 1, tuple);
        map.put(wordSequence, tuple);
        return true;
    }

    public boolean remove(String wordSequence){
        wordSequence = wordSequence.replace("‌", " ");
        Tuple2<String, Pattern> value = map.get(wordSequence);
        if (value == null) {
            return false;
        }
        patterns.remove(value);
        map.remove(wordSequence);
        return true;
    }

    public String apply(String input) {
        String output = input;
        for (Tuple2<String, Pattern> tuple : patterns) {
            output = tuple.Item2.matcher(output).replaceAll(tuple.Item1);
        }
        output = spacePattern.matcher(output).replaceAll(" ");
        output = newLinePattern.matcher(output).replaceAll("\n");
        if (fixLineSeparator) {
            output = output.replace("\n", lineSeparator);
        }
        return output;
    }
}
