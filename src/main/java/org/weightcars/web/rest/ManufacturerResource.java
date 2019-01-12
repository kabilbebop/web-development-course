package org.weightcars.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weightcars.domain.Brand;
import org.weightcars.repository.ManufacturerRepository;
import org.weightcars.web.rest.errors.BadRequestAlertException;
import org.weightcars.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api")
public class ManufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ManufacturerResource.class);

    private static final String ENTITY_NAME = "manufacturer";

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    /**
     * POST  /manufacturers : Create a new manufacturer.
     *
     * @param manufacturer the manufacturer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manufacturer, or with status 400 (Bad Request) if the manufacturer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manufacturers")
    @Timed
    public ResponseEntity<Brand> createManufacturer(@RequestBody Brand manufacturer) throws URISyntaxException {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        if (manufacturer.getId() != null) {
            throw new BadRequestAlertException("A new manufacturer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Brand result = manufacturerRepository.save(manufacturer);
        return ResponseEntity.created(new URI("/api/manufacturers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /manufacturers : Updates an existing manufacturer.
     *
     * @param manufacturer the manufacturer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated manufacturer,
     * or with status 400 (Bad Request) if the manufacturer is not valid,
     * or with status 500 (Internal Server Error) if the manufacturer couldn't be updated
     */
    @PutMapping("/manufacturers")
    @Timed
    public ResponseEntity<Brand> updateManufacturer(@RequestBody Brand manufacturer) {
        log.debug("REST request to update Manufacturer : {}", manufacturer);
        if (manufacturer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Brand result = manufacturerRepository.save(manufacturer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, manufacturer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /manufacturers : get all the manufacturers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of manufacturers in body
     */
    @GetMapping("/manufacturers")
    @Timed
    public List<Brand> getAllManufacturers() {
        log.debug("REST request to get all Manufacturers");
        return manufacturerRepository.findAll();
    }

    /**
     * GET  /manufacturers/:id : get the "id" manufacturer.
     *
     * @param id the id of the manufacturer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the manufacturer, or with status 404 (Not Found)
     */
    @GetMapping("/manufacturers/{id}")
    @Timed
    public ResponseEntity<Brand> getManufacturer(@PathVariable String id) {
        log.debug("REST request to get Manufacturer : {}", id);
        Optional<Brand> manufacturer = manufacturerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(manufacturer);
    }

    /**
     * DELETE  /manufacturers/:id : delete the "id" manufacturer.
     *
     * @param id the id of the manufacturer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/manufacturers/{id}")
    @Timed
    public ResponseEntity<Void> deleteManufacturer(@PathVariable String id) {
        log.debug("REST request to delete Manufacturer : {}", id);

        manufacturerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
