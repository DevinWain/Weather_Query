package com.wain.server.controller;

import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.AppSigner;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {
    @ResponseBody
    @RequestMapping("/weather")
    public String weatherQuery() {
        String path = "http://gwgp-h4bqkmub7dg.n.bdcloudapi.com/day";
        String resp = "";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.GET, path);
        request.setCredentials("3b080821744c42949c41d7d761a4d826", "1d2cf92786ba4fb9b024b0dddbf5dd4b");

        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/json; charset=utf-8");

        // 设置query参数
        request.addQueryParameter("ip", "110.232.1.23");


        ApiExplorerClient client = new ApiExplorerClient(new AppSigner());

        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
            resp = response.getResult();
//            System.out.println(response.getResult());
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return resp;
        }
    }

}
