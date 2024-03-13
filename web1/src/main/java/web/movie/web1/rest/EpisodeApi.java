package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.movie.web1.entity.Episode;
import web.movie.web1.model.request.UpsertEpisodeRequest;
import web.movie.web1.repository.EpisodeRepository;
import web.movie.web1.service.EpisodeService;

@RestController()
@RequestMapping("/api/admin/episodes")
@RequiredArgsConstructor
public class EpisodeApi {
    private final EpisodeService episodeService;
    @PostMapping
    public ResponseEntity<?> createEpisode(@RequestBody UpsertEpisodeRequest upsertEpisodeRequest){
        Episode episode = episodeService.createEpisode(upsertEpisodeRequest);
        return new ResponseEntity<>(episode, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/upload-video")
    public ResponseEntity<?> uploadVideo (@PathVariable Integer id , @RequestParam("file")MultipartFile multipartFile){
        episodeService.uploadVideo(id,multipartFile);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editEpisode(@RequestBody UpsertEpisodeRequest upsertEpisodeRequest, @PathVariable Integer id){
        Episode episode = episodeService.editEpisode(upsertEpisodeRequest,id);
        return  ResponseEntity.ok(episode);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> editEpisode( @PathVariable Integer id){
         episodeService.deleteEpisode(id);
        return  ResponseEntity.noContent().build();
    }

}
