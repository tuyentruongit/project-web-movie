package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre,Integer> {
}
