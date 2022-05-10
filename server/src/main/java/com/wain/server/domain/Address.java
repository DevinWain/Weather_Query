package com.wain.server.domain;

import java.util.Arrays;


public class Address {
    private String ip;
    private String city;
    private String province;

    private Integer aqi;
    private String[] fut_10_hour;
    private Integer[] fut_10_temp;
    private Integer[] fut_10_aqi;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String[] getFut_10_hour() {
        return fut_10_hour;
    }

    public void setFut_10_hour(String[] fut_10_hour) {
        this.fut_10_hour = fut_10_hour;
    }

    public Integer[] getFut_10_temp() {
        return fut_10_temp;
    }

    public void setFut_10_temp(Integer[] fut_10_temp) {
        this.fut_10_temp = fut_10_temp;
    }

    public Integer[] getFut_10_aqi() {
        return fut_10_aqi;
    }

    public void setFut_10_aqi(Integer[] fut_10_aqi) {
        this.fut_10_aqi = fut_10_aqi;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ip='" + ip + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", aqi=" + aqi +
                ", fut_10_hour=" + Arrays.toString(fut_10_hour) +
                ", fut_10_temp=" + Arrays.toString(fut_10_temp) +
                ", fut_10_aqi=" + Arrays.toString(fut_10_aqi) +
                '}';
    }
}
