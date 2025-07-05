package de.slapps.pantry_mate.pantry.model.dto

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("item", schema = "pantry_mate")
data class ItemDTO(
    val name: String,
    val quantity: Int,
    val createdAt: LocalDate,
)
