package org.avenue1.avo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    private Set<Vessel> vessels = new HashSet<>();
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

    public Set<Vessel> getVessels() {
        return vessels;
    }

    public Container vessels(Set<Vessel> vessels) {
        this.vessels = vessels;
        return this;
    }

    public Container addVessel(Vessel vessel) {
        this.vessels.add(vessel);
        vessel.setContainer(this);
        return this;
    }

    public Container removeVessel(Vessel vessel) {
        this.vessels.remove(vessel);
        vessel.setContainer(null);
        return this;
    }

    public void setVessels(Set<Vessel> vessels) {
        this.vessels = vessels;
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
