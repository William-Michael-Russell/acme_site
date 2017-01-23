package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThRadioInput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThRadioInput entity.
 */
public interface ThRadioInputRepository extends JpaRepository<ThRadioInput,Long> {

    @Query("select thRadioInput from ThRadioInput thRadioInput where thRadioInput.user.login = ?#{principal}")
    List<ThRadioInput> findByUserIsCurrentUser();

    @Query("select thRadioInput from ThRadioInput thRadioInput where thRadioInput.user.login = ?#{principal}")
    Page<ThRadioInput> findByUserIsCurrentUser(Pageable pageable);

}
