package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThVideoInput;
import net.testaholic_acme_site.repository.ThVideoInputRepository;
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
 * REST controller for managing ThVideoInput.
 */
@RestController
@RequestMapping("/api")
public class ThVideoInputResource {

    private final Logger log = LoggerFactory.getLogger(ThVideoInputResource.class);

    @Inject
    private ThVideoInputRepository thVideoInputRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /th-video-inputs : Create a new thVideoInput.
     *
     * @param thVideoInput the thVideoInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thVideoInput, or with status 400 (Bad Request) if the thVideoInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-video-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThVideoInput> createThVideoInput(@Valid @RequestBody ThVideoInput thVideoInput) throws URISyntaxException {
        log.debug("REST request to save ThVideoInput : {}", thVideoInput);
        if (thVideoInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thVideoInput", "idexists", "A new thVideoInput cannot already have an ID")).body(null);
        }

        thVideoInput.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ThVideoInput result = thVideoInputRepository.save(thVideoInput);
        return ResponseEntity.created(new URI("/api/th-video-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thVideoInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-video-inputs : Updates an existing thVideoInput.
     *
     * @param thVideoInput the thVideoInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thVideoInput,
     * or with status 400 (Bad Request) if the thVideoInput is not valid,
     * or with status 500 (Internal Server Error) if the thVideoInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-video-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThVideoInput> updateThVideoInput(@Valid @RequestBody ThVideoInput thVideoInput) throws URISyntaxException {
        log.debug("REST request to update ThVideoInput : {}", thVideoInput);
        if (thVideoInput.getId() == null) {
            return createThVideoInput(thVideoInput);
        }
        ThVideoInput result = thVideoInputRepository.save(thVideoInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thVideoInput", thVideoInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-video-inputs : get all the thVideoInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thVideoInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-video-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThVideoInput>> getAllThVideoInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThVideoInputs");
        Page<ThVideoInput> page = thVideoInputRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-video-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-video-inputs/:id : get the "id" thVideoInput.
     *
     * @param id the id of the thVideoInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thVideoInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-video-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThVideoInput> getThVideoInput(@PathVariable Long id) {
        log.debug("REST request to get ThVideoInput : {}", id);
        ThVideoInput thVideoInput = thVideoInputRepository.findOne(id);
        return Optional.ofNullable(thVideoInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-video-inputs/:id : delete the "id" thVideoInput.
     *
     * @param id the id of the thVideoInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-video-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThVideoInput(@PathVariable Long id) {
        log.debug("REST request to delete ThVideoInput : {}", id);
        thVideoInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thVideoInput", id.toString())).build();
    }

}
