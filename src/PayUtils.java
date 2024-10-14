import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Random;

public class PayUtils {

    public static String returnData;

    public static String wxPay() {
        int int_totalFee = 1;
        String str_totalFee = Integer.toString(int_totalFee);
        String str_appId = "wx5b802fd017302604";
        String str_mchId = "1560976231";
        String str_body = "test";
        String str_nonceStr = "0000000000";
        String str_notifyUrl = "http://gw.paitu5.com/";
        String str_outTradeNo = "PT500000000101010101";
        String str_spbillCreateIp = "0.0.0.0";
        String str_tradeType = "APP";
        String str_signType = "MD5";

        //int int_timeStamp = 1617926800;
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime=LocalDateTime.now();
        long int_timeStamp = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        System.out.println(int_timeStamp);

        String str_prepayId = "";
        String str_packageValue = "Sign=WXPay";

        String str_key = "aJRKX6Q28HWRFYWDYHJK8HDH01234588";
        String str_sign = "7c31fc31d49b979ff390f7cc642c50e8";


        String randomString = "";
        // 生成一个[0, 10)之间的随机整数
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            randomString += random.nextInt(10);
        }
        str_nonceStr = randomString;
        System.out.println(str_nonceStr);

        str_outTradeNo = "PT5"
                + str_nonceStr.substring(0, 4)
                + Integer.toString(localDateTime.getYear()-2000)
                + Integer.toString(localDateTime.getMonthValue())
                + Integer.toString(localDateTime.getDayOfMonth())
                + Integer.toString(localDateTime.getHour())
                + Integer.toString(localDateTime.getMinute())
                + Integer.toString(localDateTime.getSecond());
        System.out.println(str_outTradeNo);

        String str_a = "";
        String stringA="appid="+ str_appId +"&body="+ str_body +"&mch_id="+ str_mchId +"&nonce_str="+ str_nonceStr +"&notify_url="+ str_notifyUrl +"&out_trade_no="+ str_outTradeNo +"&sign_type="+ str_signType +"&spbill_create_ip="+str_spbillCreateIp +"&total_fee="+ str_totalFee +"&trade_type="+ str_tradeType +"&key="+ str_key;
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(stringA.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            str_a = hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        System.out.println(str_a);
        String post_data = "<xml><appid>"+str_appId+"</appid><body>"+str_body+"</body><mch_id>"+str_mchId+"</mch_id><nonce_str>"+str_nonceStr+"</nonce_str><notify_url>"+str_notifyUrl+"</notify_url><out_trade_no>"+str_outTradeNo+"</out_trade_no><spbill_create_ip>"+str_spbillCreateIp+"</spbill_create_ip><total_fee>"+str_totalFee+"</total_fee><trade_type>"+str_tradeType+"</trade_type><sign_type>"+str_signType+"</sign_type><sign>"+str_a+"</sign></xml>";
        System.out.println(post_data);

        String result = HttpUrlConnection.post("https://api.mch.weixin.qq.com/pay/unifiedorder", post_data);
        System.out.println(result);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(result)));
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("xml");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                Element prepayIdElement = (Element) element.getElementsByTagName("prepay_id").item(0);
                str_prepayId = prepayIdElement.getTextContent();
                System.out.println("prepay_id: " + str_prepayId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String str_b = "";
        String stringB="appid="+ str_appId +"&noncestr=" + str_nonceStr + "&package="+ str_packageValue +"&partnerid=1560976231&prepayid=" + str_prepayId + "&timestamp="+ int_timeStamp +"&key="+ str_key;
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(stringB.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            str_b = hexString.toString().toUpperCase();
            returnData = hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        System.out.println(str_b);

        return returnData;
    }


}