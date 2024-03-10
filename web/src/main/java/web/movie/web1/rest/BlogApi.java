package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.movie.web1.entity.Blog;
import web.movie.web1.model.request.UpsertBlogRequest;
import web.movie.web1.model.request.UpsertReviewRequest;
import web.movie.web1.service.BlogService;

@RestController
@RequestMapping("api/admin/blogs")
@RequiredArgsConstructor
public class BlogApi {
    private final BlogService blogService;
    @PostMapping
    public ResponseEntity<?> createBlog (@RequestBody UpsertBlogRequest upsertBlogRequest){
        Blog blog = blogService.createBlog(upsertBlogRequest);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateBlog (@PathVariable Integer id , @RequestBody UpsertBlogRequest upsertBlogRequest){
        Blog blog = blogService.updateBlog(id,upsertBlogRequest);
        return ResponseEntity.ok(blog);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id){
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
