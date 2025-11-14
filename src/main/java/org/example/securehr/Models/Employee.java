package org.example.securehr.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "names")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

//    @Column(name = "hireDate")
    private Date hireDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
