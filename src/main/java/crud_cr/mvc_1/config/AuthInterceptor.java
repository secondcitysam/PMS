package crud_cr.mvc_1.config;

import crud_cr.mvc_1.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;



public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception{


        HttpSession session = request.getSession(false);

        User user = (session!=null)
                ? (User) session.getAttribute("loggedInUser")
                :null;

        if(user==null)
        {
            response.sendRedirect("/login");

            return false;
        }

        return true;
    }

}
