
package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

/**
 * The picture Image.
 *
 * @author Johan Rojas Godoy
 */
@Entity
@Table(name = "pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Pic {

    /**
     * The id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The instance when the pic was saved.
     */
    @Getter
    @Builder.Default
    private ZonedDateTime timestamp = ZonedDateTime.now();

    /**
     * The dislikes of a pic
     */
    @Getter
    @Builder.Default
    private Integer dislikes=0;

    /**
     * Increase the dislikes of a pic
     */
    public int incrementDislikes(){
        dislikes++;
        return dislikes;
    }

    /**
     * The latitude of a pic
     */
    @Getter
    private Double latitude;

    /**
     * The longitude of a pic
     */
    @Getter
    private Double longitude;

    /**
     * The error of a pic
     */
    @Getter
    private Double error;

    /**
     * The views of a pic
     */
    @Getter
    @Builder.Default
    private Integer views=0;

    /**
     * Increase the views of a pic
     */
    public Integer IncrementViews(){
        views++;
        return views;
    }

    /**
     * The name of a pic
     */
    @Getter
    private String name;

    /**
     * The pic
     */
    @Getter
    private byte[] picture;

    /**
     * The owner
     */
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JsonBackReference
    private User owner;


}
