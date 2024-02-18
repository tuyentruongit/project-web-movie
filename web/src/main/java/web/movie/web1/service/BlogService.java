package web.movie.web1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Blog;
import web.movie.web1.repository.BlogRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    public Page<Blog> getBlogByPublishYearAndStatus(Boolean status , Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page-1 , size, Sort.by("publishedAt").descending());
        return blogRepository.findBlogByStatus(status,pageRequest);
    }

    public List<Blog> findAllBlog(Boolean status) {
        return blogRepository.findAllByStatus(status);
    }
}
