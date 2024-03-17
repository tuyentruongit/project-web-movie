package web.movie.web1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
@Table(name ="videos")
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Video {
    @Id
    String id;
    String name;
    String type;
    Double size;
    String url;
    Integer duration;


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
