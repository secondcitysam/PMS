package crud_cr.mvc_1.controller;


import crud_cr.mvc_1.dto.SignupRequest;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request)
    {

        User user = authService.signup(request);

        return ResponseEntity.ok("Signup Successful");
    }

}


