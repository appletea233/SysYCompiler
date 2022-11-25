package middleCode;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LabelManager {
    private static int labelCount = 0;
    public static String LOOP_START = "LOOP_START";
    public static String LOOP_END = "LOOP_END";
    public static String IF_START = "IF_START";
    public static String IF_END = "IF_END";
    public static String IF_OR_START = "IF_OR_START";
    public static String IF_AND_END = "IF_AND_END";

    static public Map<String, Integer> labelNum = Stream.of(new Object[][] {
            {LOOP_START, 0},
            {LOOP_END, 0},
            {IF_START, 0},
            {IF_END, 0},
            {IF_OR_START, 0},
            {IF_AND_END, 0},
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
    public static String getLabel() {
        return "label" + labelCount++;
    }
}
