package web.movie.web1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.User;
import web.movie.web1.model.Role;
import web.movie.web1.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static web.movie.web1.model.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> findUserNew() {
        LocalDate currentDate = LocalDate.now();
        List<User> userListNew = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if (user.getCreateAt().getMonth()==currentDate.getMonth()&& user.getCreateAt().getYear()==currentDate.getYear()){
                userListNew.add(user);
            }
        });

        return userListNew;

    }

    public List<User> getAllUser() {
        return userRepository.findByRole(ROLE_USER);
    }
}
