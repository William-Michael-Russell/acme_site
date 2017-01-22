package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThRadioInput.
 */
@Entity
@Table(name = "th_radio_input")
public class ThRadioInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "radio_input_field", nullable = false)
    private String radioInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRadioInputField() {
        return radioInputField;
    }

    public void setRadioInputField(String radioInputField) {
        this.radioInputField = radioInputField;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThRadioInput thRadioInput = (ThRadioInput) o;
        if(thRadioInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thRadioInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThRadioInput{" +
            "id=" + id +
            ", radioInputField='" + radioInputField + "'" +
            '}';
    }
}
