package web.movie.web1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import web.movie.web1.entity.Blog;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.Review;
import web.movie.web1.model.MovieType;
import web.movie.web1.service.BlogService;
import web.movie.web1.service.MovieService;
import web.movie.web1.service.ReviewService;

import java.util.List;
import java.util.Objects;

@Controller
public class WebController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("posterBackground",movieService.getMoviViewHight());
        model.addAttribute("printSingleMovie",movieService.getSingleMovie());
        model.addAttribute("seriesMovie",movieService.getSeriesMovie());
        model.addAttribute("theatricalMovies",movieService.getTheatricalMovies());

        return "web/index";
    }



    //phim-le?page=1?size=12
    @GetMapping("/phim-le")
    public String getSingleMovie(Model model,
                                 @RequestParam(required = false,defaultValue = "1") Integer page,
                                 @RequestParam(required = false,defaultValue = "8") Integer size){

        Page<Movie> pageData = movieService.getMoviesByTypeAndStatus(MovieType.PHIM_LE,true,page,size);
        model.addAttribute("pageData",pageData);
        model.addAttribute("currentPage" ,page);
        model.addAttribute("singleMovieTop" , movieService.getTopSingleMovie());
        model.addAttribute("movieRecommend",movieService.movieRecommentPageSingleMovie());
        return "web/phim-le";
    }


    @GetMapping("/series-movie")
    public String getSeriesMovie(Model model,
                                 @RequestParam(required = false,defaultValue = "1") Integer page,
                                 @RequestParam(required = false,defaultValue = "8") Integer size){
        Page<Movie> dataPage = movieService.getMoviesByTypeAndStatus(MovieType.PHIM_BO,true,page,size);
        model.addAttribute("pageData" , dataPage);
        model.addAttribute("currentPage", page);

        model.addAttribute("seriesMovieTop" , movieService.getTopSeriesMovie());
        model.addAttribute("movieSuggestions",movieService.movieSuggestionsPageSeriesMovie());
        return "web/series-movie";
    }


    @GetMapping("/phim-chieu-rap")
    public String getTheatricalMovies(Model model,
                                      @RequestParam(required = false,defaultValue = "1")Integer page,
                                      @RequestParam(required = false,defaultValue = "8")Integer size){
        Page<Movie> pageData = movieService.getMoviesByTypeAndStatus(MovieType.PHIM_CHIEU_RAP,true,page,size);
        model.addAttribute("pageData" , pageData);
        model.addAttribute("currentPage", page);
        model.addAttribute("theatricalMoviesTop" , movieService.getTheatricalMoviesTop());
        model.addAttribute("movieSuggestions",movieService.movieSuggestionsPageTheatricalMovie());
        return "web/phim-chieu-rap";
    }

    @GetMapping("/phim/{id}/{slug}")
    public String moviesDetail(@PathVariable Integer id,@PathVariable String slug,Model model){
        Movie movie = movieService.getAllMovie().stream()
                .filter(s-> Objects.equals(s.getId(), id))
                .filter(sl-> slug.equals(sl.getSlug()))
                .findFirst()
                .orElse(null);

        List<Review> reviewList = reviewService.getReviewById(id);
        model.addAttribute("reviewList" , reviewList);
        model.addAttribute("movieDetail",movie);
        model.addAttribute("featuredMovie" ,movieService.getFeaturedMovie());
        model.addAttribute("top3RatingMovie" , movieService.top3RatingMovie());

        return "web/movies-detail";
    }


    @GetMapping("/tin-tuc")
    public String news (Model model,
                        @RequestParam(required = false,defaultValue = "1") Integer page,
                        @RequestParam(required = false,defaultValue = "10") Integer size){
        Page<Blog> pageData = blogService.getBlogByPublishYearAndStatus(true,page,size);
        List<Blog> originalList = pageData.getContent();

        List<Blog> firstSublist = originalList.subList(0, 3);
        List<Blog> secondSublist = originalList.subList(3, originalList.size());


        model.addAttribute("firstSublist" , firstSublist);
        model.addAttribute("secondSublist" , secondSublist);
        model.addAttribute("currentPage" , page);
        model.addAttribute("pageData" , pageData);


        return "web/tin-tuc";

    }
    @GetMapping("/tin-tuc/{id}/{slug}")
    public String newsDetail (Model model, @PathVariable Integer id ,@PathVariable String slug ){
         Blog blog = blogService.findAllBlog(true).stream()
                 .filter(blog1 -> blog1.getId()==id)
                 .filter(blog1 -> blog1.getSlug().equals(slug))
                 .findFirst()
                 .orElse(null);


         model.addAttribute("blog" ,blog);
         model.addAttribute("top3RatingMovie" , movieService.top3RatingMovie());
        return "web/news-detail";

    }

    @GetMapping("/log-in")
    public String logIn (Model model){
        return "web/log-in";

    }
    @GetMapping("/create-account")
    public String createAccount (Model model){
        return "web/create-account";

    }




}
