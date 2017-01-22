package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThScrollingView;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThScrollingView entity.
 */
public interface ThScrollingViewRepository extends JpaRepository<ThScrollingView,Long> {

}
