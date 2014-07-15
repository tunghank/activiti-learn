package com.clt.system.util;

public class URLPostService {

	    public static void main(String args[]) {
	        try {
	            java.net.URL url = new java.net.URL(
	                    "http://10.200.10.79/AFExt/QuoProcessTrigger?PQSNo=CCL201109000001&OpenForm=true");
	            java.net.HttpURLConnection uc = (java.net.HttpURLConnection) url
	                    .openConnection();
	            uc.setRequestProperty("User-agent", "IE/6.0");
	            uc.setReadTimeout(30000);// 設定timeout時間
	            uc.connect();// 連線
	            System.out.println("網址/ip位置:"+java.net.Inet4Address.getByName(url.getHost()));
	            int status = uc.getResponseCode();
	            System.out.println(status);
	            switch (status) {
	            case java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT://504
	                System.out.println("連線網址逾時!");
	                break;
	            case java.net.HttpURLConnection.HTTP_FORBIDDEN://403
	                System.out.println("連線網址禁止!");
	                break;
	            case java.net.HttpURLConnection.HTTP_INTERNAL_ERROR://500
	                System.out.println("連線網址錯誤或不存在!");
	                break;
	            case java.net.HttpURLConnection.HTTP_NOT_FOUND://404
	                System.out.println("連線網址不存在!");
	                break;
	            case java.net.HttpURLConnection.HTTP_OK:
	                System.out.println("OK!");
	                break;
	 
	            }
	 
	        } catch (java.net.MalformedURLException e) {
	            System.out.println("網址格式錯誤!!!");
	            e.printStackTrace();
	        } catch (java.io.IOException e) {
	            System.out.println("連線有問題!!!!!!");
	            e.printStackTrace();
	        }
	    }

}
