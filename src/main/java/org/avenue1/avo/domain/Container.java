package org.avenue1.avo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Container.
 */
@Document(collection = "container")
public class Container implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @DBRef
    @Field("vessel")
    @JsonIgnoreProperties("containers")
    private Vessel vessel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Container name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public Container vessel(Vessel vessel) {
        this.vessel = vessel;
        return this;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
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
        Container container = (Container) o;
        if (container.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), container.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Container{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
