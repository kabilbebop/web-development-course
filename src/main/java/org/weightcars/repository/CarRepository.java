package org.weightcars.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weightcars.domain.Car;


/**
 * Spring Data  repository for the Car entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByModel_Manufacturer_name(String name);
    List<Car> findByModel_name(String name);
    List<Car> findByVariantOrOptions(String variant, String options);
}
