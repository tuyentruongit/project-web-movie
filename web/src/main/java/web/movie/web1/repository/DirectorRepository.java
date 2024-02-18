package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Director;

public interface DirectorRepository extends JpaRepository<Director,Integer> {
}
