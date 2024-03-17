package web.movie.web1.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpsertEpisodeRequest {
    Integer idMovie;
    Boolean status ;
    String title;
    Integer displayOder;


}
