package web.movie.web1.service;

import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Blog;
import web.movie.web1.entity.User;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertBlogRequest;
import web.movie.web1.repository.BlogRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        System.out.println(user);
        Slugify slugify = Slugify.builder().build();
        Boolean status = upsertBlogRequest.getStatus();
        Date publishedAt = null ;
        if (status){
            publishedAt = new Date();
        }

        Blog blog = Blog.builder()
                .title(upsertBlogRequest.getTitle())
                .description(upsertBlogRequest.getDescription())
                .slug(slugify.slugify(upsertBlogRequest.getTitle()))
                .content(upsertBlogRequest.getContent())
                .status(upsertBlogRequest.getStatus())
                .createAt( new Date())
                .updateAt( new Date())
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
        Date publishedAt = null ;
        if (status){
            publishedAt = new Date();
        }
        blog.setContent(upsertBlogRequest.getContent());
        blog.setDescription(upsertBlogRequest.getDescription());
        blog.setStatus(upsertBlogRequest.getStatus());
        blog.setTitle(upsertBlogRequest.getTitle());
        blog.setUpdateAt(new Date());
        blog.setPublishedAt(publishedAt);
        blog.setSlug(slugify.slugify(upsertBlogRequest.getTitle()));
        return blogRepository.save(blog);
    }

    public void deleteBlog(Integer id) {
        Blog blog =blogRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Cannot find blog by Id : " + id));
        blogRepository.delete(blog);
    }

}
