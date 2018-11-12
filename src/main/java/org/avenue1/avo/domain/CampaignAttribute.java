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
 * A CampaignAttribute.
 */
@Document(collection = "campaign_attribute")
public class CampaignAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("type")
    private String type;

    @Field("value")
    private String value;

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

    public CampaignAttribute name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public CampaignAttribute type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public CampaignAttribute value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public CampaignAttribute campaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
        return this;
    }

    public CampaignAttribute addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
        campaign.setCampaignAttribute(this);
        return this;
    }

    public CampaignAttribute removeCampaign(Campaign campaign) {
        this.campaigns.remove(campaign);
        campaign.setCampaignAttribute(null);
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
        CampaignAttribute campaignAttribute = (CampaignAttribute) o;
        if (campaignAttribute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaignAttribute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CampaignAttribute{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
