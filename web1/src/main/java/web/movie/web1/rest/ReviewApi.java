package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.movie.web1.entity.Review;
import web.movie.web1.exception.BadRequestException;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertReviewRequest;
import web.movie.web1.model.respone.ErrorResponse;
import web.movie.web1.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApi {

    private final ReviewService reviewService;
    // Create Review - Post
    @PostMapping()
    public ResponseEntity<?> createReview(@RequestBody UpsertReviewRequest reviewRequest){
        Review review = reviewService.createReiview(reviewRequest);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    //Update Review - Put
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id,@RequestBody UpsertReviewRequest reviewRequest){
        Review review = reviewService.updateReiview(id,reviewRequest);
        return ResponseEntity.ok(review);
//        try{
//            Review review = reviewService.updateReiview(id,reviewRequest);
//            return ResponseEntity.ok(review);
//
//        }catch (ResourceNotFound e){
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,e.getMessage());
//            return  new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
//        }
//        catch (BadRequestException e){
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage());
//            return  new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
//        }

    }

    // Delete Review- Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build() ;

    }



}
