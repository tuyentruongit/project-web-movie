package web.movie.web1.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.movie.web1.entity.Blog;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.User;
import web.movie.web1.service.BlogService;
import web.movie.web1.service.MovieService;
import web.movie.web1.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard")
public class DashboardController {
    private final MovieService movieService;
    private final UserService userService;
    private final BlogService blogService;

    @GetMapping()
    public String getPageDashBoard(Model model){
        List<Movie> movies = movieService.findMovieNew();
        List<Blog> blogs = blogService.findBlogNew();
        List<User> users = userService.findUserNew();

        model.addAttribute("movies" ,movies);
        model.addAttribute("blogs" ,blogs);
        model.addAttribute("users" ,users);
        List<Movie> movieAll = movieService.getAllMovie();
        List<Blog> blogAll = blogService.getAllBlog();
        List<User> userAll = userService.getAllUser();

        model.addAttribute("movieAll" ,movieAll);
        model.addAttribute("blogAll" ,blogAll);
        model.addAttribute("userAll" ,userAll);

        return "admin/dashboard/index";
    }



}
