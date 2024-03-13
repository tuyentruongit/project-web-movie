package web.movie.web1.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.movie.web1.service.ImageService;
import web.movie.web1.service.VideoService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VideoApi {
    private final VideoService videoService;
//    @PostMapping("/admin/videos")
//    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file){
//        return new ResponseEntity<>(videoService.upLoadVideo(file), HttpStatus.CREATED);
//    }
    @DeleteMapping("/admin/videos/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id){
        videoService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/videos/{fileName}", produces = "application/octet-stream")
    public ResponseEntity<ResourceRegion> streamVideo(@PathVariable String fileName, HttpServletRequest request) throws IOException, IOException {
        long rangeStart = 0;
        long rangeEnd = Long.MAX_VALUE;

        String rangeHeader = request.getHeader("Range");
        if (rangeHeader != null && rangeHeader.contains("bytes=")) {
            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            rangeStart = Long.parseLong(ranges[0]);
            rangeEnd = ranges.length > 1 ? Long.parseLong(ranges[1]) : Long.MAX_VALUE;
        }

        ResourceRegion resourceRegion = videoService.getVideoResourceRegion(fileName, rangeStart, rangeEnd);

        return ResponseEntity
                .status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resourceRegion);
    }

}
