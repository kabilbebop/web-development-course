package org.weightcars.controller;

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
import org.weightcars.domain.Model;
import org.weightcars.repository.ModelRepository;

/**
 * REST controller for managing Model.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ModelController {

    private final Logger log = LoggerFactory.getLogger(ModelController.class);

    private final ModelRepository modelRepository;

    public ModelController(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    /**
     * POST  /models : Create a new model.
     *
     * @param model the model to create
     * @return the ResponseEntity with status 201 (Created) and with body the new model, or with status 400 (Bad Request) if the model has already an ID
     */
    @PostMapping("/models")
    public ResponseEntity<Model> createModel(@RequestBody Model model) {
        log.debug("REST request to save Model : {}", model);
        if (model.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Model result = modelRepository.save(model);
        return ResponseEntity.ok(result);
    }

    /**
     * PUT  /models : Updates an existing model.
     *
     * @param model the model to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated model, or with status 400 (Bad Request) if the model is not valid, or with
     * status 500 (Internal Server Error) if the model couldn't be updated
     */
    @PutMapping("/models")
    public ResponseEntity<Model> updateModel(@RequestBody Model model) {
        log.debug("REST request to update Model : {}", model);
        if (model.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Model result = modelRepository.save(model);
        return ResponseEntity.ok(result);
    }

    /**
     * GET  /models : get all the models.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of models in body
     */
    @GetMapping("/models")
    public List<Model> getAllModels() {
        log.debug("REST request to get all Models");
        return modelRepository.findAll();
    }

    /**
     * GET  /models/:id : get the "id" model.
     *
     * @param id the id of the model to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the model, or with status 404 (Not Found)
     */
    @GetMapping("/models/{id}")
    public ResponseEntity<Model> getModel(@PathVariable String id) {
        log.debug("REST request to get Model : {}", id);
        Optional<Model> result = modelRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE  /models/:id : delete the "id" model.
     *
     * @param id the id of the model to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/models/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable String id) {
        log.debug("REST request to delete Model : {}", id);

        modelRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
