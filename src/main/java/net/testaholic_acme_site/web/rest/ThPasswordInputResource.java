package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThPasswordInput;
import net.testaholic_acme_site.repository.ThPasswordInputRepository;
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
 * REST controller for managing ThPasswordInput.
 */
@RestController
@RequestMapping("/api")
public class ThPasswordInputResource {

    private final Logger log = LoggerFactory.getLogger(ThPasswordInputResource.class);

    @Inject
    private ThPasswordInputRepository thPasswordInputRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /th-password-inputs : Create a new thPasswordInput.
     *
     * @param thPasswordInput the thPasswordInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thPasswordInput, or with status 400 (Bad Request) if the thPasswordInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-password-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThPasswordInput> createThPasswordInput(@Valid @RequestBody ThPasswordInput thPasswordInput) throws URISyntaxException {
        log.debug("REST request to save ThPasswordInput : {}", thPasswordInput);
        if (thPasswordInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thPasswordInput", "idexists", "A new thPasswordInput cannot already have an ID")).body(null);
        }
        thPasswordInput.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ThPasswordInput result = thPasswordInputRepository.save(thPasswordInput);
        return ResponseEntity.created(new URI("/api/th-password-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thPasswordInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-password-inputs : Updates an existing thPasswordInput.
     *
     * @param thPasswordInput the thPasswordInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thPasswordInput,
     * or with status 400 (Bad Request) if the thPasswordInput is not valid,
     * or with status 500 (Internal Server Error) if the thPasswordInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-password-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThPasswordInput> updateThPasswordInput(@Valid @RequestBody ThPasswordInput thPasswordInput) throws URISyntaxException {
        log.debug("REST request to update ThPasswordInput : {}", thPasswordInput);
        if (thPasswordInput.getId() == null) {
            return createThPasswordInput(thPasswordInput);
        }
        ThPasswordInput result = thPasswordInputRepository.save(thPasswordInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thPasswordInput", thPasswordInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-password-inputs : get all the thPasswordInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thPasswordInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-password-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThPasswordInput>> getAllThPasswordInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThPasswordInputs");
        Page<ThPasswordInput> page = thPasswordInputRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-password-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-password-inputs/:id : get the "id" thPasswordInput.
     *
     * @param id the id of the thPasswordInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thPasswordInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-password-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThPasswordInput> getThPasswordInput(@PathVariable Long id) {
        log.debug("REST request to get ThPasswordInput : {}", id);
        ThPasswordInput thPasswordInput = thPasswordInputRepository.findOne(id);
        return Optional.ofNullable(thPasswordInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-password-inputs/:id : delete the "id" thPasswordInput.
     *
     * @param id the id of the thPasswordInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-password-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThPasswordInput(@PathVariable Long id) {
        log.debug("REST request to delete ThPasswordInput : {}", id);
        thPasswordInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thPasswordInput", id.toString())).build();
    }

}
