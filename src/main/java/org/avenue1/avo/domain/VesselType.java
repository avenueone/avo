package org.avenue1.avo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.avenue1.avo.domain.enumeration.VesselTypeEnum;

/**
 * A VesselType.
 */
@Document(collection = "vessel_type")
public class VesselType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("type")
    private VesselTypeEnum type;

    @Field("recurring")
    private Boolean recurring;

    @Field("day_of_month")
    private Integer dayOfMonth;

    @Field("day_of_week")
    private Integer dayOfWeek;

    @Field("month")
    private Integer month;

    @DBRef
    @Field("vessels")
    @JsonIgnore
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

    public VesselType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VesselTypeEnum getType() {
        return type;
    }

    public VesselType type(VesselTypeEnum type) {
        this.type = type;
        return this;
    }

    public void setType(VesselTypeEnum type) {
        this.type = type;
    }

    public Boolean isRecurring() {
        return recurring;
    }

    public VesselType recurring(Boolean recurring) {
        this.recurring = recurring;
        return this;
    }

    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public VesselType dayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        return this;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public VesselType dayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getMonth() {
        return month;
    }

    public VesselType month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Set<Vessel> getVessels() {
        return vessels;
    }

    public VesselType vessels(Set<Vessel> vessels) {
        this.vessels = vessels;
        return this;
    }

    public VesselType addVessel(Vessel vessel) {
        this.vessels.add(vessel);
        vessel.getVesseltypes().add(this);
        return this;
    }

    public VesselType removeVessel(Vessel vessel) {
        this.vessels.remove(vessel);
        vessel.getVesseltypes().remove(this);
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
        VesselType vesselType = (VesselType) o;
        if (vesselType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vesselType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VesselType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", recurring='" + isRecurring() + "'" +
            ", dayOfMonth=" + getDayOfMonth() +
            ", dayOfWeek=" + getDayOfWeek() +
            ", month=" + getMonth() +
            "}";
    }
}
