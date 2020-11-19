package net.oschina.xxl.job.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.*;
import cn.hutool.json.JSONUtil;
import net.oschina.xxl.job.api.entity.ReturnT;
import net.oschina.xxl.job.constant.*;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @Classname JobUtil
 * @Description http请求工具
 * @Date 2020/11/17 14:05
 * @Author W.Z
 */
public class HttpKit {

    private static Logger logger =Logger.getLogger(HttpKit.class.getName());

    private static final String SEMICOLON = ";";

    private static String token = null;

    public static <T> T postFormObj(String url, Map<String, Object> map, Class<T> clazz) {

        final String responseBody = postForm(url, map);
        return JSONUtil.toBean(responseBody, clazz);
    }

    public static <T> T postJsonObj(String url, Map<String, Object> map, Class<T> clazz) {

        final String responseBody = postJson(url, map);
        return JSONUtil.toBean(responseBody, clazz);
    }

    public static String postJson(String url, Map<String, Object> map) {

        final HttpRequest http = HttpUtil.createPost(url);
        http.body(JSONUtil.toJsonStr(map));
        http.contentType(ContentType.JSON.getValue());
        return handleRequest(url, map, http);
    }

    private static String handleRequest(String url, Map<String, Object> map, HttpRequest http) {

        Optional.ofNullable(token).ifPresent(x -> http.header(Header.COOKIE.getValue(), Keys.SIGN+token));
        final HttpResponse execute = http.execute();
        Assert.isTrue(execute.isOk(),
                "network request error: url:{},param:{},response body:{},response status:{}",
                url,
                JSONUtil.toJsonStr(map),
                execute.body(),
                execute.getStatus());
        return execute.body();
    }

    public static String postForm(String url, Map<String, Object> map) {

        final HttpRequest http = HttpUtil.createPost(url);
        http.form(map);
        http.contentType(ContentType.FORM_URLENCODED.getValue());
        return handleRequest(url, map, http);
    }


    public static String init(String baseXxlJobUrl,String userName,String password) {

        if (token != null) {
            return token;
        }
        logger.info("xxl-job-api init start");
        XxlJobConfigKit.params(baseXxlJobUrl,userName,password);
        getLoginToken();
        logger.info("xxl-job-api init completed");
        return token;
    }
    /**
     * 初始化配置
     *
     * @param configPath
     * @return
     */
    public static String init(String configPath) {

        if (token != null) {
            return token;
        }
        logger.info("xxl-job-api init start");
        XxlJobConfigKit.params(configPath);
        getLoginToken();
        logger.info("xxl-job-api init completed");
        return token;
    }
    private static void getLoginToken(){

        logger.info("get access token start");
        final Map<String, Object> loginParam = XxlJobConfigKit.loginParamMap();
        final String xxlUrl = XxlJobConfigKit.getConfigProperty("xxl-job.url");
        final HttpRequest http = HttpUtil.createPost(xxlUrl + UrlConstant.URL_LOGIN);
        http.form(loginParam);
        final HttpResponse response = http.execute();
        Assert.isFalse(ObjectUtil.isNull(response) || !response.isOk(), "请求地址错误");

        final String body = response.body();
        final ReturnT returnT = JSONUtil.toBean(body, ReturnT.class);
        Assert.isTrue(returnT.getCode()==ReturnT.SUCCESS_CODE,returnT.getMsg());

        final String header = response.header(Header.SET_COOKIE);
        Assert.isTrue(header.contains(SEMICOLON), "不存在token标志");
        String xxlJobToken;
        if (StrUtil.contains(header, SEMICOLON)) {
            xxlJobToken = StrUtil.subBetween(header, Keys.SIGN, SEMICOLON);
        } else {
            xxlJobToken = StrUtil.subAfter(header, Keys.SIGN, false);
        }
        logger.info("get access token end:"+xxlJobToken);
        token = xxlJobToken;
    }
}
