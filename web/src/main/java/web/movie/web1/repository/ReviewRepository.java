package web.movie.web1.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
}
