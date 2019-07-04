package org.weightcars.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.weightcars.domain.Car;
import org.weightcars.domain.CarImage;
import org.weightcars.repository.CarRepository;

/**
 * TD7 : manage Car images
 */
@Service
public class CarService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Refresh given car image from carimagery
     */
    public Car refreshCarImage(Car car) {
        LOGGER.info("refreshCarImage for {}", car.toString());

        // Get image url
        String apiUrl = "http://www.carimagery.com/api.asmx/GetImageUrl?searchTerm=" /*+ car.getModel().getBrand().getName()*/;
//        if (StringUtils.isNotEmpty(car.getModel().getName())) {
//            apiUrl += "+" + car.getModel().getName();
//        }
//        if (StringUtils.isNotEmpty(car.getVariant())) {
//            apiUrl += "+" + car.getVariant();
//        }
        CarImage response = new RestTemplate().getForObject(apiUrl, CarImage.class);
        car.setImageUrl(response != null ? response.getUrl() : null);

        // Get image from url
        CarImage responseUrl = null;
        try {
            if (response.getUrl() != null) {
                BufferedImage image = ImageIO.read(new URL(response.getUrl()));
                if (image != null) {
                    // Save image as byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", baos);
                    baos.flush();
                    byte[] imageInByte = baos.toByteArray();
                    baos.close();
                    car.setImage(imageInByte);
                }
            }
        } catch (IOException e) {
            LOGGER.error(null, e);
        }

        carRepository.save(car);

        return car;
    }

    /**
     * Batch refresh database images
     */
    //@Scheduled(fixedDelay = 24 * 3600 * 1000) // each days
    public void refreshAllCarImages() {
        LOGGER.info("refresh all car images");
        carRepository.findAll().forEach(this::refreshCarImage);
    }
}
