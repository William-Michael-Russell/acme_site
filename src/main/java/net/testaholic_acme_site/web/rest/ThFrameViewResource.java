package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThFrameView;
import net.testaholic_acme_site.repository.ThFrameViewRepository;
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
 * REST controller for managing ThFrameView.
 */
@RestController
@RequestMapping("/api")
public class ThFrameViewResource {

    private final Logger log = LoggerFactory.getLogger(ThFrameViewResource.class);
        
    @Inject
    private ThFrameViewRepository thFrameViewRepository;
    
    /**
     * POST  /th-frame-views : Create a new thFrameView.
     *
     * @param thFrameView the thFrameView to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thFrameView, or with status 400 (Bad Request) if the thFrameView has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-frame-views",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThFrameView> createThFrameView(@RequestBody ThFrameView thFrameView) throws URISyntaxException {
        log.debug("REST request to save ThFrameView : {}", thFrameView);
        if (thFrameView.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thFrameView", "idexists", "A new thFrameView cannot already have an ID")).body(null);
        }
        ThFrameView result = thFrameViewRepository.save(thFrameView);
        return ResponseEntity.created(new URI("/api/th-frame-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thFrameView", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-frame-views : Updates an existing thFrameView.
     *
     * @param thFrameView the thFrameView to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thFrameView,
     * or with status 400 (Bad Request) if the thFrameView is not valid,
     * or with status 500 (Internal Server Error) if the thFrameView couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-frame-views",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThFrameView> updateThFrameView(@RequestBody ThFrameView thFrameView) throws URISyntaxException {
        log.debug("REST request to update ThFrameView : {}", thFrameView);
        if (thFrameView.getId() == null) {
            return createThFrameView(thFrameView);
        }
        ThFrameView result = thFrameViewRepository.save(thFrameView);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thFrameView", thFrameView.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-frame-views : get all the thFrameViews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thFrameViews in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-frame-views",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThFrameView>> getAllThFrameViews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThFrameViews");
        Page<ThFrameView> page = thFrameViewRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-frame-views");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-frame-views/:id : get the "id" thFrameView.
     *
     * @param id the id of the thFrameView to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thFrameView, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-frame-views/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThFrameView> getThFrameView(@PathVariable Long id) {
        log.debug("REST request to get ThFrameView : {}", id);
        ThFrameView thFrameView = thFrameViewRepository.findOne(id);
        return Optional.ofNullable(thFrameView)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-frame-views/:id : delete the "id" thFrameView.
     *
     * @param id the id of the thFrameView to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-frame-views/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThFrameView(@PathVariable Long id) {
        log.debug("REST request to delete ThFrameView : {}", id);
        thFrameViewRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thFrameView", id.toString())).build();
    }

}
