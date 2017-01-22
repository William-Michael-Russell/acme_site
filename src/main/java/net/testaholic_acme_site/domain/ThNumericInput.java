package net.testaholic_acme_site.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThNumericInput.
 */
@Entity
@Table(name = "th_numeric_input")
public class ThNumericInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numeric_input_field")
    private Integer numericInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumericInputField() {
        return numericInputField;
    }

    public void setNumericInputField(Integer numericInputField) {
        this.numericInputField = numericInputField;
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
        ThNumericInput thNumericInput = (ThNumericInput) o;
        if(thNumericInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thNumericInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThNumericInput{" +
            "id=" + id +
            ", numericInputField='" + numericInputField + "'" +
            '}';
    }
}
