package crud_cr.mvc_1.service;


import crud_cr.mvc_1.dto.SignupRequest;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public User signup(SignupRequest request)
    {
        if(userRepository.findByEmail(request.getEmail()).isPresent())
        {
            throw  new RuntimeException("Email already exists");
        }

        User user =  modelMapper.map(request,User.class);

        return userRepository.

                save(user);
    }


    public User login(String email,String password)
    {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Invalid Email or Passowrd"));

        if(!user.getPassword().equals(password))
        {
            throw new RuntimeException("Invalid email or passowrd");
        }

        return user;

    }

}
