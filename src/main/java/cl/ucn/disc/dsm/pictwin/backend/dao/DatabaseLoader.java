package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
import cl.ucn.disc.dsm.pictwin.backend.web.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The database loader
 */
@Slf4j
@Component
public class DatabaseLoader implements CommandLineRunner {

    /**
     * the PicTwin implementation.
     */
    private final PicTwin picTwin;

    /**
     * The constructor.
     * @param picTwin
     */
    public DatabaseLoader(PicTwin picTwin) {
        this.picTwin = picTwin;
    }


    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Database DataLoader: Starting seeder .. ");

        //check if exist data the database
        if(this.picTwin.getUserSize() != 0){
            log.info("Database already seeded skipping!");
            return;
        }
        log.warn("No data found in database, seeding the database .. ");

        //The main user
        User user = User.builder()
                    .email("Johan@ucn.cl")
                    .password("Johan123")
                    .strikes(0)
                    .build();
        Utils.printObject("User to create: ",user);

        //Storage the user
        this.picTwin.create(user,"Johan123");
        Utils.printObject("User created:" , user);

        //Create the first Twin
        Twin twin1=this.picTwin.createTwin(Pic.builder().name("The first pic:UCN")
                        .latitude(-23.6803026)
                        .longitude(-70.4121427)
                        .error(3.5)
                        .owner(user)
                        .build(),user.getId());
        Utils.printObject("Twin1 Created:",twin1);

        //Create the second Twin
        Twin twin2=this.picTwin.createTwin(Pic.builder().name("The second pic: parque de los eventos")
                .latitude(-23.6281221)
                .longitude(-70.3952909)
                .error(5.7)
                .owner(user)
                .build(),user.getId());
        Utils.printObject("Twin2 Created:",twin2);

        //Create the third Twin
        Twin twin3=this.picTwin.createTwin(Pic.builder().name("The third pic:Quebrada Carrizo")
                .latitude(-23.6977891)
                .longitude(-70.4105903)
                .error(5.7)
                .owner(user)
                .build(),user.getId());
        Utils.printObject("Twin3 Created:",twin3);

        //Create the fourth Twin
        Twin twin4=this.picTwin.createTwin(Pic.builder().name("The fourth pic:Balneareo juan lopez")
                .latitude(-23.6977891)
                .longitude(-70.5218646)
                .error(0.3)
                .owner(user)
                .build(),user.getId());
        Utils.printObject("Twin4 Created:",twin4);

        log.info("Database Data Loader: Done.");

        }
}
