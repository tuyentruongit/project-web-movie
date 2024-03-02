package web.movie.web1.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import web.movie.web1.entity.User;

import java.util.Objects;

@Configuration
public class Author implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // lấu thông tin user từ trong session
        User user = (User) request.getSession().getAttribute("currentUser" );
        // nếu curentUsser = null => báo lỗi 401 return false;

        if (user == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
       String role = user.getRole().getValue();
        if(Objects.equals(role,"ADMIN")){
            return true;
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }
}
