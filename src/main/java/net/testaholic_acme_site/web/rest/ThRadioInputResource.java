package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThRadioInput;
import net.testaholic_acme_site.repository.ThRadioInputRepository;
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
 * REST controller for managing ThRadioInput.
 */
@RestController
@RequestMapping("/api")
public class ThRadioInputResource {

    private final Logger log = LoggerFactory.getLogger(ThRadioInputResource.class);

    @Inject
    private ThRadioInputRepository thRadioInputRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /th-radio-inputs : Create a new thRadioInput.
     *
     * @param thRadioInput the thRadioInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thRadioInput, or with status 400 (Bad Request) if the thRadioInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-radio-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThRadioInput> createThRadioInput(@Valid @RequestBody ThRadioInput thRadioInput) throws URISyntaxException {
        log.debug("REST request to save ThRadioInput : {}", thRadioInput);
        if (thRadioInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thRadioInput", "idexists", "A new thRadioInput cannot already have an ID")).body(null);
        }
        thRadioInput.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ThRadioInput result = thRadioInputRepository.save(thRadioInput);
        return ResponseEntity.created(new URI("/api/th-radio-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thRadioInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-radio-inputs : Updates an existing thRadioInput.
     *
     * @param thRadioInput the thRadioInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thRadioInput,
     * or with status 400 (Bad Request) if the thRadioInput is not valid,
     * or with status 500 (Internal Server Error) if the thRadioInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-radio-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThRadioInput> updateThRadioInput(@Valid @RequestBody ThRadioInput thRadioInput) throws URISyntaxException {
        log.debug("REST request to update ThRadioInput : {}", thRadioInput);
        if (thRadioInput.getId() == null) {
            return createThRadioInput(thRadioInput);
        }
        ThRadioInput result = thRadioInputRepository.save(thRadioInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thRadioInput", thRadioInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-radio-inputs : get all the thRadioInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thRadioInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-radio-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThRadioInput>> getAllThRadioInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThRadioInputs");
        Page<ThRadioInput> page = thRadioInputRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-radio-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-radio-inputs/:id : get the "id" thRadioInput.
     *
     * @param id the id of the thRadioInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thRadioInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-radio-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThRadioInput> getThRadioInput(@PathVariable Long id) {
        log.debug("REST request to get ThRadioInput : {}", id);
        ThRadioInput thRadioInput = thRadioInputRepository.findOne(id);
        return Optional.ofNullable(thRadioInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-radio-inputs/:id : delete the "id" thRadioInput.
     *
     * @param id the id of the thRadioInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-radio-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThRadioInput(@PathVariable Long id) {
        log.debug("REST request to delete ThRadioInput : {}", id);
        thRadioInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thRadioInput", id.toString())).build();
    }

}
