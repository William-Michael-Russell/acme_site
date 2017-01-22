package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThEmailInput;
import net.testaholic_acme_site.repository.ThEmailInputRepository;
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
 * REST controller for managing ThEmailInput.
 */
@RestController
@RequestMapping("/api")
public class ThEmailInputResource {

    private final Logger log = LoggerFactory.getLogger(ThEmailInputResource.class);
        
    @Inject
    private ThEmailInputRepository thEmailInputRepository;
    
    /**
     * POST  /th-email-inputs : Create a new thEmailInput.
     *
     * @param thEmailInput the thEmailInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thEmailInput, or with status 400 (Bad Request) if the thEmailInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-email-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThEmailInput> createThEmailInput(@Valid @RequestBody ThEmailInput thEmailInput) throws URISyntaxException {
        log.debug("REST request to save ThEmailInput : {}", thEmailInput);
        if (thEmailInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thEmailInput", "idexists", "A new thEmailInput cannot already have an ID")).body(null);
        }
        ThEmailInput result = thEmailInputRepository.save(thEmailInput);
        return ResponseEntity.created(new URI("/api/th-email-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thEmailInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-email-inputs : Updates an existing thEmailInput.
     *
     * @param thEmailInput the thEmailInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thEmailInput,
     * or with status 400 (Bad Request) if the thEmailInput is not valid,
     * or with status 500 (Internal Server Error) if the thEmailInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-email-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThEmailInput> updateThEmailInput(@Valid @RequestBody ThEmailInput thEmailInput) throws URISyntaxException {
        log.debug("REST request to update ThEmailInput : {}", thEmailInput);
        if (thEmailInput.getId() == null) {
            return createThEmailInput(thEmailInput);
        }
        ThEmailInput result = thEmailInputRepository.save(thEmailInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thEmailInput", thEmailInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-email-inputs : get all the thEmailInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thEmailInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-email-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThEmailInput>> getAllThEmailInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThEmailInputs");
        Page<ThEmailInput> page = thEmailInputRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-email-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-email-inputs/:id : get the "id" thEmailInput.
     *
     * @param id the id of the thEmailInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thEmailInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-email-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThEmailInput> getThEmailInput(@PathVariable Long id) {
        log.debug("REST request to get ThEmailInput : {}", id);
        ThEmailInput thEmailInput = thEmailInputRepository.findOne(id);
        return Optional.ofNullable(thEmailInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-email-inputs/:id : delete the "id" thEmailInput.
     *
     * @param id the id of the thEmailInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-email-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThEmailInput(@PathVariable Long id) {
        log.debug("REST request to delete ThEmailInput : {}", id);
        thEmailInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thEmailInput", id.toString())).build();
    }

}
