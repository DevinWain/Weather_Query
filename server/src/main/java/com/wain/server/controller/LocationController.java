package com.wain.server.controller;

import com.wain.server.utils.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LocationController {
    @ResponseBody
    @RequestMapping("/location")
    public String getLocation(HttpServletRequest req) {
        String path = "https://ipaddquery.api.bdymkt.com/ip/query";
        String resp = "";
        String ipAddress = "";
//        System.out.println(request);
        ipAddress = Tools.ipQuery(req);
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, path);
        request.setCredentials("3b080821744c42949c41d7d761a4d826", "1d2cf92786ba4fb9b024b0dddbf5dd4b");
        request.addQueryParameter("ip", ipAddress);
        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            resp = response.getResult();
            // 返回结果格式为Json字符串
//            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return resp;
        }
    }

}
