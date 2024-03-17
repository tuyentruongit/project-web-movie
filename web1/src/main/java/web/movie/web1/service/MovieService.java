package web.movie.web1.service;


import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.*;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertMovieRequest;
import web.movie.web1.repository.*;
import web.movie.web1.model.MovieType;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MovieService  {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;



    public List<Movie> getAllMovie(){
        return movieRepository.findMovieByStatus(true);
    }

    public List<Movie> getAllMovieForAdmin(){
        return movieRepository.findAll();
    }
    public List<Movie> getSingleMovie (){
        PageRequest pageRequest = PageRequest.of(0,4,Sort.by("RelishYear").descending());
        Page<Movie> moviePage = movieRepository.findByMovieTypeAndStatus(MovieType.PHIM_LE,true,pageRequest);
        return moviePage.getContent();
    }


    public List<Movie> getSeriesMovie() {
        Pageable pageable = PageRequest.of(0,4,Sort.by("RelishYear").descending());
        Page<Movie> moviePage = movieRepository.findByMovieTypeAndStatus(MovieType.PHIM_BO,true,pageable);
        return moviePage.getContent();
    }

    public List<Movie> getTheatricalMovies() {
        Pageable pageable = PageRequest.of(0,4,Sort.by("RelishYear").descending());
        Page<Movie> moviePage = movieRepository.findByMovieTypeAndStatus(MovieType.PHIM_CHIEU_RAP,true,pageable);
        return moviePage.getContent();
    }
    public Movie getMoviViewHight(){
        return movieRepository.findMovieByStatusOrderByViewDesc(true).get(0);
    }




    public List<Movie> getTopSingleMovie(){
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Movie movie = movieRepository.findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType.PHIM_LE,true).get(i);
            list.add(movie);
        }
        return list;
    }

    public List<Movie>  movieRecommentPageSingleMovie() {
        return movieRepository.findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType.PHIM_BO,true).stream()
                .limit(4)
                .toList();
    }
    public List<Movie>  movieSuggestionsPageSeriesMovie() {
        return movieRepository.findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType.PHIM_CHIEU_RAP,true).stream()
                .limit(4)
                .toList();
    }
    public List<Movie>  movieSuggestionsPageTheatricalMovie() {
        return movieRepository.findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType.PHIM_LE,true).stream()
                .limit(4)
                .toList();
    }

    public List<Movie> getTopSeriesMovie() {
        return movieRepository.findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType.PHIM_BO,true).stream()
                .limit(4)
                .toList();
    }


    public List<Movie> getTheatricalMoviesTop() {

        return movieRepository.findByMovieTypeAndStatusOrderByRelishYearDesc(MovieType.PHIM_CHIEU_RAP,true).stream()
                .limit(4)
                .toList();
    }

    public List<Movie> getFeaturedMovie() {
        return movieRepository.findMovieByStatusOrderByViewDesc(true).stream()
                .limit(4)
                .toList();
    }

    public List<Movie> top3RatingMovie() {

//        movieRepository.findByMovieTypeAndStatus(MovieType.PHIM_LE,true,s)
        return movieRepository.findByStatusOrderByRatingDesc(true).stream()
                .limit(3)
                .toList();
    }

    public Page<Movie> getMoviesByTypeAndStatus(MovieType movieType, Boolean status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending());
        return movieRepository.findByMovieTypeAndStatus(movieType, status, pageRequest);
    }

    public Movie createMovie(UpsertMovieRequest upsertMovieRequest) {
        // kiểm tra xem tên phim đó đã có hay chưa
        List<Movie> list = movieRepository.findAll();
        list.forEach(movie -> {
            if (movie.getTitle().equals(upsertMovieRequest.getTitle())){
                throw new RuntimeException("Tên Phim này đã có ");
            }
        });
        Slugify slugify = Slugify.builder().build();
        Boolean status = upsertMovieRequest.getStatus();
        LocalDate publishedAt = null;
        if (status){
            publishedAt=LocalDate.now();
        }
        List<Actor> actorList = actorRepository.findAllById(upsertMovieRequest.getActorIds());
        List<Director> directorList = directorRepository.findAllById(upsertMovieRequest.getDirectorIds());
        List<Genre> genreList = genreRepository.findAllById(upsertMovieRequest.getGenreIds());

        Movie movie = Movie.builder()
                .title(upsertMovieRequest.getTitle())
                .description(upsertMovieRequest.getDescription())
                .status(status)
                .slug(slugify.slugify(upsertMovieRequest.getTitle()))
                .movieType(upsertMovieRequest.getMovieType())
                .relishYear(upsertMovieRequest.getReleaseYear())
                .poster(upsertMovieRequest.getPoster())
                .actorList(actorList)
                .directorList(directorList)
                .genreList(genreList)
                .createAt(LocalDate.now())
                .updateAt(null)
                .publishedAt(publishedAt)
                .build();
       return movieRepository.save(movie);
    }

    public Movie updateMovie(Integer id, UpsertMovieRequest upsertMovieRequest) {
        // tìm kiếm phim theo id
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Cannot find Movie by Id : " + id));
        Slugify slugify = Slugify.builder().build();
        Boolean status = upsertMovieRequest.getStatus();
        LocalDate publishedAt = null;
        if (status){
            publishedAt=LocalDate.now();
        }
        List<Actor> actorList = actorRepository.findAllById(upsertMovieRequest.getActorIds());
        List<Director> directorList = directorRepository.findAllById(upsertMovieRequest.getDirectorIds());
        List<Genre> genreList = genreRepository.findAllById(upsertMovieRequest.getGenreIds());

        movie.setTitle(upsertMovieRequest.getTitle());
        movie.setDescription(upsertMovieRequest.getDescription());
        movie.setSlug(slugify.slugify(upsertMovieRequest.getTitle()));
        movie.setStatus(status);
        movie.setMovieType(upsertMovieRequest.getMovieType());
        movie.setRelishYear(upsertMovieRequest.getReleaseYear());
        movie.setPoster(upsertMovieRequest.getPoster());
        movie.setPublishedAt(publishedAt);
        movie.setDirectorList(directorList);
        movie.setActorList(actorList);
        movie.setGenreList(genreList);

        return movieRepository.save(movie);

    }

    public void deleteMovie(Integer id) {
       Movie movie = movieRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Cannot find by id :" + id));
       List<Review> reviewList = reviewRepository.findReviewByMovie_Id(id);
       reviewRepository.deleteAll(reviewList);
       movieRepository.delete(movie);

    }

    public Movie findMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Cannot find by id :" + id));
    }

    public List<Movie> findMovieNew() {
        LocalDate currentDate = LocalDate.now();
        List<Movie> movieListNew = new ArrayList<>();

        movieRepository.findAll().forEach(movie -> {
            if (movie.getCreateAt().getYear()==currentDate.getYear() && movie.getCreateAt().getMonth()==currentDate.getMonth()){
                movieListNew.add(movie);
            }
        });

        return movieListNew;

    }
}
