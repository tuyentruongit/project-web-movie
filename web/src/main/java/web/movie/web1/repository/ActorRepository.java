package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor,Integer> {
}
