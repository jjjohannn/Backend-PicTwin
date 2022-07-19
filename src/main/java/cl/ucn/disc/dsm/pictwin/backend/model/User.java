package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The user
 * @author Johan Rojas Godoy
 */
@Entity
@Table(name="users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {

    /**
     * The id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The email of a user
     */
    @Getter
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * The password of a user
     */
    @Getter
    @Setter
    private String password;

    /**
     * Strikes of a person
     */
    @Getter
    private int strikes;

    /**
     * Increase strikes of a user
     */
    public int incrementStrikes(){
        this.strikes++;
        return this.strikes;
    }

    /**
     * State of a user
     */
    @Getter
    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private State state = State.ACTIVE;

    /**
     * The twins
     */
    @OneToMany( mappedBy  = "owner" ,fetch = FetchType.EAGER)
    @Builder.Default
    @Getter
    @JsonManagedReference
    private List<Twin> twins = new ArrayList<>();

    /**
     * Insert a Twin in the list
     *
     * @param twin to add
     */
    public void add(final Twin twin){
        this.twins.add(twin);
    }


}
