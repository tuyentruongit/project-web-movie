package web.movie.web1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.movie.web1.entity.Episode;
import web.movie.web1.entity.Movie;
import web.movie.web1.entity.Video;
import web.movie.web1.exception.ResourceNotFound;
import web.movie.web1.model.request.UpsertEpisodeRequest;
import web.movie.web1.repository.EpisodeRepository;
import web.movie.web1.repository.MovieRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final VideoService videoService;
    private final MovieRepository movieRepository;


    public List<Episode> getEpisodeOfMovie(Integer id){
        return episodeRepository.findByMovie_IdOrderByDisplayOderAsc(id);
    }

    public List<Episode> getEpisodeOfMovie(Integer id,Boolean status){
        return episodeRepository.findByMovie_IdAndStatusOrderByDisplayOderAsc(id,status);
    }

    public void uploadVideo(Integer id, MultipartFile multipartFile) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("không thể tìm thấy tập phim trên"));
        Video video = videoService.upLoadVideo(multipartFile);

        episode.setDuration(video.getDuration());
        episode.setVideoUrl(video.getUrl());

        episodeRepository.save(episode);
    }

    public Episode getEpisode(Integer id, String tap, boolean status) {
        if (Objects.equals(tap, "Full")){
            return getEpisodeOfMovie(id,status).stream().filter(epi -> epi.getDisplayOder()==1).findFirst().orElse(null);
        }
        Integer passeIntTap = Integer.parseInt(tap);
        return getEpisodeOfMovie(id,status).stream().filter(episode -> Objects.equals(episode.getDisplayOder(), passeIntTap)).findFirst().orElse(null);
    }

    public Episode createEpisode(UpsertEpisodeRequest upsertEpisodeRequest) {
        Movie movie = movieRepository.findById(upsertEpisodeRequest.getIdMovie())
                .orElseThrow(()-> new ResourceNotFound("không thể tìm thấy phim trên"));
        Episode episode =Episode.builder()
                .title(upsertEpisodeRequest.getTitle())
                .movie(movie)
                .status(upsertEpisodeRequest.getStatus())
                .displayOder(upsertEpisodeRequest.getDisplayOder())
                .duration(10000)
                .build();

        episodeRepository.save(episode);
        return episode;

    }

    public Episode editEpisode(UpsertEpisodeRequest upsertEpisodeRequest, Integer id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("không thể tìm thấy tập phim trên"));
        episode.setStatus(upsertEpisodeRequest.getStatus());
        episode.setTitle(upsertEpisodeRequest.getTitle());
        episodeRepository.save(episode);
        return episode;
    }


    public void deleteEpisode(Integer id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("không thể tìm thấy tập phim trên"));
        episodeRepository.delete(episode);
    }
}
