package project.watering.util;

public class DataFromFeed {
    public static String getOnlyValue(String input) {
        String key = "\"value\":\"";
        int startIndex = input.indexOf(key);
        if (startIndex == -1)
            return null;

        startIndex += key.length(); // Di chuyển đến sau dấu "
        int endIndex = input.indexOf("\"", startIndex); // Tìm dấu " kết thúc
        if (endIndex == -1)
            return null;

        return input.substring(startIndex, endIndex);
    }
}
