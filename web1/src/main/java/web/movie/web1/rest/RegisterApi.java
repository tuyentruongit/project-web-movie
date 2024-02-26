package web.movie.web1.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.movie.web1.model.request.RegisterRequest;
import web.movie.web1.service.RegisterService;

@RestController
@RequestMapping("/api/regist")
@RequiredArgsConstructor
public class RegisterApi {
    private final RegisterService registerService;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        registerService.register(request);
        return ResponseEntity.ok().build();
    }
}
