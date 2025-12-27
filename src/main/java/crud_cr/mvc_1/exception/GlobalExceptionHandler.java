package crud_cr.mvc_1.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(BadRequestException ex, Model model)
    {
        model.addAttribute("error",ex.getMessage());

        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex,Model model)
    {
        model.addAttribute("error","Something went wrong");

        return "error";
    }
}
