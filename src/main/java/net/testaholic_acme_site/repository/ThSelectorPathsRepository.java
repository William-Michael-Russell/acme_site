package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThSelectorPaths;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThSelectorPaths entity.
 */
public interface ThSelectorPathsRepository extends JpaRepository<ThSelectorPaths,Long> {

}
