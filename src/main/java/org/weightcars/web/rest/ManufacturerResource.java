package org.weightcars.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.weightcars.domain.Manufacturer;
import org.weightcars.repository.ManufacturerRepository;
import org.weightcars.web.rest.errors.BadRequestAlertException;
import org.weightcars.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api")
public class ManufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ManufacturerResource.class);

    private static final String ENTITY_NAME = "manufacturer";

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerResource(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    /**
     * POST  /manufacturers : Create a new manufacturer.
     *
     * @param manufacturer the manufacturer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manufacturer, or with status 400 (Bad Request) if the manufacturer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/manufacturers")
    @Timed
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        if (manufacturer.getId() != null) {
            throw new BadRequestAlertException("A new manufacturer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Manufacturer result = manufacturerRepository.save(manufacturer);
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
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/manufacturers")
    @Timed
    public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to update Manufacturer : {}", manufacturer);
        if (manufacturer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Manufacturer result = manufacturerRepository.save(manufacturer);
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
    public List<Manufacturer> getAllManufacturers() {
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
    public ResponseEntity<Manufacturer> getManufacturer(@PathVariable Long id) {
        log.debug("REST request to get Manufacturer : {}", id);
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
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
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        log.debug("REST request to delete Manufacturer : {}", id);

        manufacturerRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
