package cl.ucn.disc.dsm.pictwin.backend.services;

import cl.ucn.disc.dsm.pictwin.backend.dao.PicRepository;
import cl.ucn.disc.dsm.pictwin.backend.dao.TwinRepository;
import cl.ucn.disc.dsm.pictwin.backend.dao.UserRepository;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * the {@link PicTwin} implementation.
 * @author johan rojas godoy.
 */
@Slf4j
@Service
public class PicTwinImpl  implements PicTwin {

    /**
     * The hasher
     */
    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    /**
     * The random
     */
    private final static Random RANDOM=new Random();

    /**
     * The pic repository
     */
    private final PicRepository picRepository;

    /**
     * The Twin repository
     */
    private final TwinRepository twinRepository;

    /**
     * The User repository
     */
    private final UserRepository userRepository;

    /**
     * Build the PicTwinImplementation
     * @param picRepository
     * @param twinRepository
     * @param userRepository
     */
    @Autowired
    public PicTwinImpl(PicRepository picRepository, TwinRepository twinRepository, UserRepository userRepository) {
        this.picRepository = picRepository;
        this.twinRepository = twinRepository;
        this.userRepository = userRepository;
    }


    /**
     * Create a user With a specific password
     * @param user
     * @param password
     * @return
     */
    @Override
    @Transactional
    public User create(@NonNull User user,@NonNull String password) {

        //if th user already exists
        if (this.userRepository.findOneByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("The User with email <"+user.getEmail()+" its already in the system");
        }
        //Using the password with the hash
        String passwdHash=PASSWORD_ENCODER.encode(password);
        //Replace the password with the hash
        user.setPassword(passwdHash);
        //Save into the repository
        return this.userRepository.save(user);
    }

    /**
     * Return the user with the email and password
     * @param email
     * @param password
     * @return
     */
    @Override
    public User authenticate(@NonNull String email,@NonNull String password) {
        //Find the email
        Optional<User> userOptional=this.userRepository.findOneByEmail(email);
        User user =userOptional.orElseThrow(()->new RuntimeException("Error"));
        //check the password
        if (PASSWORD_ENCODER.matches(password,user.getPassword())){
            return user;
        }
        log.debug("User: {}",userOptional);
        return userOptional.orElseThrow(() ->new RuntimeException("Wrong Credentials or user not found"));
    }

    /**
     * Create a Twin using Pic from the user
     * @param myPic
     * @param idUser
     * @return
     */
    @Override
    @Transactional
    public Twin createTwin(@NonNull Pic myPic,@NonNull Long idUser) {
        User owner = this.userRepository.findById(idUser).orElseThrow();

        log.debug("Pics: {} in the user {}",owner.getTwins().size(),owner.getEmail());

        //Set the User
        myPic.setOwner(owner);

        //Store the pic
        this.picRepository.save(myPic);

        //Get all the pics
        List<Pic> pics=this.picRepository.findAll();
        log.debug(" Number of pics in the database: {}",pics.size());

        //Remove my owns Pics
        List<Pic> picsFiltered=pics.stream().filter(p->!p.getOwner().getId().equals(idUser)).toList();
        if(picsFiltered.size()==0){
            log.warn("Re-using Pics from database.");
            picsFiltered=pics;
        }
        //Select a random pic
        Pic your =picsFiltered.get(RANDOM.nextInt(picsFiltered.size()));

        //Increment the views
        your.IncrementViews();

        //Save the increment
        this.picRepository.save(your);

        //Store the Twin
        Twin twin = Twin.builder()
                .myPic(myPic)
                .yoursPic(your)
                .owner(owner)
                .build();
        //Save the Twin
        this.twinRepository.save(twin);

        //add the twin to the user
        owner.add(twin);
        this.userRepository.save(owner);
        return twin;
    }

    /**
     * Dislikes a pic in a twin
     * @param idTwin
     * @param idUser
     */
    @Override
    @Transactional
    public void dislikes(@NonNull Long idTwin,@NonNull Long idUser) {
        //Retrieve the twin
        Optional<Twin> optionalTwin=this.twinRepository.findById(idTwin);

        //Check if exist
        Twin twin= optionalTwin.orElseThrow(()->new RuntimeException("Cant find Twin with id: "+idTwin));

        //Check the owner of a pic
        if(!idUser.equals((twin.getMyPic().getOwner().getId()))){
            throw new RuntimeException("Twin id>"+idTwin+"> not owned by User id <"+idUser+">!");
        }
        //set the dislikes an save
        twin.setDislike(true);
        this.twinRepository.save(twin);

        //Increment yhe dislikes of twin and save
        Pic yours=twin.getYoursPic();
        yours.getDislikes();
        this.picRepository.save(yours);

        //Increment the strikes in user and save
        User user= yours.getOwner();
        user.incrementStrikes();
        this.userRepository.save(user);
    }

    /**
     * @return the number os users in the system
     */
    @Override
    public Long getUserSize() {
        return this.userRepository.count();
    }


}
