package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThDropDownInput.
 */
@Entity
@Table(name = "th_drop_down_input")
public class ThDropDownInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "drop_down_input_field", nullable = false)
    private String dropDownInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDropDownInputField() {
        return dropDownInputField;
    }

    public void setDropDownInputField(String dropDownInputField) {
        this.dropDownInputField = dropDownInputField;
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
        ThDropDownInput thDropDownInput = (ThDropDownInput) o;
        if(thDropDownInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thDropDownInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThDropDownInput{" +
            "id=" + id +
            ", dropDownInputField='" + dropDownInputField + "'" +
            '}';
    }
}
