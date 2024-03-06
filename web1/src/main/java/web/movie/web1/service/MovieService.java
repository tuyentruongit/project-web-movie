package web.movie.web1.service;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.movie.web1.repository.MovieRepository;
import web.movie.web1.entity.Movie;
import web.movie.web1.model.MovieType;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService  {
    private final MovieRepository movieRepository;

    public List<Movie> getAllMovie(){
        return movieRepository.findMovieByStatus(true);
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

}
