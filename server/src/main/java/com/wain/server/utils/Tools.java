package com.wain.server.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class Tools {
    public static String ipQuery(HttpServletRequest request){
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
//            System.out.println(ipAddress);
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
//                System.out.println("1111");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
//                System.out.println("2222");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
//                System.out.println(ipAddress);
                if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                    // 根据网卡取本机配置的IP
                    try {
//                        ipAddress = InetAddress.getLocalHost().getHostAddress();
                        String ip = "http://pv.sohu.com/cityjson?ie=utf-8";
                        String inputLine = "";
                        String read = "";
                        String toIp="";
                        try {
                            URL url = new URL(ip);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            while ((read = in.readLine()) != null) {
                                inputLine += read;
                            }
                            String ObjJson=inputLine.substring(inputLine.indexOf("=")+1,inputLine.length()-1);
//            System.out.println(ObjJson);
                            ObjectMapper mapper = new ObjectMapper();
                            JsonNode actualObj = mapper.readTree(ObjJson);
                            toIp = actualObj.get("cip").textValue();
//            throw new Exception();
                        } catch (Exception e) {
                            toIp="";
                        }
//                        System.out.println(toIp);
                        ipAddress = toIp;
//                        System.out.println(InetAddress.getLocalHost().getHostAddress());
//                        System.out.println("locallocal!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null) {
                if (ipAddress.contains(",")) {
                    System.out.println("multiple!");
                    return ipAddress.split(",")[0];
                } else {
                    return ipAddress;
                }

            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
