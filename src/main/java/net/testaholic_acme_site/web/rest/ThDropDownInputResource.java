package net.testaholic_acme_site.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.testaholic_acme_site.domain.ThDropDownInput;
import net.testaholic_acme_site.repository.ThDropDownInputRepository;
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
 * REST controller for managing ThDropDownInput.
 */
@RestController
@RequestMapping("/api")
public class ThDropDownInputResource {

    private final Logger log = LoggerFactory.getLogger(ThDropDownInputResource.class);

    @Inject
    private ThDropDownInputRepository thDropDownInputRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /th-drop-down-inputs : Create a new thDropDownInput.
     *
     * @param thDropDownInput the thDropDownInput to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thDropDownInput, or with status 400 (Bad Request) if the thDropDownInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-drop-down-inputs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThDropDownInput> createThDropDownInput(@Valid @RequestBody ThDropDownInput thDropDownInput) throws URISyntaxException {
        log.debug("REST request to save ThDropDownInput : {}", thDropDownInput);
        if (thDropDownInput.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("thDropDownInput", "idexists", "A new thDropDownInput cannot already have an ID")).body(null);
        }
        thDropDownInput.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        ThDropDownInput result = thDropDownInputRepository.save(thDropDownInput);
        return ResponseEntity.created(new URI("/api/th-drop-down-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("thDropDownInput", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /th-drop-down-inputs : Updates an existing thDropDownInput.
     *
     * @param thDropDownInput the thDropDownInput to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thDropDownInput,
     * or with status 400 (Bad Request) if the thDropDownInput is not valid,
     * or with status 500 (Internal Server Error) if the thDropDownInput couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/th-drop-down-inputs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThDropDownInput> updateThDropDownInput(@Valid @RequestBody ThDropDownInput thDropDownInput) throws URISyntaxException {
        log.debug("REST request to update ThDropDownInput : {}", thDropDownInput);
        if (thDropDownInput.getId() == null) {
            return createThDropDownInput(thDropDownInput);
        }
        ThDropDownInput result = thDropDownInputRepository.save(thDropDownInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("thDropDownInput", thDropDownInput.getId().toString()))
            .body(result);
    }

    /**
     * GET  /th-drop-down-inputs : get all the thDropDownInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thDropDownInputs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/th-drop-down-inputs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ThDropDownInput>> getAllThDropDownInputs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThDropDownInputs");
        Page<ThDropDownInput> page = thDropDownInputRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/th-drop-down-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /th-drop-down-inputs/:id : get the "id" thDropDownInput.
     *
     * @param id the id of the thDropDownInput to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thDropDownInput, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/th-drop-down-inputs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ThDropDownInput> getThDropDownInput(@PathVariable Long id) {
        log.debug("REST request to get ThDropDownInput : {}", id);
        ThDropDownInput thDropDownInput = thDropDownInputRepository.findOne(id);
        return Optional.ofNullable(thDropDownInput)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /th-drop-down-inputs/:id : delete the "id" thDropDownInput.
     *
     * @param id the id of the thDropDownInput to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/th-drop-down-inputs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteThDropDownInput(@PathVariable Long id) {
        log.debug("REST request to delete ThDropDownInput : {}", id);
        thDropDownInputRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("thDropDownInput", id.toString())).build();
    }

}
