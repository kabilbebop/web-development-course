package org.weightcars.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.weightcars.domain.Car;
import org.weightcars.domain.CarImage;
import org.weightcars.repository.CarRepository;
import org.weightcars.web.rest.errors.BadRequestAlertException;
import org.weightcars.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Car.
 */
@RestController
@RequestMapping("/api")
public class CarResource {

    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    private static final String ENTITY_NAME = "car";

    private final CarRepository carRepository;

    public CarResource(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * POST  /cars : Create a new car.
     *
     * @param car the car to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car, or with status 400 (Bad Request) if the car has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cars")
    @Timed
    public ResponseEntity<Car> createCar(@RequestBody Car car) throws URISyntaxException {
        log.debug("REST request to save Car : {}", car);
        if (car.getId() != null) {
            throw new BadRequestAlertException("A new car cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Car result = carRepository.save(car);
        return ResponseEntity.created(new URI("/api/cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cars : Updates an existing car.
     *
     * @param car the car to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car,
     * or with status 400 (Bad Request) if the car is not valid,
     * or with status 500 (Internal Server Error) if the car couldn't be updated
     */
    @PutMapping("/cars")
    @Timed
    public ResponseEntity<Car> updateCar(@RequestBody Car car) {
        log.debug("REST request to update Car : {}", car);
        if (car.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Car result = carRepository.save(car);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cars : get all the cars.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     */
    @GetMapping("/cars")
    @Timed
    public List<Car> getAllCars() {
        log.debug("REST request to get all Cars");
        List<Car> cars = carRepository.findAll();
        cars = cars.stream()
            .sorted(Comparator.comparing(car -> car.getModel().getName()))
            .limit(10)
            .map(car -> refreshCarImage(car))
            .collect(Collectors.toList());
        cars.sort(Comparator.naturalOrder());
        return cars;
    }

    /**
     * GET  /cars/:id : get the "id" car.
     *
     * @param id the id of the car to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car, or with status 404 (Not Found)
     */
    @GetMapping("/cars/{id}")
    @Timed
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        log.debug("REST request to get Car : {}", id);
        Optional<Car> car = carRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(car);
    }

    /**
     * DELETE  /cars/:id : delete the "id" car.
     *
     * @param id the id of the car to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cars/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        log.debug("REST request to delete Car : {}", id);

        carRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /cars : search cars from given search string.
     * @param searchString String that match any car information (variant, option, model, manufacturer)
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     */
    @GetMapping("/cars/search/{searchString}")
    @Timed
    public List<Car> searchCarsByString(@PathVariable String searchString) {
        log.debug("REST request to get Cars from given string {}", searchString);

        String like = String.format("%%%s%%", searchString);

        // Search cars by manufacture name
        List<Car> carsByManufacturer = carRepository.findByModel_Manufacturer_nameLikeIgnoreCase(like);

        // Search cars by model name
        List<Car> carsByModel = carRepository.findByModel_nameLikeIgnoreCase(like);

        // Search cars by variant or options
        List<Car> carsByVariantOrOptions = carRepository.findByVariantLikeIgnoreCaseOrOptionsLikeIgnoreCase(like, like);

        // Add all cars to new result list
        Set<Car> set = new HashSet<>();
        set.addAll(carsByManufacturer);
        set.addAll(carsByModel);
        set.addAll(carsByVariantOrOptions);

        List<Car> result = set.stream()
            .sorted(Comparator.naturalOrder())
            .limit(10)
            .map(car -> refreshCarImage(car))
            .collect(Collectors.toList());
        return result;
    }

    /**
     * GET  /cars : search cars from given search string.
     * @param criteria String that match one of 'weight', 'ratio' or 'power'
     * @param number Max number of cars returned
     * @return the ResponseEntity with status 200 (OK) and the list of cars in body
     */
    @GetMapping("/cars/top/{criteria}/{number}")
    @Timed
    public List<Car> topCars(@PathVariable String criteria, @PathVariable Integer number) {
        log.debug("REST request to get {} top cars regarding {}", number, criteria);

        List<Car> result;
        switch (criteria) {
            case "weight":
                result = carRepository.findTop10ByOrderByRealWeightAsc();
                break;
            case "power":
                result = carRepository.findTop10ByOrderByPowerDesc();
                break;
            case "ratio":
                result = carRepository.findAllOrderByRatio();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return result.stream()
            .limit(number)
            .map(car -> refreshCarImage(car))
            .collect(Collectors.toList());
    }

    public Car refreshCarImage(Car car) {
        String url = "http://www.carimagery.com/api.asmx/GetImageUrl?searchTerm=" + car.getModel().getManufacturer().getName();
        if (StringUtils.isNotEmpty(car.getModel().getName())) {
            url += "+" + car.getModel().getName();
        }
        if (StringUtils.isNotEmpty(car.getVariant())) {
            url += "+" + car.getVariant();
        }

        CarImage response = new RestTemplate().getForObject(url, CarImage.class);

        car.setImage(response != null ? response.getUrl() : null);
        carRepository.save(car);

        return car;
    }
}
