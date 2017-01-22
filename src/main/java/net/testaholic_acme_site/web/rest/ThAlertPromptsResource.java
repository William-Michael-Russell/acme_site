package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThAlertPrompts;
import net.testaholic_acme_site.repository.ThAlertPromptsRepository;
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
 * REST controller for managing ThAlertPrompts.
 */
@RestController
@RequestMapping("/api")
public class ThAlertPromptsResource {

    private final Logger log = LoggerFactory.getLogger(ThAlertPromptsResource.class);
        
    @Inject
    private ThAlertPromptsRepository thAlertPromptsRepository;
    
    /**
     * POST  /th-alert-prompts : Create a new thAlertPrompts.
     *
     * @param thAlertPrompts the thAlertPrompts to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thAlertPrompts, or with status 400 (Bad Request) if the thAlertPrompts has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-alert-prompts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThAlertPrompts> createThAlertPrompts(@RequestBody ThAlertPrompts thAlertPrompts) throws URISyntaxException {
        log.debug("REST request to save ThAlertPrompts : {}", thAlertPrompts);
        if (thAlertPrompts.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thAlertPrompts", "idexists", "A new thAlertPrompts cannot already have an ID")).body(null);
        }
        ThAlertPrompts result = thAlertPromptsRepository.save(thAlertPrompts);
        return ResponseEntity.created(new URI("/api/th-alert-prompts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thAlertPrompts", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-alert-prompts : Updates an existing thAlertPrompts.
     *
     * @param thAlertPrompts the thAlertPrompts to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thAlertPrompts,
     * or with status 400 (Bad Request) if the thAlertPrompts is not valid,
     * or with status 500 (Internal Server Error) if the thAlertPrompts couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-alert-prompts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThAlertPrompts> updateThAlertPrompts(@RequestBody ThAlertPrompts thAlertPrompts) throws URISyntaxException {
        log.debug("REST request to update ThAlertPrompts : {}", thAlertPrompts);
        if (thAlertPrompts.getId() == null) {
            return createThAlertPrompts(thAlertPrompts);
        }
        ThAlertPrompts result = thAlertPromptsRepository.save(thAlertPrompts);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thAlertPrompts", thAlertPrompts.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-alert-prompts : get all the thAlertPrompts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thAlertPrompts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-alert-prompts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThAlertPrompts>> getAllThAlertPrompts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThAlertPrompts");
        Page<ThAlertPrompts> page = thAlertPromptsRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-alert-prompts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-alert-prompts/:id : get the "id" thAlertPrompts.
     *
     * @param id the id of the thAlertPrompts to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thAlertPrompts, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-alert-prompts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThAlertPrompts> getThAlertPrompts(@PathVariable Long id) {
        log.debug("REST request to get ThAlertPrompts : {}", id);
        ThAlertPrompts thAlertPrompts = thAlertPromptsRepository.findOne(id);
        return Optional.ofNullable(thAlertPrompts)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-alert-prompts/:id : delete the "id" thAlertPrompts.
     *
     * @param id the id of the thAlertPrompts to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-alert-prompts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThAlertPrompts(@PathVariable Long id) {
        log.debug("REST request to delete ThAlertPrompts : {}", id);
        thAlertPromptsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thAlertPrompts", id.toString())).build();
    }

}
