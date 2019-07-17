package org.weightcars.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
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
    private static final String CARIMAGERY_COM_API = "http://www.carimagery.com/api.asmx/GetImageUrl?searchTerm=";

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Refresh given car image from carimagery
     */
    public Car refreshCarImage(Car car) {

        if (car.getImageUrl() == null) {
            // Get image url
            String searchTerms = car.getModel().getBrand().getName();
            if (car.getModel().getName() != null && !car.getModel().getName().isEmpty()) {
                searchTerms += " " + car.getModel().getName();
            }
            if (car.getVariant() != null && !car.getVariant().isEmpty()) {
                searchTerms += " " + car.getVariant();
            }
            try {
                searchTerms = URLEncoder.encode(searchTerms, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(null, e);
            }
            LOGGER.info("refreshCarImage with url {}", CARIMAGERY_COM_API + searchTerms);
            CarImage response = new RestTemplate().getForObject(CARIMAGERY_COM_API + searchTerms, CarImage.class);
            car.setImageUrl(response != null ? response.getUrl() : null);

            // Get image from url
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

            car = carRepository.save(car);
        }

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
