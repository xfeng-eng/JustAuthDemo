package com.xiongfeng.justauthtest.controller;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: JustAuthTest
 * @description: 使用JustAuth实现第三方登录
 * @author: xiongfeng
 * @create: 2022-09-17 00:03
 **/
@RestController()
@RequestMapping("/oauth")
public class JustAuthController {
    /**
     * 通过JustAuth的AuthRequest拿到第三方的授权链接，并跳转到该链接页面
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/render/{source}")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }

    /**
     * 用户在确认第三方平台授权（登录）后， 第三方平台会重定向到该地址，并携带code、state等参数
     * authRequest.login通过code向第三方请求用户数据
     *
     * @param callback 第三方回调时的入参
     * @return 第三方平台的用户信息
     */
    @RequestMapping("/callback/{source}")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }

    /**
     * 创建授权request
     *
     * @return AuthRequest
     */
    private AuthRequest getAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId("19528e9e6505ce7028817f601b1a3a531776a1bf89020e9e35a5fdbe520c6085")
                .clientSecret("cadf7241b4a829b4e07d3dc1e60f74f2e5225b568e067294b5a76d28beaff03e")
                .redirectUri("http://localhost:8080/oauth/callback/gitee")
                .build());
    }


}
