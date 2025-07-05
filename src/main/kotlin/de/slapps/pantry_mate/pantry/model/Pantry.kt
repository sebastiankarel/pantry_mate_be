package de.slapps.pantry_mate.pantry.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("pantry", schema = "pantry_mate")
data class Pantry(
    @Id val id: Int? = null,
    val userId: Int,
)