package web.movie.web1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Image {
    @Id

    String id;
    String name;
    String type;
    Double size;
    String url;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;



    LocalDate createAt;

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDate.now();
    }

}
