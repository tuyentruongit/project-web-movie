package web.movie.web1.service;

import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Blog;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.User;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertBlogRequest;
import web.movie.web1.repository.BlogRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private  final  HttpSession session;
    public Page<Blog> getBlogByPublishYearAndStatus(Boolean status , Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page-1 , size, Sort.by("publishedAt").descending());
        return blogRepository.findBlogByStatus(status,pageRequest);
    }

    public List<Blog> findAllBlog(Boolean status) {
        return blogRepository.findAllByStatus(status);
    }
    public List<Blog> findAllBlogAdmin(){

        return blogRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Blog::getCreateAt, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public List<Blog> findBlogByUser( ) {
        User user = (User) session.getAttribute("currentUser");
       return blogRepository.findByUserEmailOrderByCreateAtDesc(user.getEmail());
    }
    public Blog createBlog(UpsertBlogRequest upsertBlogRequest) {
        User user = (User) session.getAttribute("currentUser");
        Slugify slugify = Slugify.builder().build();
        Boolean status = upsertBlogRequest.getStatus();
        LocalDate publishedAt = null ;
        if (status){
            publishedAt = LocalDate.now();
        }

        Blog blog = Blog.builder()
                .title(upsertBlogRequest.getTitle())
                .description(upsertBlogRequest.getDescription())
                .slug(slugify.slugify(upsertBlogRequest.getTitle()))
                .content(upsertBlogRequest.getContent())
                .thumbnail(upsertBlogRequest.getThumbnail())
                .status(upsertBlogRequest.getStatus())
                .createAt( LocalDate.now())
                .updateAt( LocalDate.now())
                .publishedAt(publishedAt)
                .user(user)
                .build();

       return blogRepository.save(blog);

    }

    public Blog updateBlog(Integer id, UpsertBlogRequest upsertBlogRequest) {


        // kiem tra blog co ton tai khong
        Blog blog =blogRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Cannot find blog by Id : " + id));
        Slugify slugify = Slugify.builder().build();
        Boolean status = upsertBlogRequest.getStatus();
        LocalDate publishedAt = null ;
        if (status){

            publishedAt = LocalDate.now() ;
        }
        blog.setContent(upsertBlogRequest.getContent());
        blog.setDescription(upsertBlogRequest.getDescription());
        blog.setStatus(upsertBlogRequest.getStatus());
        blog.setTitle(upsertBlogRequest.getTitle());
        blog.setThumbnail(upsertBlogRequest.getThumbnail());
        blog.setUpdateAt(LocalDate.now());
        blog.setPublishedAt(publishedAt);
        blog.setSlug(slugify.slugify(upsertBlogRequest.getTitle()));
        return blogRepository.save(blog);
    }

    public void deleteBlog(Integer id) {
        Blog blog =blogRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Cannot find blog by Id : " + id));
        blogRepository.delete(blog);
    }
    public List<Blog> findBlogNew() {
        LocalDate currentDate =LocalDate.now();
        List<Blog> blogListNew = new ArrayList<>();
        blogRepository.findAll().forEach(blog -> {
            if (blog.getCreateAt().getMonth()==currentDate.getMonth()&&blog.getCreateAt().getYear()==currentDate.getYear()){
                blogListNew.add(blog);
            }
        });
        return blogListNew;

    }

    public List<Blog> getAllBlog() {
       return blogRepository.findAll();
    }
}
