package com.example.test1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "privilege")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Privilege implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "id_privilege", nullable = false)
    private Long idPrivilege;

    @Enumerated(EnumType.STRING)
    private ERole name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "privilege")
    private List<Utilisateur> utilis;


}

