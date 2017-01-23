package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThTextInput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThTextInput entity.
 */
public interface ThTextInputRepository extends JpaRepository<ThTextInput,Long> {

    @Query("select thTextInput from ThTextInput thTextInput where thTextInput.user.login = ?#{principal}")
    List<ThTextInput> findByUserIsCurrentUser();

    @Query("select thTextInput from ThTextInput thTextInput where thTextInput.user.login = ?#{principal}")
    Page<ThTextInput> findByUserIsCurrentUser(Pageable pageable);

}
