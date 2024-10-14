
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // HttpURLConnection请求
        httpUrlconnection();

        // 支付功能
        payUtils();
    }

    public static void httpUrlconnection() {
        String result;
        result = HttpUrlConnection.get("http://www.girlsvo.com/Api/api_request.php?parm=1");
        System.out.println(result);

        result = HttpUrlConnection.post("http://www.girlsvo.com/Api/api_request.php", "parm=2");
        System.out.println(result);
    }

    public static void payUtils() {
        String result;
        result = PayUtils.wxPay();
        System.out.println(result);
    }


}