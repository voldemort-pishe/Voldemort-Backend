package hr.pishe.interceptor;

import hr.pishe.security.SecurityUtils;
import hr.pishe.service.UserStateService;
import hr.pishe.service.dto.UserStateDTO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneralInterceptor implements HandlerInterceptor {

    @Autowired
    private UserStateService userStateService;
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            UserStateDTO userStateDTO = userStateService.findByUserId(securityUtils.getCurrentUserId());
            if (userStateDTO != null)
                httpServletResponse.addHeader("X-User-State", userStateDTO.getState().name());
        } catch (NotFoundException ignore) {
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
