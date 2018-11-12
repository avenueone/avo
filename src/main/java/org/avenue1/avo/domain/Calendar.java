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

/**
 * A Calendar.
 */
@Document(collection = "calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @DBRef
    @Field("calendarattribute")
    private Set<CalendarAttribute> calendarattributes = new HashSet<>();
    @DBRef
    @Field("campaign")
    private Set<Campaign> campaigns = new HashSet<>();
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

    public Calendar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Calendar description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CalendarAttribute> getCalendarattributes() {
        return calendarattributes;
    }

    public Calendar calendarattributes(Set<CalendarAttribute> calendarAttributes) {
        this.calendarattributes = calendarAttributes;
        return this;
    }

    public Calendar addCalendarattribute(CalendarAttribute calendarAttribute) {
        this.calendarattributes.add(calendarAttribute);
        calendarAttribute.setCalender(this);
        return this;
    }

    public Calendar removeCalendarattribute(CalendarAttribute calendarAttribute) {
        this.calendarattributes.remove(calendarAttribute);
        calendarAttribute.setCalender(null);
        return this;
    }

    public void setCalendarattributes(Set<CalendarAttribute> calendarAttributes) {
        this.calendarattributes = calendarAttributes;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public Calendar campaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
        return this;
    }

    public Calendar addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
        campaign.setCalendar(this);
        return this;
    }

    public Calendar removeCampaign(Campaign campaign) {
        this.campaigns.remove(campaign);
        campaign.setCalendar(null);
        return this;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
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
        Calendar calendar = (Calendar) o;
        if (calendar.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calendar.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
