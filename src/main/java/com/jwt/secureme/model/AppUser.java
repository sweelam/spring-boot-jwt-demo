package com.jwt.secureme.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private Collection<AppRole> roles = new ArrayList<>();
}
