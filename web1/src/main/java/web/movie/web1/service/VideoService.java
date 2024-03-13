package web.movie.web1.service;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.movie.web1.entity.Image;
import web.movie.web1.entity.User;
import web.movie.web1.entity.Video;
import web.movie.web1.repository.ImageRepository;
import web.movie.web1.repository.VideoRepository;
import web.movie.web1.rest.VideoApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class VideoService {
    public String uploadDir = "video_uploads";
    public final VideoRepository videoRepository;
    public final HttpSession session;


    public static final long CHUNK_SIZE = 100000L;

    public VideoService( VideoRepository videoRepository, HttpSession session) {
        this.videoRepository = videoRepository;
        this.session = session;
        createDir(uploadDir);
    }


    public ResourceRegion getVideoResourceRegion(String fileName, long start, long end) throws IOException {
        UrlResource videoResource = new UrlResource("file:" + uploadDir + File.separator + fileName);

        if (!videoResource.exists() || !videoResource.isReadable()) {
            throw new IOException("Video not found");
        }

        Resource video = videoResource;
        long contentLength = video.contentLength();
        end = Math.min(end, contentLength - 1);

        long rangeLength = Math.min(CHUNK_SIZE, end - start + 1);
        return new ResourceRegion(video, start, rangeLength);
    }

    public void createDir(String name)  {
        Path path = Paths.get(name);
        if (!Files.exists(path)){
            try{
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }


    }


    public Video upLoadVideo(MultipartFile file) {
        User user = (User) session.getAttribute("currentUser");

        String idVideo = UUID.randomUUID().toString();
        Path rootPath = Paths.get(uploadDir);
        Path filePath = rootPath.resolve(idVideo);
        try {
            Files.copy(file.getInputStream(),filePath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot upload file "+e);
        }

        Video video = Video.builder()
                .id(idVideo)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize()/1048576.0)
                .duration(1000)
                .url("/api/videos/" + idVideo)
                .user(user)
                .build();
        return videoRepository.save(video);
    }

    public void deleteImage(String id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Image not Found"));
        // xóa ảnh trong folder
        Path path = Paths.get(uploadDir).resolve(video.getId());
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  xóa ảnh trong db
        videoRepository.deleteById(video.getId());
    }
}
