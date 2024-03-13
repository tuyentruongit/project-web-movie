package web.movie.web1.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertEpisodeRequest {
    Integer idMovie;
    Boolean status ;
    String title;
    Integer displayOder;


}
