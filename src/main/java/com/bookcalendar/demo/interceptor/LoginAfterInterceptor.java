package com.bookcalendar.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginAfterInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("member") != null ) {
            response.sendRedirect(request.getContextPath() + "/");
            // getContextPath() 프로젝트 path 가져옴
            return false; // 컨트롤러에 진입하지 않음!
        }
        return true; //정상적으로 진행
    }
}
