package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThTextInput.
 */
@Entity
@Table(name = "th_text_input")
public class ThTextInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, max = 20)
    @Column(name = "texted_input_field", length = 20)
    private String textedInputField;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextedInputField() {
        return textedInputField;
    }

    public void setTextedInputField(String textedInputField) {
        this.textedInputField = textedInputField;
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
        ThTextInput thTextInput = (ThTextInput) o;
        if(thTextInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thTextInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThTextInput{" +
            "id=" + id +
            ", textedInputField='" + textedInputField + "'" +
            '}';
    }
}
