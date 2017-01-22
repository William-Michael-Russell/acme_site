package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThSelectorPaths;
import net.testaholic_acme_site.repository.ThSelectorPathsRepository;
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
 * REST controller for managing ThSelectorPaths.
 */
@RestController
@RequestMapping("/api")
public class ThSelectorPathsResource {

    private final Logger log = LoggerFactory.getLogger(ThSelectorPathsResource.class);
        
    @Inject
    private ThSelectorPathsRepository thSelectorPathsRepository;
    
    /**
     * POST  /th-selector-paths : Create a new thSelectorPaths.
     *
     * @param thSelectorPaths the thSelectorPaths to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thSelectorPaths, or with status 400 (Bad Request) if the thSelectorPaths has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-selector-paths",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThSelectorPaths> createThSelectorPaths(@RequestBody ThSelectorPaths thSelectorPaths) throws URISyntaxException {
        log.debug("REST request to save ThSelectorPaths : {}", thSelectorPaths);
        if (thSelectorPaths.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thSelectorPaths", "idexists", "A new thSelectorPaths cannot already have an ID")).body(null);
        }
        ThSelectorPaths result = thSelectorPathsRepository.save(thSelectorPaths);
        return ResponseEntity.created(new URI("/api/th-selector-paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thSelectorPaths", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-selector-paths : Updates an existing thSelectorPaths.
     *
     * @param thSelectorPaths the thSelectorPaths to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thSelectorPaths,
     * or with status 400 (Bad Request) if the thSelectorPaths is not valid,
     * or with status 500 (Internal Server Error) if the thSelectorPaths couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-selector-paths",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThSelectorPaths> updateThSelectorPaths(@RequestBody ThSelectorPaths thSelectorPaths) throws URISyntaxException {
        log.debug("REST request to update ThSelectorPaths : {}", thSelectorPaths);
        if (thSelectorPaths.getId() == null) {
            return createThSelectorPaths(thSelectorPaths);
        }
        ThSelectorPaths result = thSelectorPathsRepository.save(thSelectorPaths);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thSelectorPaths", thSelectorPaths.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-selector-paths : get all the thSelectorPaths.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thSelectorPaths in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-selector-paths",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThSelectorPaths>> getAllThSelectorPaths(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThSelectorPaths");
        Page<ThSelectorPaths> page = thSelectorPathsRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-selector-paths");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-selector-paths/:id : get the "id" thSelectorPaths.
     *
     * @param id the id of the thSelectorPaths to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thSelectorPaths, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-selector-paths/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThSelectorPaths> getThSelectorPaths(@PathVariable Long id) {
        log.debug("REST request to get ThSelectorPaths : {}", id);
        ThSelectorPaths thSelectorPaths = thSelectorPathsRepository.findOne(id);
        return Optional.ofNullable(thSelectorPaths)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-selector-paths/:id : delete the "id" thSelectorPaths.
     *
     * @param id the id of the thSelectorPaths to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-selector-paths/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThSelectorPaths(@PathVariable Long id) {
        log.debug("REST request to delete ThSelectorPaths : {}", id);
        thSelectorPathsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thSelectorPaths", id.toString())).build();
    }

}
