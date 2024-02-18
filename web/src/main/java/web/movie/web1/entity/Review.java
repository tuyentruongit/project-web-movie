package web.movie.web1.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
@Table(name ="review")
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition = "TEXT")
    String comment;

    Integer rating;
    Date createdAt;
    Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    @PrePersist
    void prePersist(){
        createdAt = new Date();
        updatedAt = new Date();
    }
    @PreUpdate
    void preUpdate(){
        updatedAt = new Date();
    }


}
