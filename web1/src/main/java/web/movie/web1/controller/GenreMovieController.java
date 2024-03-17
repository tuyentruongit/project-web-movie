package web.movie.web1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.movie.web1.entity.Genre;
import web.movie.web1.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/admin/genres")
@RequiredArgsConstructor
public class GenreMovieController {
    private final GenreService genreService;
    @GetMapping
    public String viewHomPage (Model model){
        List<Genre> genreList = genreService.getListGenre();
        model.addAttribute("genreList",genreList);
        return "admin/genre/index";
    }

}
