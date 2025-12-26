package crud_cr.mvc_1.controller;


import crud_cr.mvc_1.dto.LoginRequest;
import crud_cr.mvc_1.dto.SignupRequest;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup")
    public String signupForm(Model model)
    {

        model.addAttribute("signupRequest", new SignupRequest());
        return "signup";

    }

    @PostMapping("/signup")
    public String processSignup(

            @Valid @ModelAttribute("signupRequest") SignupRequest request ,
            BindingResult result
    )
    {
        if(result.hasErrors())
        {
            return "signup";
        }

        authService.signup(request);
        return "redirect:/login";
    }



    // login

    @GetMapping("/login")
    public String loginForm(Model model)
    {
        model.addAttribute("loginRequest", new LoginRequest());

        return "login";

    }

    @PostMapping("/login")
    public String processLogin
            (@Valid @ModelAttribute("loginRequest") LoginRequest request,
             BindingResult result,
             HttpSession session,
             Model model)
    {
        if(result.hasErrors())
        {
            return "login";
        }

        try{

            User user = authService.login(request.getEmail(),request.getPassword());

            session.setAttribute("loggedInUser",user);

            return "redirect:/home";

        }catch (RuntimeException e)
        {
            model.addAttribute("error",e.getMessage());

            return "login";
        }
    }


    //logout


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


}
