package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThAlertPrompts;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThAlertPrompts entity.
 */
public interface ThAlertPromptsRepository extends JpaRepository<ThAlertPrompts,Long> {

}
