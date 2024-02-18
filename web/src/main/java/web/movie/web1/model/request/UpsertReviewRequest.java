package web.movie.web1.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertReviewRequest {
    Integer ratting;
    String comment;
    Integer movieId;
}
