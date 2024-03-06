package web.movie.web1.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.movie.web1.entity.Image;
import web.movie.web1.entity.User;
import web.movie.web1.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {
    public String uploadDir = "image_uploads";
    public final ImageRepository imageRepository;
    public final HttpSession session;



    public ImageService(ImageRepository imageRepository, HttpSession session) {
        this.imageRepository = imageRepository;
        this.session = session;
        createDir(uploadDir);
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

    public List<Image> getAllImageByCurrentUser() {
        User user = (User) session.getAttribute("currentUser");
        return imageRepository.findByUser_IdOrderByCreateAtDesc(user.getId());
    }

    public Image upLoadImage(MultipartFile file) {
        User user = (User) session.getAttribute("currentUser");

        String imageId = UUID.randomUUID().toString();
        Path rootPath = Paths.get(uploadDir);
        Path filePath = rootPath.resolve(imageId);
       try {
           Files.copy(file.getInputStream(),filePath);
       } catch (IOException e) {
           throw new RuntimeException("Cannot upload file "+e);
       }

       Image image = Image.builder()
               .id(imageId)
               .name(file.getOriginalFilename())
               .type(file.getContentType())
               .size(file.getSize()/1048576.0)
               .url("/"+uploadDir+"/"+imageId)
               .user(user)
               .build();
       imageRepository.save(image);
       return image;
    }

    public void deleteImage(String id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Image not Found"));
        // xóa ảnh trong folder
        Path path = Paths.get(uploadDir).resolve(image.getId());
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //  xóa ảnh trong db
        imageRepository.deleteById(image.getId());
    }
}
