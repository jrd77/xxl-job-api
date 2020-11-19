package net.oschina.xxl.job.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @Classname XxlJobConfigKit
 * @Description
 * @Date 2020/11/17 13:59
 * @Author W.Z
 */
public class XxlJobConfigKit {

    private static Map<String, Object> configMap=null;

    static Map<String, Object> loginParamMap(){

        Assert.notEmpty(configMap,"plz use net.oschina.xxl.job.util.HttpKit.init(String) first");
        Map<String, Object> map=new HashMap<>();
        map.put("userName",configMap.get("xxl-job.user-name"));
        map.put("password",configMap.get("xxl-job.password"));
        return map;
    }


    public static String getConfigProperty(String key){

        Assert.notEmpty(configMap,"plz use net.oschina.xxl.job.util.HttpKit.init(String) first,look file [resources/xxl-job.setting]");
        return Optional.ofNullable(configMap.get(key)).get().toString();
    }

    static Map<String, Object> params(String baseXxlJobUrl,String userName,String password){

        if(configMap!=null){
            return configMap;
        }
        Assert.isTrue(StrUtil.isAllNotBlank(baseXxlJobUrl,userName,password),"the params must be not blank");

        configMap=new HashMap<>(16);
        configMap.put("xxl-job.url",baseXxlJobUrl);
        configMap.put("xxl-job.user-name",userName);
        configMap.put("xxl-job.password",password);
        return configMap;
    }
    static Map<String, Object> params(String settingPath){

        if(configMap!=null){
            return configMap;
        }
        configMap=new HashMap<>(16);
        Setting setting=new Setting(settingPath);
        final Set<Map.Entry<String, String>> entrySet = setting.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            configMap.put(entry.getKey(),entry.getValue());
        }
        return configMap;
    }

    static class XxlJobSetting {

        private String userName;

        private String password;


        public XxlJobSetting(String userName, String password) {
            this.userName = userName;
            this.password = password;
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
}
