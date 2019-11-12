package org.weightcars.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.weightcars.domain.Car;


/**
 * Spring Data  repository for the Car entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByModel_Brand_nameLikeIgnoreCase(String name);

    List<Car> findByModel_nameLikeIgnoreCase(String name);

    List<Car> findByNameLikeIgnoreCase(String name);

    List<Car> findTop10ByOrderByPowerDesc();

    List<Car> findTop10ByOrderByWeightAsc();

    @Query("from Car where weight is not null and weight <> 0 and power is not null and power <> 0 order by weight/power asc")
    List<Car> findAllOrderByRatio();
}
