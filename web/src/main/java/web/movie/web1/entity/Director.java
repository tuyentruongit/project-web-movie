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
@Table(name ="director")
@Entity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    @Column(columnDefinition = "TEXT" )
    String description;
    String avatar;
    Date birthday;
    Date createdAt;
    Date updatedAt;
}
