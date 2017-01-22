package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThPhoneInput;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThPhoneInput entity.
 */
public interface ThPhoneInputRepository extends JpaRepository<ThPhoneInput,Long> {

    @Query("select thPhoneInput from ThPhoneInput thPhoneInput where thPhoneInput.user.login = ?#{principal}")
    List<ThPhoneInput> findByUserIsCurrentUser();

}
