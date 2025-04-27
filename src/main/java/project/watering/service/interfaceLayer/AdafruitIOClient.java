package project.watering.service.interfaceLayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdafruitIOClient {
    private static final String USERNAME = "sonwoang";
    private static final String password = "aio_pAml68yWPAOJoQnHwCc1RPfWDxab";

    public static void sendData(String value, String feedName) {
        try {
            URL url = new URL("https://io.adafruit.com/api/v2/" + USERNAME + "/feeds/" + feedName + "/data");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-AIO-Key", password);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonPayload = "{\"value\": \"" + value + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                System.out.println("Gửi dữ liệu thành công!");
            } else {
                System.out.println("Lỗi gửi dữ liệu: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLatestData(String feedName) {
        try {
            URL url = new URL("https://io.adafruit.com/api/v2/" + USERNAME + "/feeds/" + feedName + "/data/last");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-AIO-Key", password);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                conn.disconnect();

                return response.toString(); // JSON chứa thông tin value, timestamp, id,...
            } else {
                System.out.println("Lỗi lấy dữ liệu: " + responseCode);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}