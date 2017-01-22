package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThScrollingView;
import net.testaholic_acme_site.repository.ThScrollingViewRepository;
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
 * REST controller for managing ThScrollingView.
 */
@RestController
@RequestMapping("/api")
public class ThScrollingViewResource {

    private final Logger log = LoggerFactory.getLogger(ThScrollingViewResource.class);
        
    @Inject
    private ThScrollingViewRepository thScrollingViewRepository;
    
    /**
     * POST  /th-scrolling-views : Create a new thScrollingView.
     *
     * @param thScrollingView the thScrollingView to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thScrollingView, or with status 400 (Bad Request) if the thScrollingView has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-scrolling-views",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThScrollingView> createThScrollingView(@RequestBody ThScrollingView thScrollingView) throws URISyntaxException {
        log.debug("REST request to save ThScrollingView : {}", thScrollingView);
        if (thScrollingView.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thScrollingView", "idexists", "A new thScrollingView cannot already have an ID")).body(null);
        }
        ThScrollingView result = thScrollingViewRepository.save(thScrollingView);
        return ResponseEntity.created(new URI("/api/th-scrolling-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thScrollingView", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-scrolling-views : Updates an existing thScrollingView.
     *
     * @param thScrollingView the thScrollingView to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thScrollingView,
     * or with status 400 (Bad Request) if the thScrollingView is not valid,
     * or with status 500 (Internal Server Error) if the thScrollingView couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-scrolling-views",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThScrollingView> updateThScrollingView(@RequestBody ThScrollingView thScrollingView) throws URISyntaxException {
        log.debug("REST request to update ThScrollingView : {}", thScrollingView);
        if (thScrollingView.getId() == null) {
            return createThScrollingView(thScrollingView);
        }
        ThScrollingView result = thScrollingViewRepository.save(thScrollingView);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thScrollingView", thScrollingView.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-scrolling-views : get all the thScrollingViews.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thScrollingViews in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-scrolling-views",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThScrollingView>> getAllThScrollingViews(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThScrollingViews");
        Page<ThScrollingView> page = thScrollingViewRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-scrolling-views");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-scrolling-views/:id : get the "id" thScrollingView.
     *
     * @param id the id of the thScrollingView to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thScrollingView, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-scrolling-views/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThScrollingView> getThScrollingView(@PathVariable Long id) {
        log.debug("REST request to get ThScrollingView : {}", id);
        ThScrollingView thScrollingView = thScrollingViewRepository.findOne(id);
        return Optional.ofNullable(thScrollingView)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-scrolling-views/:id : delete the "id" thScrollingView.
     *
     * @param id the id of the thScrollingView to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-scrolling-views/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThScrollingView(@PathVariable Long id) {
        log.debug("REST request to delete ThScrollingView : {}", id);
        thScrollingViewRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thScrollingView", id.toString())).build();
    }

}
