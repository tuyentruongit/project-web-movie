package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.movie.web1.entity.Movie;

import web.movie.web1.model.request.UpsertMovieRequest;

import web.movie.web1.service.MovieService;

@RestController
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;
    @PostMapping
    public ResponseEntity<?> createMovie (@RequestBody UpsertMovieRequest upsertMovieRequest){
        Movie movie = movieService.createMovie(upsertMovieRequest);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateMovie (@PathVariable Integer id , @RequestBody UpsertMovieRequest upsertMovieRequest){
        Movie movie = movieService.updateMovie(id,upsertMovieRequest);
        return ResponseEntity.ok(movie);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
