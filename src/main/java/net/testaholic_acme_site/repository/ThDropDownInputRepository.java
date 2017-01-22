package net.testaholic_acme_site.repository;

import net.testaholic_acme_site.domain.ThDropDownInput;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThDropDownInput entity.
 */
public interface ThDropDownInputRepository extends JpaRepository<ThDropDownInput,Long> {

    @Query("select thDropDownInput from ThDropDownInput thDropDownInput where thDropDownInput.user.login = ?#{principal}")
    List<ThDropDownInput> findByUserIsCurrentUser();

}
