package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThToolTips;
import net.testaholic_acme_site.repository.ThToolTipsRepository;
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
 * REST controller for managing ThToolTips.
 */
@RestController
@RequestMapping("/api")
public class ThToolTipsResource {

    private final Logger log = LoggerFactory.getLogger(ThToolTipsResource.class);
        
    @Inject
    private ThToolTipsRepository thToolTipsRepository;
    
    /**
     * POST  /th-tool-tips : Create a new thToolTips.
     *
     * @param thToolTips the thToolTips to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thToolTips, or with status 400 (Bad Request) if the thToolTips has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-tool-tips",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThToolTips> createThToolTips(@RequestBody ThToolTips thToolTips) throws URISyntaxException {
        log.debug("REST request to save ThToolTips : {}", thToolTips);
        if (thToolTips.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thToolTips", "idexists", "A new thToolTips cannot already have an ID")).body(null);
        }
        ThToolTips result = thToolTipsRepository.save(thToolTips);
        return ResponseEntity.created(new URI("/api/th-tool-tips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thToolTips", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-tool-tips : Updates an existing thToolTips.
     *
     * @param thToolTips the thToolTips to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thToolTips,
     * or with status 400 (Bad Request) if the thToolTips is not valid,
     * or with status 500 (Internal Server Error) if the thToolTips couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-tool-tips",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThToolTips> updateThToolTips(@RequestBody ThToolTips thToolTips) throws URISyntaxException {
        log.debug("REST request to update ThToolTips : {}", thToolTips);
        if (thToolTips.getId() == null) {
            return createThToolTips(thToolTips);
        }
        ThToolTips result = thToolTipsRepository.save(thToolTips);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thToolTips", thToolTips.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-tool-tips : get all the thToolTips.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thToolTips in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-tool-tips",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThToolTips>> getAllThToolTips(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThToolTips");
        Page<ThToolTips> page = thToolTipsRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-tool-tips");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-tool-tips/:id : get the "id" thToolTips.
     *
     * @param id the id of the thToolTips to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thToolTips, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-tool-tips/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThToolTips> getThToolTips(@PathVariable Long id) {
        log.debug("REST request to get ThToolTips : {}", id);
        ThToolTips thToolTips = thToolTipsRepository.findOne(id);
        return Optional.ofNullable(thToolTips)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-tool-tips/:id : delete the "id" thToolTips.
     *
     * @param id the id of the thToolTips to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-tool-tips/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThToolTips(@PathVariable Long id) {
        log.debug("REST request to delete ThToolTips : {}", id);
        thToolTipsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thToolTips", id.toString())).build();
    }

}
