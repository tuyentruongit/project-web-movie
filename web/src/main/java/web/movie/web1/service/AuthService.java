package web.movie.web1.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.User;
import web.movie.web1.exception.BadRequestException;
import web.movie.web1.model.request.LoginRequest;
import web.movie.web1.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;

    public void login(LoginRequest loginRequest) {

        // kiếm tra email có trùng hay không

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->new BadRequestException("Tài khoản hoặc mật khẩu không chính xác"));
        // kiểm tra password
        if (!passwordEncoder.matches(loginRequest.getPassword() , user.getPassword())){
            throw new  BadRequestException("Tài khoản hoặc mật khẩu không chính xác");
        }
        // lưu  thông tin user vào trong sesion
        session.setAttribute("currentUser",user);
    }
}
