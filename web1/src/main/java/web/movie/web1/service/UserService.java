package web.movie.web1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.User;
import web.movie.web1.model.Role;
import web.movie.web1.repository.UserRepository;

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
        Date currentDate = new Date();
        List<User> userListNew = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(currentDate);
        userRepository.findAll().forEach(user -> {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(user.getCreateAt());
            if (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                    calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
                userListNew.add(user);
            }
        });
        return userListNew;

    }

    public List<User> getAllUser() {
        return userRepository.findByRole(ROLE_USER);
    }
}
