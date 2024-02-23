package web.movie.web1.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import web.movie.web1.model.MovieType;

import java.util.Date;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
@Table(name ="movie")
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;
    String slug;
    @Column(columnDefinition = "TEXT" )
    String description;
    String poster;

    Integer relishYear;
    Integer view;

    Double rating;

    @Enumerated(EnumType.STRING)
    MovieType movieType;
    Boolean status;
    Date createAt;
    Date updateAt;
    Date publishedAt;


    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_actor")
    )
    List<Actor> actorList;
    @ManyToMany
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_director")
    )
    List<Director> directorList ;
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_genre")
    )
    List<Genre> genreList;
}
