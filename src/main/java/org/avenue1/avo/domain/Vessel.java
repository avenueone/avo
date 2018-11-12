package org.avenue1.avo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vessel.
 */
@Document(collection = "vessel")
public class Vessel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 4)
    @Field("name")
    private String name;

    @NotNull
    @Field("start_date")
    private LocalDate startDate;

    @NotNull
    @Field("end_date")
    private LocalDate endDate;

    @Field("description")
    private String description;

    @DBRef
    @Field("vesselattribute")
    private Set<VesselAttribute> vesselattributes = new HashSet<>();
    @DBRef
    @Field("container")
    @JsonIgnoreProperties("vessels")
    private Container container;

    @DBRef
    @Field("vesseltypes")
    private Set<VesselType> vesseltypes = new HashSet<>();

    @DBRef
    @Field("campaign")
    @JsonIgnoreProperties("vessels")
    private Campaign campaign;

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

    public Vessel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Vessel startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Vessel endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public Vessel description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<VesselAttribute> getVesselattributes() {
        return vesselattributes;
    }

    public Vessel vesselattributes(Set<VesselAttribute> vesselAttributes) {
        this.vesselattributes = vesselAttributes;
        return this;
    }

    public Vessel addVesselattribute(VesselAttribute vesselAttribute) {
        this.vesselattributes.add(vesselAttribute);
        vesselAttribute.setVessel(this);
        return this;
    }

    public Vessel removeVesselattribute(VesselAttribute vesselAttribute) {
        this.vesselattributes.remove(vesselAttribute);
        vesselAttribute.setVessel(null);
        return this;
    }

    public void setVesselattributes(Set<VesselAttribute> vesselAttributes) {
        this.vesselattributes = vesselAttributes;
    }

    public Container getContainer() {
        return container;
    }

    public Vessel container(Container container) {
        this.container = container;
        return this;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Set<VesselType> getVesseltypes() {
        return vesseltypes;
    }

    public Vessel vesseltypes(Set<VesselType> vesselTypes) {
        this.vesseltypes = vesselTypes;
        return this;
    }

    public Vessel addVesseltype(VesselType vesselType) {
        this.vesseltypes.add(vesselType);
        vesselType.getVessels().add(this);
        return this;
    }

    public Vessel removeVesseltype(VesselType vesselType) {
        this.vesseltypes.remove(vesselType);
        vesselType.getVessels().remove(this);
        return this;
    }

    public void setVesseltypes(Set<VesselType> vesselTypes) {
        this.vesseltypes = vesselTypes;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public Vessel campaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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
        Vessel vessel = (Vessel) o;
        if (vessel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vessel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vessel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
