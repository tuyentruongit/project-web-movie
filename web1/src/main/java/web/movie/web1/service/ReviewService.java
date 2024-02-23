package web.movie.web1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.Review;
import web.movie.web1.entity.User;
import web.movie.web1.exception.BadRequestException;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertReviewRequest;
import web.movie.web1.repository.MovieRepository;
import web.movie.web1.repository.ReviewRepository;
import web.movie.web1.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final MovieRepository movieRepository;
    public List<Review> getReviewById (Integer id){

     List<Review> reviewList = reviewRepository.findAll().stream()
             .filter(review -> Objects.equals(review.getMovie().getId(), id))
             .toList();
     return reviewList;
    }

    public Review createReiview(UpsertReviewRequest reviewRequest) {
        //TODO: Fix UserId = 1;
        Integer userId = 1;


        // Tìm kiếm user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFound("Cannot find user by Id : " + userId));

        // kiểm tra movie có tồn tại không

        Movie movie  = movieRepository.findById(reviewRequest.getMovieId())
                .orElseThrow(()-> new ResourceNotFound("Cannot find movie by Id : "+reviewRequest.getMovieId()));

        // tao review
        Review review = Review.builder()
                .comment(reviewRequest.getComment())
                .rating(reviewRequest.getRating())
                .user(user)
                .movie(movie)
                .build();

        return reviewRepository.save(review);
    }

    public Review updateReiview(Integer id, UpsertReviewRequest reviewRequest) {
        Integer userId = 1;
        // Tìm kiếm user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFound("Cannot find user by Id : " + userId));

        // kiểm tra movie có tồn tại không

        Movie movie  = movieRepository.findById(reviewRequest.getMovieId())
                .orElseThrow(()-> new ResourceNotFound("Cannot find movie by Id : "+reviewRequest.getMovieId()));
        // kiểm tra review có tồn tại hay không
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Canno find review by Id : " + id));

        // kiểm tra xem id của người đang thực hiện có phải của user đang log in hay không
        if (!review.getUser().getId().equals(user.getId())){
            throw new BadRequestException ("Bạn không có quyền truy cập ");
        }

        //kiểm tra xem review có thuộc movie này hay không
        if (!review.getMovie().getId().equals(movie.getId())){
            throw new BadRequestException("Review không thuộc bộ phim này ");
        }


        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());


        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        Integer userId = 1;
        // Tìm kiếm user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFound("Cannot find user by Id : " + userId));
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Canno find review by Id : " + id));

        // kiểm tra xem id của người đang thực hiện có phải của user đang log in hay không
        if (!review.getUser().getId().equals(user.getId())){
            throw new BadRequestException ("Bạn không có quyền chỉnh sửa review này  ");
        }
        reviewRepository.delete(review);
    }
}
