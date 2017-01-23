package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThPhoneInput;
import net.testaholic_acme_site.repository.ThPhoneInputRepository;
import net.testaholic_acme_site.repository.UserRepository;
import net.testaholic_acme_site.security.SecurityUtils;
import net.testaholic_acme_site.web.rest.util.HeaderUtil;
import net.testaholic_acme_site.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ThPhoneInput.
 */
@RestController
@RequestMapping("/api")
public class ThPhoneInputResource {

    private final Logger log = LoggerFactory.getLogger(ThPhoneInputResource.class);

    @Inject
    private ThPhoneInputRepository thPhoneInputRepository;

    @Inject
    private UserRepository userRepository;
    /**
     * POST  /th-phone-inputs : Create a new thPhoneInput.
     *
     * @param thPhoneInput the thPhoneInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thPhoneInput, or with status 400 (Bad Request) if the thPhoneInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-phone-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThPhoneInput> createThPhoneInput(@Valid @RequestBody ThPhoneInput thPhoneInput) throws URISyntaxException {
        log.debug("REST request to save ThPhoneInput : {}", thPhoneInput);
        if (thPhoneInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thPhoneInput", "idexists", "A new thPhoneInput cannot already have an ID")).body(null);
        }
        thPhoneInput.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ThPhoneInput result = thPhoneInputRepository.save(thPhoneInput);
        return ResponseEntity.created(new URI("/api/th-phone-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thPhoneInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-phone-inputs : Updates an existing thPhoneInput.
     *
     * @param thPhoneInput the thPhoneInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thPhoneInput,
     * or with status 400 (Bad Request) if the thPhoneInput is not valid,
     * or with status 500 (Internal Server Error) if the thPhoneInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-phone-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThPhoneInput> updateThPhoneInput(@Valid @RequestBody ThPhoneInput thPhoneInput) throws URISyntaxException {
        log.debug("REST request to update ThPhoneInput : {}", thPhoneInput);
        if (thPhoneInput.getId() == null) {
            return createThPhoneInput(thPhoneInput);
        }
        ThPhoneInput result = thPhoneInputRepository.save(thPhoneInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thPhoneInput", thPhoneInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-phone-inputs : get all the thPhoneInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thPhoneInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-phone-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThPhoneInput>> getAllThPhoneInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThPhoneInputs");
        Page<ThPhoneInput> page = thPhoneInputRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-phone-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-phone-inputs/:id : get the "id" thPhoneInput.
     *
     * @param id the id of the thPhoneInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thPhoneInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-phone-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThPhoneInput> getThPhoneInput(@PathVariable Long id) {
        log.debug("REST request to get ThPhoneInput : {}", id);
        ThPhoneInput thPhoneInput = thPhoneInputRepository.findOne(id);
        return Optional.ofNullable(thPhoneInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-phone-inputs/:id : delete the "id" thPhoneInput.
     *
     * @param id the id of the thPhoneInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-phone-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThPhoneInput(@PathVariable Long id) {
        log.debug("REST request to delete ThPhoneInput : {}", id);
        thPhoneInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thPhoneInput", id.toString())).build();
    }

}
