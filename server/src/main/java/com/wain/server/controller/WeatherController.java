package com.wain.server.controller;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wain.server.domain.Address;
import com.wain.server.utils.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WeatherController {

    private final Address addr = new Address();

    @ResponseBody
    @RequestMapping("/api/location")
    public String getLocation(HttpServletRequest req) {
        String path = "https://ipaddquery.api.bdymkt.com/ip/query";
        String resp = "";
        String ipAddress = "";
//        System.out.println(request);
        ipAddress = Tools.ipQuery(req);
        addr.setIp(ipAddress);
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, path);
        request.setCredentials("3b080821744c42949c41d7d761a4d826", "1d2cf92786ba4fb9b024b0dddbf5dd4b");
        request.addQueryParameter("ip", addr.getIp());
        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            resp = response.getResult();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(resp);
//            addr.setIp(resp);
            // 返回结果格式为Json字符串
            addr.setProvince(actualObj.get("data").get("region").textValue());
            addr.setCity(actualObj.get("data").get("city").textValue());
            System.out.println(addr);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return resp;
        }
    }


    @ResponseBody
    @RequestMapping("/api/weather")
    public Object weatherQuery() {
        String path = "http://gwgp-h4bqkmub7dg.n.bdcloudapi.com/day";
        String resp = "";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.GET, path);
        request.setCredentials("3b080821744c42949c41d7d761a4d826", "1d2cf92786ba4fb9b024b0dddbf5dd4b");

        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        // 设置query参数
        request.addQueryParameter("ip", addr.getIp());


        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            resp = response.getResult();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readTree(resp);
            addr.setAqi(Integer.parseInt(actualObj.get("air").textValue()));

            String[] fut_10_hour = new String[10];
            Integer[] fut_10_temp = new Integer[10];
            Integer[] fut_10_aqi = new Integer[10];
            for(int i = 0; i < 10; i++){
                fut_10_hour[i] = actualObj.get("hours").get(i).get("hours").textValue();
                fut_10_temp[i] = Integer.parseInt(actualObj.get("hours").get(i).get("tem").textValue());
                fut_10_aqi[i] = Integer.parseInt(actualObj.get("hours").get(i).get("aqinum").textValue());
            }
            addr.setFut_10_hour(fut_10_hour);
            addr.setFut_10_temp(fut_10_temp);
            addr.setFut_10_aqi(fut_10_aqi);
            System.out.println(addr);
//            System.out.println(actualObj.get("hours").getClass());
//            System.out.println(response.getResult());
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return addr;
        }
    }

    @ResponseBody
    @RequestMapping("/api/test")
    public Object test() {
        addr.setIp("113.200.174.6");
        addr.setProvince("陕西");
        addr.setCity("西安");
        addr.setAqi(53);
        addr.setFut_10_hour(new String[]{"现在",
                "05/11",
                "01:00",
                "02:00",
                "03:00",
                "04:00",
                "05:00",
                "06:00",
                "07:00",
                "08:00"});
        addr.setFut_10_temp(new Integer[]{
                15,
                17,
                17,
                16,
                15,
                15,
                15,
                15,
                16,
                16
        });
        addr.setFut_10_aqi(new Integer[]{
                50,
                44,
                44,
                44,
                44,
                44,
                44,
                44,
                46,
                46
        });
        return addr;
    }

}
