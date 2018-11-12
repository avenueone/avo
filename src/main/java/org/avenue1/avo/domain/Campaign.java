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
 * A Campaign.
 */
@Document(collection = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @NotNull
    @Field("start_date")
    private LocalDate startDate;

    @NotNull
    @Field("end_date")
    private LocalDate endDate;

    @DBRef
    @Field("vessel")
    private Set<Vessel> vessels = new HashSet<>();
    @DBRef
    @Field("campaignAttribute")
    @JsonIgnoreProperties("campaigns")
    private CampaignAttribute campaignAttribute;

    @DBRef
    @Field("calendar")
    @JsonIgnoreProperties("campaigns")
    private Calendar calendar;

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

    public Campaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Campaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Campaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Vessel> getVessels() {
        return vessels;
    }

    public Campaign vessels(Set<Vessel> vessels) {
        this.vessels = vessels;
        return this;
    }

    public Campaign addVessel(Vessel vessel) {
        this.vessels.add(vessel);
        vessel.setCampaign(this);
        return this;
    }

    public Campaign removeVessel(Vessel vessel) {
        this.vessels.remove(vessel);
        vessel.setCampaign(null);
        return this;
    }

    public void setVessels(Set<Vessel> vessels) {
        this.vessels = vessels;
    }

    public CampaignAttribute getCampaignAttribute() {
        return campaignAttribute;
    }

    public Campaign campaignAttribute(CampaignAttribute campaignAttribute) {
        this.campaignAttribute = campaignAttribute;
        return this;
    }

    public void setCampaignAttribute(CampaignAttribute campaignAttribute) {
        this.campaignAttribute = campaignAttribute;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Campaign calendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
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
        Campaign campaign = (Campaign) o;
        if (campaign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
