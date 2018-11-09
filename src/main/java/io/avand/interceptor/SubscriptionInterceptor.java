package io.avand.interceptor;

import io.avand.security.AuthoritiesConstants;
import io.avand.security.SecurityUtils;
import io.avand.service.SubscriptionService;
import io.avand.service.UserAuthorityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscriptionInterceptor implements HandlerInterceptor {

    @Autowired
    public SubscriptionService subscriptionService;
    @Autowired
    public UserAuthorityService userAuthorityService;
    @Autowired
    public SecurityUtils securityUtils;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Long userId = securityUtils.getCurrentUserId();
        try {
            subscriptionService.checkSubscription(userId);
            return true;
        } catch (NotFoundException e) {
            userAuthorityService.removeAuthority(AuthoritiesConstants.SUBSCRIPTION, userId);
            httpServletResponse.sendError(402, "Subscription Needed");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
