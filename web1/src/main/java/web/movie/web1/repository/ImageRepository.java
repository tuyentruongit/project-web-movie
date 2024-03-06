package web.movie.web1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,String> {
    List<Image> findByUser_IdOrderByCreateAtDesc(Integer id);
}
