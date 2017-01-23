package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThPasswordInput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThPasswordInput entity.
 */
public interface ThPasswordInputRepository extends JpaRepository<ThPasswordInput,Long> {

    @Query("select thPasswordInput from ThPasswordInput thPasswordInput where thPasswordInput.user.login = ?#{principal}")
    List<ThPasswordInput> findByUserIsCurrentUser();

    @Query("select thPasswordInput from ThPasswordInput thPasswordInput where thPasswordInput.user.login = ?#{principal}")
    Page<ThPasswordInput> findByUserIsCurrentUser(Pageable pageable);

}
