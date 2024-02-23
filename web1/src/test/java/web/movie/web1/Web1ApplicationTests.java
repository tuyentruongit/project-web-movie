package web.movie.web1;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import web.movie.web1.entity.*;
import web.movie.web1.model.Role;
import web.movie.web1.repository.*;
import web.movie.web1.model.MovieType;

import java.util.*;

@SpringBootTest
class Web1ApplicationTests {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ReviewRepository reviewRepository;



    @Test
    void contextLoads() {
        List<Actor> actorList = actorRepository.findAll();
        List<Director> directorList = directorRepository.findAll();
        List<Genre> genreList = genreRepository.findAll();
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
//             random 1-3 thể loại
            List<Genre > rdgenreList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3)+1; j++) {
             Genre genre = genreList.get(random.nextInt(genreList.size()));
              if (!rdgenreList.contains(genre)){
                  rdgenreList.add(genre);
              }
            }
            // random 2-3 đạo diễn
            List<Director > rdDirectorlist = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3)+1; j++) {
                Director director = directorList.get(random.nextInt(directorList.size()));
                if (!rdDirectorlist.contains(director)){
                    rdDirectorlist.add(director);
                }
            }
            // random 5-7 diễn viên
            List<Actor > rdActorList = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3)+7; j++) {
                Actor actor = actorList.get(random.nextInt(actorList.size()));
                if (!rdActorList.contains(actor)){
                    rdActorList.add(actor);
                }
            }
            String title = faker.book().title();
            Boolean status = faker.bool().bool();
            Date createAt = faker.date().birthday();
            Date publishAt = null;
            if (status){
                publishAt = createAt;
            }

            Movie movie = Movie.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .poster(faker.company().logo())
                    .relishYear(faker.number().numberBetween(2020,2024))
                    .view(faker.number().numberBetween(1000,10000))
                    .rating(faker.number().randomDouble(1,6,10))
                    .movieType(MovieType.values()[random.nextInt(MovieType.values().length)])
                    .status(status)
                    .createAt(createAt)
                    .updateAt(createAt)
                    .publishedAt(publishAt)
                    .actorList(rdActorList)
                    .directorList(rdDirectorlist)
                    .genreList(genreList)
                    .build();
            movieRepository.save(movie);
        }



    }
    @Test
    void createDataUser(){
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Date createAt = faker.date().birthday();
            User user = User.builder()
                    .name(faker.name().name())
                    .email(faker.internet().emailAddress())
                    .password(faker.internet().password())
                    .avatar(faker.company().logo())
                    .role(Role.values()[random.nextInt(Role.values().length)])
                    .createAt(createAt)
                    .updateAt(createAt)
                    .build();
            userRepository.save(user);
        }
    }
    @Test
    void createDataBlog(){
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        List<User> listAdmin = userRepository.findByRole(Role.ROLE_ADMIN);
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(listAdmin.size());
            User user = listAdmin.get(index);
            String title = faker.book().title();
            Boolean status = faker.bool().bool();
            Date createAt = faker.date().birthday();
            Date publishAt = null;
            if (status){
                publishAt = createAt;
            }
            Blog blog = Blog.builder()
                    .title(title)
                    .slug(slugify.slugify(title))
                    .description(faker.lorem().paragraph())
                    .content(faker.lorem().paragraph(30))
                    .thumBail(faker.company().logo())
                    .status(status)
                    .createAt(createAt)
                    .updateAt(createAt)
                    .publishedAt(publishAt)
                    .user(user)
                    .build();
            blogRepository.save(blog);
        }
    }

    @Test
    void createActor(){
        Faker faker = new Faker();
        for (int i = 0; i < 50; i++) {
            Actor actor = Actor.builder()
                    .name(faker.name().name())
                    .avatar(faker.company().logo())
                    .description(faker.lorem().paragraph())
                    .birthday(faker.date().birthday())
                    .createdAt(faker.date().birthday())
                    .createdAt(faker.date().birthday())
                    .build();
            actorRepository.save(actor);
        }
    }
    @Test
    void createDirector(){

        Faker faker = new Faker();

        for (int i = 0; i < 20; i++) {
            Director director = Director.builder()
                    .name(faker.name().name())
                    .avatar(faker.company().logo())
                    .description(faker.lorem().paragraph())
                    .birthday(faker.date().birthday())
                    .createdAt(faker.date().birthday())
                    .createdAt(faker.date().birthday())
                    .build();

            directorRepository.save(director);
        }

    }
    @Test
    void createGenre(){
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();

        for (int i = 0; i < 10; i++) {

            String name = faker.name().name();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .build();
            genreRepository.save(genre);
        }

    }
    @Test
    void createReview(){
        Faker faker = new Faker();
        List<User> userList = userRepository.findAll();
        List<Movie> movieList = movieRepository.findAll();
        Random random = new Random();

        for (int i = 0; i <300; i++) {
            User user = userList.get(random.nextInt(userList.size()));
            int indexMovie = random.nextInt(movieList.size());
            Movie movie = movieList.get(indexMovie);
            Date createAt = faker.date().birthday();
            Review review = Review.builder()
                    .comment(faker.lorem().paragraph())
                    .rating(faker.number().randomDigit())
                    .createdAt(createAt)
                    .updatedAt(createAt)
                    .user(user)
                    .movie(movie)
                    .build();
            reviewRepository.save(review);

        }
    }




}
