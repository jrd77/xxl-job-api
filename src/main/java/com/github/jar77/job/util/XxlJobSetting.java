package com.github.jar77.job.util;

/**
 * @Classname XxlJobConfig
 * @Description  xxl_job配置参数
 * @Date 2020/11/17 13:51
 * @Author W.Z
 */

public class XxlJobSetting {

    private String baseUrl;

    private String userName;

    private String password;


    public XxlJobSetting(String baseUrl, String userName, String password) {
        this.baseUrl = baseUrl;
        this.userName = userName;
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
