import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUrlConnection {

    public static String returnData;

    public static String get(String url) {
        try {
            // 目标URL
            URL path = new URL(url);

            // 打开连接
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) path.openConnection();

            // 设置请求方法
            connection.setRequestMethod("GET");

            // 接收响应码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 打印结果
            // System.out.println("get: " + response.toString());
            returnData = response.toString();

            // 断开连接
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnData;
    }

    public static String post(String url, String arg) {
        try {
            // 目标URL
            URL path = new URL(url);

            // 打开连接
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) path.openConnection();

            // 设置请求方法
            connection.setRequestMethod("POST");

            // 设置请求属性，例如：Content-Type 和其他自定义文件头
            //connection.setRequestProperty("Content-Type", "application/xml");
            //connection.setRequestProperty("Custom-Header", "header-value");

            connection.setDoOutput(true); // 设置允许输出

            //String postData = "parm="+ arg +"&key=value"; // POST请求的数据
            String postData = arg;
            byte[] outputInBytes = postData.getBytes("UTF-8");
            try(OutputStream os = connection.getOutputStream()) {
                os.write(outputInBytes);
            }

            // 接收响应码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 打印结果
            //System.out.println("post: " + response.toString());
            returnData = response.toString();

            // 断开连接
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnData;
    }


}