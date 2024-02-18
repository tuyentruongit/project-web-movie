package web.movie.web1.repository;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.movie.web1.entity.Movie;
import web.movie.web1.model.MovieType;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie , Integer>  {
    List<Movie> findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType movitype,Boolean status);
    List<Movie> findByMovieTypeAndStatusOrderByViewDesc(MovieType movitype,Boolean status);

    List<Movie> findMovieByStatusOrderByViewDesc(Boolean status);
    List<Movie> findMovieByStatus(Boolean status);
    List<Movie> findByStatusOrderByRatingDesc(Boolean status);


    //Tìm kiếm move theo type và status (phân trang);
    Page<Movie> findByMovieTypeAndStatus (MovieType movieType, Boolean status , Pageable pageable);






}
