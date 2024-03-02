package web.movie.web1.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.movie.web1.entity.Blog;
import web.movie.web1.service.BlogService;

import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final HttpSession session;

    @GetMapping
    public String viewHomePage(Model model){

       List<Blog> blogList= blogService.findAllBlogAdmin();
       model.addAttribute("blogList" , blogList);
        return "admin/blog/index";
    }
    @GetMapping("/owns-blog")
    public String viewOwnBlogPage(Model model ){
        List<Blog> blogListUser = blogService.findBlogByUser();
        model.addAttribute("blogListByUser" , blogListUser);
        return "admin/blog/own-blog";
    }
    @GetMapping("/create-blog")
    public String viewCreateBlogPage(){
        return "admin/blog/create";
    }
    @GetMapping("/{id}/detail-blog")
    public String viewDetailBlogPage(@PathVariable Integer id){
        return "admin/blog/detail";
    }
}
