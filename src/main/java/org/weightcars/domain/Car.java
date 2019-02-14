package org.weightcars.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
public class Car implements Serializable, Comparable<Car> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant")
    private String variant;

    @Column(name = "power")
    private Double power;

    @Column(name = "real_weight")
    private Double realWeight;

    @Column(name = "official_weight")
    private Double officialWeight;

    @Column(name = "options")
    private String options;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Model model;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public Car variant(String variant) {
        this.variant = variant;
        return this;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Double getPower() {
        return power;
    }

    public Car power(Double power) {
        this.power = power;
        return this;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getRealWeight() {
        return realWeight;
    }

    public Car realWeight(Double realWeight) {
        this.realWeight = realWeight;
        return this;
    }

    public void setRealWeight(Double realWeight) {
        this.realWeight = realWeight;
    }

    public Double getOfficialWeight() {
        return officialWeight;
    }

    public Car officialWeight(Double officialWeight) {
        this.officialWeight = officialWeight;
        return this;
    }

    public void setOfficialWeight(Double officialWeight) {
        this.officialWeight = officialWeight;
    }

    public String getOptions() {
        return options;
    }

    public Car options(String options) {
        this.options = options;
        return this;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Car startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Model getModel() {
        return model;
    }

    public Car model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Car setImage(String image) {
        this.image = image;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", variant='" + getVariant() + "'" +
            ", power=" + getPower() +
            ", realWeight=" + getRealWeight() +
            ", officialWeight=" + getOfficialWeight() +
            ", options='" + getOptions() + "'" +
            ", startDate='" + getStartDate() + "'" +
            "}";
    }

    @Override
    public int compareTo(Car other) {
        int result;
        if (other == null) {
            result = -1; // null first
        } else {
            result = this.getModel().compareTo(other.getModel());
            if (result == 0) {
                result = StringUtils.compareIgnoreCase(this.getVariant(), other.getVariant());
            }
        }
        return result;
    }
}
