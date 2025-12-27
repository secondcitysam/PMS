package crud_cr.mvc_1.service;


import crud_cr.mvc_1.dto.SignupRequest;
import crud_cr.mvc_1.exception.BadRequestException;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;
    public User signup(SignupRequest request)
    {
        if(userRepository.findByEmail(request.getEmail()).isPresent())
        {
            throw  new BadRequestException("Email already exists");
        }

        User user =  modelMapper.map(request,User.class);


        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.

                save(user);
    }


    public User login(String email,String password)
    {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Invalid Email or Passowrd"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        return user;

    }

}
