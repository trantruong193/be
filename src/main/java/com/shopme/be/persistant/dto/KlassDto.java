package com.shopme.be.persistant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.shopme.be.persistant.model.Klass} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KlassDto implements Serializable {
    private Long id;
    private String name;
}