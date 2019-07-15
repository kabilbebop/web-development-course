package org.weightcars.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weightcars.domain.Brand;
import org.weightcars.repository.BrandRepository;

/**
 * REST controller for managing Brand.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class BrandController {

    private final Logger log = LoggerFactory.getLogger(BrandController.class);

    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * POST  /manufacturers : Create a new manufacturer.
     *
     * @param brand the manufacturer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new manufacturer, or with status 400 (Bad Request) if the manufacturer has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brands")
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) throws URISyntaxException {
        log.debug("REST request to save Brand : {}", brand);
        if (brand.getId() != null) {
            ResponseEntity.badRequest().build();
        }
        Brand result = brandRepository.save(brand);
        return ResponseEntity.created(new URI("/api/brands/" + result.getId()))
            .body(result);
    }

    /**
     * PUT  /brands : Updates an existing brand.
     *
     * @param brand the brand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brand, or with status 400 (Bad Request) if the brand is not valid, or with
     * status 500 (Internal Server Error) if the brand couldn't be updated
     */
    @PutMapping("/brands")
    public ResponseEntity<Brand> updateBrand(@RequestBody Brand brand) {
        log.debug("REST request to update Brand : {}", brand);
        if (brand.getId() == null) {
            ResponseEntity.badRequest().build();
        }
        Brand result = brandRepository.save(brand);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /brands : get all the brands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of brands in body
     */
    @GetMapping("/brands")
    public List<Brand> getAllBrands() {
        log.debug("REST request to get all Brands");
        return brandRepository.findAll();
    }

    /**
     * GET  /brands/:id : get the "id" brand.
     *
     * @param id the id of the brand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brand, or with status 404 (Not Found)
     */
    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable String id) {
        log.debug("REST request to get Brand : {}", id);
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE  /brands/:id : delete the "id" brand.
     *
     * @param id the id of the brand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String id) {
        log.debug("REST request to delete Brand : {}", id);

        brandRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
