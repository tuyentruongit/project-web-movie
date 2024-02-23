package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.User;
import web.movie.web1.model.Role;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByRole(Role role);

}
