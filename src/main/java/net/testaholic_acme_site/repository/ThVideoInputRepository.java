package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThVideoInput;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThVideoInput entity.
 */
public interface ThVideoInputRepository extends JpaRepository<ThVideoInput,Long> {

    @Query("select thVideoInput from ThVideoInput thVideoInput where thVideoInput.user.login = ?#{principal}")
    List<ThVideoInput> findByUserIsCurrentUser();

}
