package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThNumericInput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThNumericInput entity.
 */
public interface ThNumericInputRepository extends JpaRepository<ThNumericInput,Long> {

    @Query("select thNumericInput from ThNumericInput thNumericInput where thNumericInput.user.login = ?#{principal}")
    List<ThNumericInput> findByUserIsCurrentUser();

    @Query("select thNumericInput from ThNumericInput thNumericInput where thNumericInput.user.login = ?#{principal}")
    Page<ThNumericInput> findByUserIsCurrentUser(Pageable pageable);

}
