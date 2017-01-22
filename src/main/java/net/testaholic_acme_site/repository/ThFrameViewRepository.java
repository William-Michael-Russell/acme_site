package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThFrameView;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThFrameView entity.
 */
public interface ThFrameViewRepository extends JpaRepository<ThFrameView,Long> {

}
