package org.weightcars.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.weightcars.domain.Car;
import org.weightcars.domain.CarImage;
import org.weightcars.repository.CarRepository;

@Service
public class CarService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car refreshCarImage(Car car) {
        LOGGER.info("refreshCarImage for {}", car.toString());
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

    @Scheduled(fixedDelay = 24 * 3600 * 1000)
    public void refreshAllCarImages() {
        LOGGER.info("refresh all car images");
        carRepository.findAll().forEach(this::refreshCarImage);
    }
}
