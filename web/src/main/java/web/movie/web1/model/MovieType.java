package web.movie.web1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovieType {
    PHIM_LE("Phim Lẻ"),
    PHIM_BO("Phim Bộ"),
    PHIM_CHIEU_RAP("Phim Chiếu Rạp");
    private final String value;
}
