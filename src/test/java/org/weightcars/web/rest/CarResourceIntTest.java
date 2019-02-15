package org.weightcars.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.weightcars.web.rest.TestUtil.createFormattingConversionService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;
import org.weightcars.WeightCarsApp;
import org.weightcars.domain.Brand;
import org.weightcars.domain.Car;
import org.weightcars.domain.Model;
import org.weightcars.repository.CarRepository;
import org.weightcars.service.CarService;

/**
 * Test class for the CarResource REST controller.
 *
 * @see CarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeightCarsApp.class)
public class CarResourceIntTest {

    private static final Logger logger = LoggerFactory.getLogger(CarResourceIntTest.class);

    private static final String DEFAULT_MANUFACTURER = "RRRRRRR";
    private static final String DEFAULT_MODEL = "MMMMMMM";

    private static final String DEFAULT_VARIANT = "AAAAAAAAAA";
    private static final String UPDATED_VARIANT = "BBBBBBBBBB";

    private static final Double DEFAULT_POWER = 1D;
    private static final Double UPDATED_POWER = 2D;

    private static final Double DEFAULT_REAL_WEIGHT = 1D;
    private static final Double UPDATED_REAL_WEIGHT = 2D;

    private static final Double DEFAULT_OFFICIAL_WEIGHT = 1D;
    private static final Double UPDATED_OFFICIAL_WEIGHT = 2D;

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCarMockMvc;

    private Car car;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarResource carResource = new CarResource(carRepository, carService);
        this.restCarMockMvc = MockMvcBuilders.standaloneSetup(carResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createEntity(EntityManager em) {

        Brand brand = new Brand();
        brand.setName(DEFAULT_MANUFACTURER);

        Model model = new Model();
        model.setName(DEFAULT_MODEL);
        model.setManufacturer(brand);

        return new Car()
            .variant(DEFAULT_VARIANT)
            .power(DEFAULT_POWER)
            .realWeight(DEFAULT_REAL_WEIGHT)
            .officialWeight(DEFAULT_OFFICIAL_WEIGHT)
            .options(DEFAULT_OPTIONS)
            .startDate(DEFAULT_START_DATE)
            .model(model);
    }

    @Before
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        assertCarEqual(databaseSizeBeforeCreate + 1, DEFAULT_VARIANT, DEFAULT_POWER, DEFAULT_REAL_WEIGHT, DEFAULT_OFFICIAL_WEIGHT, DEFAULT_OPTIONS, DEFAULT_START_DATE);
    }

    private void assertCarEqual(int i, String defaultVariant, Double defaultPower, Double defaultRealWeight, Double defaultOfficialWeight, String defaultOptions, LocalDate defaultStartDate) {
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(i);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getVariant()).isEqualTo(defaultVariant);
        assertThat(testCar.getPower()).isEqualTo(defaultPower);
        assertThat(testCar.getRealWeight()).isEqualTo(defaultRealWeight);
        assertThat(testCar.getOfficialWeight()).isEqualTo(defaultOfficialWeight);
        assertThat(testCar.getOptions()).isEqualTo(defaultOptions);
        assertThat(testCar.getStartDate()).isEqualTo(defaultStartDate);
    }

    @Test
    @Transactional
    public void createCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car with an existing ID
        car.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        Assertions.assertThatExceptionOfType(NestedServletException.class).isThrownBy(() ->
            restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car))));


        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId()))
            .andExpect(jsonPath("$.variant").value(DEFAULT_VARIANT))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER))
            .andExpect(jsonPath("$.realWeight").value(DEFAULT_REAL_WEIGHT))
            .andExpect(jsonPath("$.officialWeight").value(DEFAULT_OFFICIAL_WEIGHT))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", String.valueOf(Long.MAX_VALUE)))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Optional<Car> updatedCarOptional = carRepository.findById(car.getId());
        assertThat(updatedCarOptional.isPresent()).isTrue();
        Car updatedCar = updatedCarOptional.get();
        // Disconnect from session so that the updates on updatedCar are not directly saved in db
        em.detach(updatedCar);
        updatedCar
            .variant(UPDATED_VARIANT)
            .power(UPDATED_POWER)
            .realWeight(UPDATED_REAL_WEIGHT)
            .officialWeight(UPDATED_OFFICIAL_WEIGHT)
            .options(UPDATED_OPTIONS)
            .startDate(UPDATED_START_DATE);

        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCar)))
            .andExpect(status().isOk());

        // Validate the Car in the database
        assertCarEqual(databaseSizeBeforeUpdate, UPDATED_VARIANT, UPDATED_POWER, UPDATED_REAL_WEIGHT, UPDATED_OFFICIAL_WEIGHT, UPDATED_OPTIONS, UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Create the Car

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        Assertions.assertThatExceptionOfType(NestedServletException.class).isThrownBy(() ->restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car))));

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Get the car
        restCarMockMvc.perform(delete("/api/cars/{id}", car.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void testSearch() throws Exception {

        Car car1 = new Car();
        car1.setModel(car.getModel());
        car1.setVariant("toto");

        Car car2 = new Car();
        car2.setModel(car.getModel());
        car2.setVariant("tutu");

        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);

        List<Car> cars = carRepository.findAll();


        String result = restCarMockMvc.perform(get("/api/cars/search/tutu"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].variant").value(hasItem(car2.getVariant())))
            .andReturn().getResponse().getContentAsString();
        logger.debug(result);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car.class);
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(car1.getId());
        assertThat(car1).isEqualTo(car2);
        car2.setId(2L);
        assertThat(car1).isNotEqualTo(car2);
        car1.setId(null);
        assertThat(car1).isNotEqualTo(car2);
    }
}
