package net.tomocraft.tempest.util;

import java.util.*;

public enum TextFormat {

    BLACK('0', 30),

    DARK_BLUE('1', 34),

    DARK_GREEN('2', 32),

    DARK_AQUA('3', 36),

    DARK_RED('4', 31),

    DARK_PURPLE('5', 35),

    GOLD('6', 33),

    GRAY('7', 37),

    DARK_GRAY('8', 37),

    BLUE('9', 34),

    GREEN('a', 32),

    AQUA('b', 36),

    RED('c', 31),

    LIGHT_PURPLE('d', 35),

    YELLOW('e', 33),

    WHITE('f', 0),

    BOLD('l', 1),

    ITALIC('o', 3),

    UNDERLINE('n', 4),

    RESET('r', 0);
    
    private static final char ESCAPE_CODE = '\u00A7';
    private static final String PC_RESET = "\u001b[00m";

    private static final int FLAG_RESET = 0;
    private static final int FLAG_FORMAT = -1;

    private char mcbeCode;
    private int pcCode;
    
    private static Map<Character, Integer> codeMap = new HashMap<>();
    private List<TextFormat> list = new ArrayList<>();

    static {
        for (TextFormat value : values()) {
            codeMap.put(value.mcbeCode, value.pcCode);
        }
    }

    TextFormat(char mcbeCode, int pcCode) {
        this.mcbeCode = mcbeCode;
        this.pcCode = pcCode;
        
        list.add(this);
    }

    /**
     * Convert mcbe text format (like §a) to PC color format(like \u001b[00;32m).
     * @param text targetText
     * @return formattedText
     */
    public static String toPCColor(String text) {
        for (Map.Entry<Character, Integer> entry : codeMap.entrySet()) {
            switch (entry.getValue()) {
                case FLAG_RESET:
                    text = text.replaceAll(new String(new char[]{ESCAPE_CODE, entry.getKey()}), PC_RESET);
                    break;

                case FLAG_FORMAT:
                    text = text.replaceAll(new String(new char[]{ESCAPE_CODE, entry.getKey()}), "");
                    break;

                    default:
                        text = text.replaceAll(new String(new char[]{ESCAPE_CODE, entry.getKey()}), "\u001b\\[00;" + entry.getValue() + "m");
                        break;
            }
        }
        return text + PC_RESET;
    }

    private String toMcbeCode() {
        return new String(new char[]{ESCAPE_CODE, mcbeCode});
    }

    @Override
    public String toString() {
        return toMcbeCode();
    }

}
