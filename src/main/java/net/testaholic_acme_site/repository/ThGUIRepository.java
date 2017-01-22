package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThGUI;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThGUI entity.
 */
public interface ThGUIRepository extends JpaRepository<ThGUI,Long> {

}
