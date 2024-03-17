package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.movie.web1.entity.Genre;
import web.movie.web1.model.request.UpsertGenreRequest;
import web.movie.web1.repository.GenreRepository;
import web.movie.web1.service.GenreService;

@RestController
@RequestMapping("/api/admin/genre")
@RequiredArgsConstructor
public class GenreApi {
    private final GenreService genreService;
    @PostMapping
    public ResponseEntity<?> createGenre(@RequestBody UpsertGenreRequest request){
        Genre genre = genreService.createGenre(request);
        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable Integer id, @RequestBody UpsertGenreRequest request){
        genreService.updateGenre(id,request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Integer id){
         genreService.deleteGenre(id);
        return  ResponseEntity.noContent().build();
    }
}
