package io.avand.interceptor;

import io.avand.service.CompanyService;
import io.avand.service.dto.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CompanyInterceptor implements HandlerInterceptor {

    @Autowired
    private CompanyService companyService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        List<CompanyDTO> companyDTOS = companyService.findAll();
        if (!companyDTOS.isEmpty()) {
            httpServletRequest.setAttribute("companyId", companyDTOS.get(0).getId());
            return true;
        } else {
            httpServletResponse.sendError(400, "You Should Create Company First");
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
