package cl.ucn.disc.dsm.pictwin.backend.services;


import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;

/**
 * The interface pic twin
 * @author Johan Rojas Godoy
 */

public interface PicTwin {

    /**
     * Create a user with a specific passwored
     *
     * @param user
     * @param password
     * @return
     */
    User create(User user, String password);

    /**
     * Return the User with the email and password
     * @param email
     * @param password
     * @return
     */
    User authenticate(String email,String password);

    /**
     * Create a Twin using the Pic from The user
     * @param myPic
     * @param idUser
     * @return
     */
    Twin createTwin(Pic myPic, Long idUser);

    /**
     * Dislikes a pic in a Twin
     * @param idTwin
     * @param idUser
     */
    void dislikes(Long idTwin,Long idUser);

    /**
     *
     * @return the number os users in the system
     */
    Long getUserSize();
}
