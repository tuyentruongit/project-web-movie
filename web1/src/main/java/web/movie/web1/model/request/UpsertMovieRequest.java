package web.movie.web1.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import web.movie.web1.model.MovieType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertMovieRequest {
    String title;
    String description;
    Boolean status;
    MovieType movieType;
    Integer releaseYear;
    String poster;
    List<Integer> directorIds;
    List<Integer> actorIds;
    List<Integer> genreIds;
}