package web.movie.web1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.User;
import web.movie.web1.exception.BadRequestException;
import web.movie.web1.model.Role;
import web.movie.web1.model.request.RegisterRequest;
import web.movie.web1.repository.UserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private  final BCryptPasswordEncoder passwordEncoder;


    public void register(RegisterRequest request) {
        // kiểm tra xem email đó đã tạo tài khoảng hay chưa
        List<User> userList = userRepository.findAll();
        userList.forEach(user -> {
            if (user.getEmail().equals(request.getEmail())){
                throw new BadRequestException("Email đã được sử dụng");
            }
        });

        // tạo user mới
        String avatarUrl = "https://pigment.github.io/fake-logos/logos/medium/color/2.png";
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .avatar(avatarUrl)
                .createAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        userRepository.save(user);
    }
}
