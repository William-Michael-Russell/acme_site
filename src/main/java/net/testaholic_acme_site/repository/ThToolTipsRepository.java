package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThToolTips;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThToolTips entity.
 */
public interface ThToolTipsRepository extends JpaRepository<ThToolTips,Long> {

}
