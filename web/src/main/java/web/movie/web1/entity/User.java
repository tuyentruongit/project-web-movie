package web.movie.web1.entity;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.mapping.List;
import web.movie.web1.model.Role;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user")
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    @Column(unique = true,nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    Role role;



    String password;
    String avatar;


    Date createAt;
    Date updateAt;




}
