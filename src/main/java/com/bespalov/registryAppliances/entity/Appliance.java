package com.bespalov.registryAppliances.entity;

import com.bespalov.registryAppliances.dto.ApplianceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "appliances")
public class Appliance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appliance_id")
    private UUID id;
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Manufacturer country cannot be empty")
    @Column(name = "manufacturer_country")
    private String manufacturerCountry;
    @NotEmpty(message = "Firm cannot be empty")
    @Column(name = "firm")
    private String firm;
    @NotNull(message = "Ordering online cannot be empty")
    @Column(name = "order_online")
    private Boolean isOrderOnline;
    @NotNull(message = "Possibility of payment by installments cannot be empty")
    @Column(name = "installment")
    private Boolean isInstallment;
    @NotNull(message = "Appliance type cannot be empty")
    @Column(name = "appliance_type")
    @Enumerated(EnumType.STRING)
    private ApplianceType applianceType;
    @Column(name = "models_available")
    private Integer modelAvailable;

}
