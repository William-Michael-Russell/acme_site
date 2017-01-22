package net.testaholic_acme_site.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThVideoInput.
 */
@Entity
@Table(name = "th_video_input")
public class ThVideoInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 50, max = 10000000)
    @Lob
    @Column(name = "video_input_field", nullable = false)
    private byte[] videoInputField;

    @Column(name = "video_input_field_content_type", nullable = false)    
    private String videoInputFieldContentType;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getVideoInputField() {
        return videoInputField;
    }

    public void setVideoInputField(byte[] videoInputField) {
        this.videoInputField = videoInputField;
    }

    public String getVideoInputFieldContentType() {
        return videoInputFieldContentType;
    }

    public void setVideoInputFieldContentType(String videoInputFieldContentType) {
        this.videoInputFieldContentType = videoInputFieldContentType;
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
        ThVideoInput thVideoInput = (ThVideoInput) o;
        if(thVideoInput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thVideoInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThVideoInput{" +
            "id=" + id +
            ", videoInputField='" + videoInputField + "'" +
            ", videoInputFieldContentType='" + videoInputFieldContentType + "'" +
            '}';
    }
}
