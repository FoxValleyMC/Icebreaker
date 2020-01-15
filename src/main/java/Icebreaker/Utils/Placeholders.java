package Icebreaker.Utils;

public class Placeholders {

    public static String Register(String rawString, String player, String message) {
        return rawString
                .replace("{player}", player)
                .replace("{msg}", message);
    }

}
