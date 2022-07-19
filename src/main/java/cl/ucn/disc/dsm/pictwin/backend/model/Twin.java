package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

/**
 * The twin
 * @author Johan Rojas Godoy
 */
@Entity
@Table(name = "twins")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Twin {

    /**
     * The id of a twin
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The dislike of a twin
     */
    @Getter
    @Setter
    @Builder.Default
    private boolean dislike = Boolean.FALSE;

    /**
     * The owner pic
     */
    @Getter
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Pic myPic;

    /**
     * yours pic
     */
    @Getter
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Pic yoursPic;

    /**
     * The Owner
     */
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @Getter
    @JsonBackReference
    private User owner;

}
