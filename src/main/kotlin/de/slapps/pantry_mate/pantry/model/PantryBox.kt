package de.slapps.pantry_mate.pantry.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("pantry_box", schema = "pantry_mate")
data class PantryBox(
    @Id val id: Int? = null,
    val name: String,
    val quantity: Int,
    val userId: Int,
    val createdOn: LocalDateTime,
)
