package net.testaholic_acme_site.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ThGUI.
 */
@Entity
@Table(name = "th_gui")
public class ThGUI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThGUI thGUI = (ThGUI) o;
        if(thGUI.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thGUI.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ThGUI{" +
            "id=" + id +
            '}';
    }
}
