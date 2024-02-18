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
@Table(name ="episode")
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    Integer displayOder;
    Boolean status;
    String videoUrl;
    Integer duration;


    Date birthday;
    Date createdAt;
    Date updatedAt;




}
