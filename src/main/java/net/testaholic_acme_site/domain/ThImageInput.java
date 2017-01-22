package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThImageInput.
 */
@Entity
@Table(name = "th_image_input")
public class ThImageInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 5000000)
    @Lob
    @Column(name = "image_input_field", nullable = false)
    private byte[] imageInputField;

    @Column(name = "image_input_field_content_type", nullable = false)    
    private String imageInputFieldContentType;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageInputField() {
        return imageInputField;
    }

    public void setImageInputField(byte[] imageInputField) {
        this.imageInputField = imageInputField;
    }

    public String getImageInputFieldContentType() {
        return imageInputFieldContentType;
    }

    public void setImageInputFieldContentType(String imageInputFieldContentType) {
        this.imageInputFieldContentType = imageInputFieldContentType;
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
        ThImageInput thImageInput = (ThImageInput) o;
        if(thImageInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thImageInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThImageInput{" +
            "id=" + id +
            ", imageInputField='" + imageInputField + "'" +
            ", imageInputFieldContentType='" + imageInputFieldContentType + "'" +
            '}';
    }
}
