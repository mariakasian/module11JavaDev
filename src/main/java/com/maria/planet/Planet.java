package com.maria.planet;

import com.maria.ticket.Ticket;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "planet")
@Entity
public class Planet {

    @Id
    private String id;

    @Column(name = "pname")
    private String name;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private Set<Ticket> ticketsFrom = new HashSet<>();

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    private Set<Ticket> ticketsTo = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(id, planet.id) && Objects.equals(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}