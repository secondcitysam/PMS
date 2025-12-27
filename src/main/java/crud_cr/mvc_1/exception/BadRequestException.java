package crud_cr.mvc_1.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String msg)
    {
        super(msg);
    }

}
