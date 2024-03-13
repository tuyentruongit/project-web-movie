package web.movie.web1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.movie.web1.entity.Blog;
import web.movie.web1.entity.Movie;
import web.movie.web1.model.MovieType;
import web.movie.web1.repository.ActorRepository;
import web.movie.web1.repository.DirectorRepository;
import web.movie.web1.repository.EpisodeRepository;
import web.movie.web1.repository.GenreRepository;
import web.movie.web1.service.EpisodeService;
import web.movie.web1.service.ImageService;
import web.movie.web1.service.MovieService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private  final ImageService imageService;
    private final EpisodeService episodeService;
    @GetMapping
    public String viewHomePage(Model model){
        List<Movie> movieList = movieService.getAllMovieForAdmin();
        model.addAttribute("listMovie",movieList);
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String viewCreateBlogPage(Model model){

        model.addAttribute("images", imageService.getAllImageByCurrentUser());
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("movieTypes", MovieType.values());
        return "admin/movie/create";
    }
    @GetMapping("/{id}/detail")
    public String viewDetailBlogPage(@PathVariable Integer id , Model model){
        model.addAttribute("movie",movieService.findMovieById(id));
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("movieTypes", MovieType.values());
        model.addAttribute("images", imageService.getAllImageByCurrentUser());
        model.addAttribute("episodeList",episodeService.getEpisodeOfMovie(id));


        return "admin/movie/detail";
    }
}
