package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Video;

public interface VideoRepository extends JpaRepository<Video,String> {

}
