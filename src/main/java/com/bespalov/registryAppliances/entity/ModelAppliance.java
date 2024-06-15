package com.bespalov.registryAppliances.entity;

import com.bespalov.registryAppliances.dto.ApplianceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "models")
public class ModelAppliance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "model_appliance_id")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appliance_id")
    private Appliance appliance;
    @Column(name = "appliance_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplianceType applianceType;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "serial_number",nullable = false,unique = true)
    private String serialNumber;
    @Column(name = "color",nullable = false)
    private String color;
    @Column(name = "size",nullable = false)
    private String size;
    @Column(name = "price",nullable = false)
    private BigDecimal price;
    @Column(name = "availability",nullable = false)
    private Boolean isAvailability;

}
