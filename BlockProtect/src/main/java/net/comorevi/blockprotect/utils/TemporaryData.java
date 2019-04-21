package net.comorevi.blockprotect.core;

import java.util.ArrayList;
import java.util.List;

public class TemporaryData {

    private static List<String> players;

    static {
        players = new ArrayList<>();
    }

    public static void addLogCPList(String name) {
        players.add(name);
    }

    public static void removeLogCPList(String name) {
        players.remove(name);
    }

    public static boolean containsLogCPList(String name) {
        return players.contains(name);
    }

}
