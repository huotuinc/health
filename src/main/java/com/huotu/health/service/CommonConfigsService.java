package com.huotu.health.service;

/**
 * 通用配置项服务
 * Created by slt on 2016/11/17
 */
public interface CommonConfigsService {

    /**
     * 网站地址
     *
     * @return
     */
    String getWebUrl();

    /**
     * 授权网站域名地址
     *
     * @return
     */
    String getAuthWebUrl();

    /**
     * 传输加密的密钥
     *
     * @return
     */
    String getAuthKeySecret();


    /**
     * appsecret用于mallapi
     *
     * @return
     */
    String getAppSecret();

    /**
     * appid 用于mallapi
     *
     * @return
     */
    String getAppId();


    /**
     * 商城mallapi地址
     *
     * @return
     */
    String getMallApiWebUrl();

    /**
     * 商城静态资源地址
     *
     * @return
     */
    String getResourceServerUrl();



    /**
     * huobanplus上传资源的服务地址
     * @return
     */
    String getResourcesHome();


    /**
     * huobanplus静态资源域名地址
     * @return
     */
    String getResourcesUri();

    /**
     * 短信设置
     * @return
     */
    String getNoteSendId();

    /**
     * 获取商户主域名
     * @return
     */
    String getMallDomain();

    /**
     * 资源地址
     *
     * @return
     */
    String getResoureServerUrl();
}
