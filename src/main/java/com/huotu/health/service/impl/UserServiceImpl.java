package com.huotu.health.service.impl;

import com.huotu.common.base.RSAHelper;
import com.huotu.health.common.CookieHelper;
import com.huotu.health.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户服务实现
 * Created by slt on 2016/11/17.
 */
@Service
public class UserServiceImpl implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);
    private String userKey = "userId";
    private String loginApp = "loginApp";
    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK8Cd7gS6dOz3ALnICrlLOiGWv5RHgiQFvSsKMBudp59UDrsknSsaZsdBagrhMdtlKxYI1JzD2iJrGGBjPexvtGXVFrZjkLXZCdmeiM0L41m7VvkeI4ASD/4T3qxSjhMCRAVvJ0vC/sPffKR71In0hyUWMrFXCPR10zGUmcU9TwVAgMBAAECgYAeP1/vuZ0eUOTCv62onEmBus75S43UTwsYqLS2ZaEszV3TgVXiwnXSMFbs9PCTA1aB3w3jzy0nlTvs8lYp7VecWjG+rqayIZk2HtdKwNoEroqOPLgvDUTwxCC30CByZL4yb95XhNFBpd4p7cJLlPgf8M58WT7ttS3UquJDhYPYgQJBAPD8/HH07yMS17VfO6KwM7OCsnwUdrH3mGtK3ac86Z5xhelK4ikiKetu+1QFSeUOLm4Uv4K67c6lko+yPvmjqzUCQQC56VP0QhexQj4sylGtGSjqYGftRkkhbg6zHLURR0RMzTw+jXP8J6R0xTlIiBKJyK2xgAXuuqNUlyQVEJwCSolhAkEAmQ+F43c3P+ai3Q7MmMsjO1vCs25n6SciRts5JxRYKYtfC0rFlGyfhWpq9PWa9oHoWYCSFp1Vl4+wI9aJixM6FQJAA43vefsNgukWUTrpBts1Sg3fzsyKN2ZoR4pj99mZ97Hw1e1Ua1zCqyzeJIHdgN7iW0NsWZ0d5E8jdHel0/Fi4QJAJhb7Mt8pC5UCJNTJc1JQyzTUiZAvGr4EeTiAi0MCkPH85pOEr6ChMH4qI/51nTTqskoMJtd/xHxMDbEeXkrnAg==";
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvAne4EunTs9wC5yAq5Szohlr+UR4IkBb0rCjAbnaefVA67JJ0rGmbHQWoK4THbZSsWCNScw9oiaxhgYz3sb7Rl1Ra2Y5C12QnZnojNC+NZu1b5HiOAEg/+E96sUo4TAkQFbydLwv7D33yke9SJ9IclFjKxVwj0ddMxlJnFPU8FQIDAQAB";

    @Autowired
    private Environment env;

    @Override
    public Long getUserId(HttpServletRequest request) {
        if (env.acceptsProfiles("develop")) {
            return 97278L;//146 4471商户 王明
        } else {
            String encrypt = CookieHelper.get(request, userKey);
            try {
                Long userId = Long.parseLong(RSAHelper.decrypt(encrypt, privateKey));
                if (userId > 0) return userId;
                return null;
            } catch (Exception ex) {
                return null;
            }
        }
    }

    @Override
    public void setUserId(Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userId > 0) {
            String encrypt = RSAHelper.encrypt(userId.toString(), publicKey);

            CookieHelper.set(response, userKey, encrypt, request.getServerName(), 60 * 60 * 24 * 365);
//            CookieHelper.set(response, userKey, userId.toString(), request.getServerName(), 60 * 60 * 24 * 365);
        }

    }

    @Override
    public void setLoginType(HttpServletRequest request, HttpServletResponse response, String loginType) throws Exception {
        String encrypt = RSAHelper.encrypt(loginType, publicKey);
        CookieHelper.set(response, loginApp, encrypt, request.getServerName(), 60 * 60 * 24 * 365);

    }

    @Override
    public String getLoginType(HttpServletRequest request) throws Exception {
//        if (env.acceptsProfiles("develop")){
//            return "1";
//        }
        String encrypt = CookieHelper.get(request, loginApp);
        if (encrypt == null) {
            return "0";
        }
        try {
            String type = RSAHelper.decrypt(encrypt, privateKey);
            if ("1".equals(type)) {
                return type;
            }
            return "0";
        } catch (Exception ex) {
            return "0";
        }
    }

}
