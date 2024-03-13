package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Episode;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode,Integer> {
    List<Episode> findByMovie_IdOrderByDisplayOderAsc(Integer id);

    List<Episode> findByMovie_IdAndStatusOrderByDisplayOderAsc(Integer id, Boolean status);
}
