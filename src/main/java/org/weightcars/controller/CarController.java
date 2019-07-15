package org.weightcars.controller;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weightcars.domain.Brand;
import org.weightcars.domain.Car;
import org.weightcars.domain.Model;
import org.weightcars.repository.BrandRepository;
import org.weightcars.repository.CarRepository;
import org.weightcars.service.CarService;

/**
 * REST controller for managing Car.
 */
@RestController
@RequestMapping("/api")
public class CarController {

    private final Logger log = LoggerFactory.getLogger(CarController.class);

    private final BrandRepository brandRepository;

    private final CarRepository carRepository;

    private final CarService carService;

    CarController(BrandRepository brandRepository, CarRepository carRepository, CarService carService) {
        this.brandRepository = brandRepository;
        this.carRepository = carRepository;
        this.carService = carService;
    }

    /**
     * GET  /cars : get all the cars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     */
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        log.debug("REST request to get all Cars grouped by Brands and Models");
        List<Car> cars = carRepository.findAll();
        return cars.stream()
            .sorted(Comparator.naturalOrder())
            .limit(10)
            .map(carService::refreshCarImage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /cars/:id : get the "id" car.
     *
     * @param id the id of the car to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car, or with status 404 (Not Found)
     */
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        log.debug("REST request to get Car : {}", id);
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            carService.refreshCarImage(carOptional.get());
            return ResponseEntity.ok(carOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET  /cars : search cars from given search string.
     *
     * @param searchString String that match any car information (variant, option, model, brand)
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     */
    @GetMapping("/cars/search/{searchString}")
    public List<Car> searchCarsByString(@PathVariable String searchString) {
        log.debug("REST request to get Cars from given string {}", searchString);

        String like = String.format("%%%s%%", searchString);

        // Search cars by manufacture name
        List<Car> carsByBrand = carRepository.findByModel_Brand_nameLikeIgnoreCase(like);

        // Search cars by model name
        List<Car> carsByModel = carRepository.findByModel_nameLikeIgnoreCase(like);

        // Search cars by variant or options
        List<Car> carsByVariantOrOptions = carRepository.findByVariantLikeIgnoreCaseOrOptionsLikeIgnoreCase(like, like);

        // Add all cars to new result list
        Set<Car> set = new HashSet<>();
        set.addAll(carsByBrand);
        set.addAll(carsByModel);
        set.addAll(carsByVariantOrOptions);

        return set.stream()
            .sorted(Comparator.naturalOrder())
            .limit(10)
            .map(carService::refreshCarImage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /cars : search cars from given search string.
     *
     * @param criteria String that match one of 'weight', 'ratio' or 'power'
     * @param number Max number of cars returned
     * @return the ResponseEntity with status 200 (OK) and the list of brands, including models including cars in body
     */
    @GetMapping("/cars/top/{criteria}/{number}")
    public Set<Brand> topCars(@PathVariable String criteria, @PathVariable Integer number) {
        log.debug("REST request to get {} top cars regarding {}", number, criteria);

        List<Car> topCars = null;
        switch (criteria) {
            case "weight":
                topCars = carRepository.findTop10ByOrderByRealWeightAsc();
                break;
            case "power":
                topCars = carRepository.findTop10ByOrderByPowerDesc();
                break;
            case "ratio":
                topCars = carRepository.findAllOrderByRatio();
                break;
            default:
                throw new UnsupportedOperationException();
        }

        final List<Car> cars = topCars.stream().limit(number).map(carService::refreshCarImage).collect(Collectors.toList());
        Set<Model> models = cars.stream().map(car -> car.getModel()).collect(Collectors.toSet());
        Set<Brand> brands = models.stream().map(model -> model.getBrand()).collect(Collectors.toSet());
        models.forEach(model -> model.setCars(cars.stream().filter(car -> car.getModel().equals(model)).collect(Collectors.toList())));
        brands.forEach(brand -> brand.setModels(models.stream().filter(model -> model.getBrand().equals(brand)).collect(Collectors.toList())));

        return brands;
    }
}
