package web.movie.web1.service;

import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Genre;
import web.movie.web1.entity.Movie;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertGenreRequest;
import web.movie.web1.repository.GenreRepository;
import web.movie.web1.repository.MovieRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    public Genre createGenre(UpsertGenreRequest request) {
        // kiêm tra xem tên thể loại đã có hay chưa
        List<Genre> genreList = genreRepository.findAll();
        genreList.forEach(genre -> {
            if (request.getName().equals(genre.getName())){
                throw new RuntimeException("Thể loại phim đã tồn tại");
            }
        });
        Slugify slugify = Slugify.builder().build();
        Genre genre = Genre.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .build();
        System.out.println(genre.getId());
        return  genreRepository.save(genre);
    }

    public void updateGenre(Integer id ,UpsertGenreRequest request) {
        Slugify slugify = Slugify.builder().build();
        Genre genre = genreRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("không thể tìm thấy thể loại trên"));


        if (genre.getName().equalsIgnoreCase(request.getName())){
            return;
        }
        genre.setName(request.getName());
        genre.setSlug(slugify.slugify(request.getName()));
        genreRepository.save(genre);

    }

    public void deleteGenre(Integer id) {



        Genre genre = genreRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("không thể tìm thấy thể loại trên"));
        genreRepository.delete(genre);
    }

    public List<Genre> getListGenre() {
        return genreRepository.findAll();
    }
}
