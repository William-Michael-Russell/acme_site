package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThImageInput;
import net.testaholic_acme_site.repository.ThImageInputRepository;
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
 * REST controller for managing ThImageInput.
 */
@RestController
@RequestMapping("/api")
public class ThImageInputResource {

    private final Logger log = LoggerFactory.getLogger(ThImageInputResource.class);
        
    @Inject
    private ThImageInputRepository thImageInputRepository;
    
    /**
     * POST  /th-image-inputs : Create a new thImageInput.
     *
     * @param thImageInput the thImageInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thImageInput, or with status 400 (Bad Request) if the thImageInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-image-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThImageInput> createThImageInput(@Valid @RequestBody ThImageInput thImageInput) throws URISyntaxException {
        log.debug("REST request to save ThImageInput : {}", thImageInput);
        if (thImageInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thImageInput", "idexists", "A new thImageInput cannot already have an ID")).body(null);
        }
        ThImageInput result = thImageInputRepository.save(thImageInput);
        return ResponseEntity.created(new URI("/api/th-image-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thImageInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-image-inputs : Updates an existing thImageInput.
     *
     * @param thImageInput the thImageInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thImageInput,
     * or with status 400 (Bad Request) if the thImageInput is not valid,
     * or with status 500 (Internal Server Error) if the thImageInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-image-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThImageInput> updateThImageInput(@Valid @RequestBody ThImageInput thImageInput) throws URISyntaxException {
        log.debug("REST request to update ThImageInput : {}", thImageInput);
        if (thImageInput.getId() == null) {
            return createThImageInput(thImageInput);
        }
        ThImageInput result = thImageInputRepository.save(thImageInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thImageInput", thImageInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-image-inputs : get all the thImageInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thImageInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-image-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThImageInput>> getAllThImageInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThImageInputs");
        Page<ThImageInput> page = thImageInputRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-image-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-image-inputs/:id : get the "id" thImageInput.
     *
     * @param id the id of the thImageInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thImageInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-image-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThImageInput> getThImageInput(@PathVariable Long id) {
        log.debug("REST request to get ThImageInput : {}", id);
        ThImageInput thImageInput = thImageInputRepository.findOne(id);
        return Optional.ofNullable(thImageInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-image-inputs/:id : delete the "id" thImageInput.
     *
     * @param id the id of the thImageInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-image-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThImageInput(@PathVariable Long id) {
        log.debug("REST request to delete ThImageInput : {}", id);
        thImageInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thImageInput", id.toString())).build();
    }

}
