package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThEmailInput.
 */
@Entity
@Table(name = "th_email_input")
public class ThEmailInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$")
    @Column(name = "email_input_field")
    private String emailInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailInputField() {
        return emailInputField;
    }

    public void setEmailInputField(String emailInputField) {
        this.emailInputField = emailInputField;
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
        ThEmailInput thEmailInput = (ThEmailInput) o;
        if(thEmailInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thEmailInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThEmailInput{" +
            "id=" + id +
            ", emailInputField='" + emailInputField + "'" +
            '}';
    }
}
