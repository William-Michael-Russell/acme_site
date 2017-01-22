package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThPasswordInput.
 */
@Entity
@Table(name = "th_password_input")
public class ThPasswordInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "password_input_field", length = 20, nullable = false)
    private String passwordInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordInputField() {
        return passwordInputField;
    }

    public void setPasswordInputField(String passwordInputField) {
        this.passwordInputField = passwordInputField;
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
        ThPasswordInput thPasswordInput = (ThPasswordInput) o;
        if(thPasswordInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thPasswordInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThPasswordInput{" +
            "id=" + id +
            ", passwordInputField='" + passwordInputField + "'" +
            '}';
    }
}
