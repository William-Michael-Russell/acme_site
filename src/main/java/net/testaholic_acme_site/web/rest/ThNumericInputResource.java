package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThNumericInput;
import net.testaholic_acme_site.repository.ThNumericInputRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ThNumericInput.
 */
@RestController
@RequestMapping("/api")
public class ThNumericInputResource {

    private final Logger log = LoggerFactory.getLogger(ThNumericInputResource.class);

    @Inject
    private ThNumericInputRepository thNumericInputRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /th-numeric-inputs : Create a new thNumericInput.
     *
     * @param thNumericInput the thNumericInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thNumericInput, or with status 400 (Bad Request) if the thNumericInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-numeric-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThNumericInput> createThNumericInput(@RequestBody ThNumericInput thNumericInput) throws URISyntaxException {
        log.debug("REST request to save ThNumericInput : {}", thNumericInput);
        if (thNumericInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thNumericInput", "idexists", "A new thNumericInput cannot already have an ID")).body(null);
        }

        thNumericInput.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ThNumericInput result = thNumericInputRepository.save(thNumericInput);
        return ResponseEntity.created(new URI("/api/th-numeric-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thNumericInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-numeric-inputs : Updates an existing thNumericInput.
     *
     * @param thNumericInput the thNumericInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thNumericInput,
     * or with status 400 (Bad Request) if the thNumericInput is not valid,
     * or with status 500 (Internal Server Error) if the thNumericInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-numeric-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThNumericInput> updateThNumericInput(@RequestBody ThNumericInput thNumericInput) throws URISyntaxException {
        log.debug("REST request to update ThNumericInput : {}", thNumericInput);
        if (thNumericInput.getId() == null) {
            return createThNumericInput(thNumericInput);
        }
        ThNumericInput result = thNumericInputRepository.save(thNumericInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thNumericInput", thNumericInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-numeric-inputs : get all the thNumericInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thNumericInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-numeric-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThNumericInput>> getAllThNumericInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThNumericInputs");
        Page<ThNumericInput> page = thNumericInputRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-numeric-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-numeric-inputs/:id : get the "id" thNumericInput.
     *
     * @param id the id of the thNumericInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thNumericInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-numeric-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThNumericInput> getThNumericInput(@PathVariable Long id) {
        log.debug("REST request to get ThNumericInput : {}", id);
        ThNumericInput thNumericInput = thNumericInputRepository.findOne(id);
        return Optional.ofNullable(thNumericInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-numeric-inputs/:id : delete the "id" thNumericInput.
     *
     * @param id the id of the thNumericInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-numeric-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThNumericInput(@PathVariable Long id) {
        log.debug("REST request to delete ThNumericInput : {}", id);
        thNumericInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thNumericInput", id.toString())).build();
    }

}
