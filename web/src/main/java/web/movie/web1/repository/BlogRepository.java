package web.movie.web1.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Blog;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Integer> {
    Page<Blog> findBlogByStatus(Boolean status, Pageable pageable);
    List<Blog> findAllByStatus(Boolean status);

}
