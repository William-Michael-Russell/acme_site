package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThTextInput;
import net.testaholic_acme_site.repository.ThTextInputRepository;
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
 * REST controller for managing ThTextInput.
 */
@RestController
@RequestMapping("/api")
public class ThTextInputResource {

    private final Logger log = LoggerFactory.getLogger(ThTextInputResource.class);
        
    @Inject
    private ThTextInputRepository thTextInputRepository;
    
    /**
     * POST  /th-text-inputs : Create a new thTextInput.
     *
     * @param thTextInput the thTextInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thTextInput, or with status 400 (Bad Request) if the thTextInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-text-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThTextInput> createThTextInput(@Valid @RequestBody ThTextInput thTextInput) throws URISyntaxException {
        log.debug("REST request to save ThTextInput : {}", thTextInput);
        if (thTextInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thTextInput", "idexists", "A new thTextInput cannot already have an ID")).body(null);
        }
        ThTextInput result = thTextInputRepository.save(thTextInput);
        return ResponseEntity.created(new URI("/api/th-text-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thTextInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-text-inputs : Updates an existing thTextInput.
     *
     * @param thTextInput the thTextInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thTextInput,
     * or with status 400 (Bad Request) if the thTextInput is not valid,
     * or with status 500 (Internal Server Error) if the thTextInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-text-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThTextInput> updateThTextInput(@Valid @RequestBody ThTextInput thTextInput) throws URISyntaxException {
        log.debug("REST request to update ThTextInput : {}", thTextInput);
        if (thTextInput.getId() == null) {
            return createThTextInput(thTextInput);
        }
        ThTextInput result = thTextInputRepository.save(thTextInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thTextInput", thTextInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-text-inputs : get all the thTextInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thTextInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-text-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThTextInput>> getAllThTextInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThTextInputs");
        Page<ThTextInput> page = thTextInputRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-text-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-text-inputs/:id : get the "id" thTextInput.
     *
     * @param id the id of the thTextInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thTextInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-text-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThTextInput> getThTextInput(@PathVariable Long id) {
        log.debug("REST request to get ThTextInput : {}", id);
        ThTextInput thTextInput = thTextInputRepository.findOne(id);
        return Optional.ofNullable(thTextInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-text-inputs/:id : delete the "id" thTextInput.
     *
     * @param id the id of the thTextInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-text-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThTextInput(@PathVariable Long id) {
        log.debug("REST request to delete ThTextInput : {}", id);
        thTextInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thTextInput", id.toString())).build();
    }

}
