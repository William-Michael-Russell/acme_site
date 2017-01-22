package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThPhoneInput.
 */
@Entity
@Table(name = "th_phone_input")
public class ThPhoneInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "^[(]{0,1}[0-9]{3}[)]{0,1}[-\\s\\.]{0,1}[0-9]{3}[-\\s\\.]{0,1}[0-9]{4}$")
    @Column(name = "phone_input_field")
    private String phoneInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneInputField() {
        return phoneInputField;
    }

    public void setPhoneInputField(String phoneInputField) {
        this.phoneInputField = phoneInputField;
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
        ThPhoneInput thPhoneInput = (ThPhoneInput) o;
        if(thPhoneInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thPhoneInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThPhoneInput{" +
            "id=" + id +
            ", phoneInputField='" + phoneInputField + "'" +
            '}';
    }
}
