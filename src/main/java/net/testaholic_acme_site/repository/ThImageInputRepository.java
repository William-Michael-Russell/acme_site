package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThImageInput;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThImageInput entity.
 */
public interface ThImageInputRepository extends JpaRepository<ThImageInput,Long> {

    @Query("select thImageInput from ThImageInput thImageInput where thImageInput.user.login = ?#{principal}")
    List<ThImageInput> findByUserIsCurrentUser();

}
