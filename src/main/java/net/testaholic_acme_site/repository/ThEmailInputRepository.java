package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThEmailInput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThEmailInput entity.
 */
public interface ThEmailInputRepository extends JpaRepository<ThEmailInput,Long> {

    @Query("select thEmailInput from ThEmailInput thEmailInput where thEmailInput.user.login = ?#{principal}")
    List<ThEmailInput> findByUserIsCurrentUser();

    @Query("select thEmailInput from ThEmailInput thEmailInput where thEmailInput.user.login = ?#{principal}")
    Page<ThEmailInput> findByUserIsCurrentUser(Pageable pageable);

}
