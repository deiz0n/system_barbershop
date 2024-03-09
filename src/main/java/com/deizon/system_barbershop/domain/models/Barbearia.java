package com.deizon.system_barbershop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_barbearia")
public class Barbearia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID id;
    private String nome;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @OneToMany(mappedBy = "barbearia")
    private List<Horario> horarios;

}
