package web.movie.web1.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpsertBlogRequest {
    String  title ;
    String content;
    String description;
    Boolean status;
    String thumbnail;


}
