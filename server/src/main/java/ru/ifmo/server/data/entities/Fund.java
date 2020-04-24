package ru.ifmo.server.data.entities;

import javax.persistence.*;

@Entity()
@Table(name = "funds", schema = "crowdfunding")
public class Fund {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "owner_id")
    private int ownerId;

    private int raised;

    @Column(name = "raise_limit")
    private Integer limit;

    private String name;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getRaised() {
        return raised;
    }

    public void setRaised(int raised) {
        this.raised = raised;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addAmount(int amount) {
        this.raised += amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
