package cl.ucn.disc.dsm.pictwin.backend;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
import cl.ucn.disc.dsm.pictwin.backend.web.Utils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The PicTwin Controller
 */
@RestController
@Slf4j
public class PicTwinRestController {

    /**
     * The PicTwin service
     */
    private final PicTwin picTwin;

    /**
     * the constructor
     * @param picTwin
     */
    @Autowired
    public PicTwinRestController(PicTwin picTwin) {
        this.picTwin = picTwin;
    }

    /**
     * Create a user
     * @param user
     * @param password
     * @return
     */
    @RequestMapping(value = {"/users","/users/"},method = RequestMethod.POST)
    public User create(@Valid @RequestBody User user,@RequestParam String password){
        //Debug
        Utils.printObject("User",user);
        //call the controller
        return this.picTwin.create(user,password);
    }

    /**
     * Authenticate a User
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = {"/users","/users/"},method = RequestMethod.GET)
    public User authenticate(@RequestParam String email,@RequestParam String password){
        return this.picTwin.authenticate(email,password);
    }

    /**
     * Create a twin
     * @param pic
     * @param idUser
     * @return
     */
    @RequestMapping(value = {"/twins","/twins/"},method = RequestMethod.POST)
    public Twin createTwin(@Valid @RequestBody Pic pic, @RequestParam Long idUser){
        return this.picTwin.createTwin(pic,idUser);
    }

    /**
     * dislike a twin
     * @param idTwin
     * @param idUser
     * @return
     */
    @RequestMapping(value = {"/twins","/twins/"},method = RequestMethod.PATCH)
    public void dislike(@RequestParam Long idTwin, @RequestParam Long idUser){
        this.picTwin.dislikes(idTwin,idUser);
    }

    /**
     * Show a message i {@link MethodArgumentNotValidException}.
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fielName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fielName,errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleValidationExceptions(IllegalArgumentException ex){
        return ex.getMessage();
    }






}
