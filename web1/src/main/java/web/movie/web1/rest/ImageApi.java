package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.movie.web1.service.ImageService;

@RestController
@RequestMapping("api/admin/images")
@RequiredArgsConstructor
public class ImageApi {
    private final ImageService imageService;
    @GetMapping()
    public ResponseEntity<?> getAllImageByCurrentUser(){
        return ResponseEntity.ok(imageService.getAllImageByCurrentUser());
    }

    @PostMapping()
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(imageService.upLoadImage(file), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id){
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
