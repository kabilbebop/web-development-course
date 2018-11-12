package org.weightcars.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.GenericGenerator;

/**
 * A Manufacturer.
 */
@Entity
@Table(name = "manufacturer")
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    public Manufacturer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        Manufacturer manufacturer = (Manufacturer) o;
        if (manufacturer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manufacturer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
