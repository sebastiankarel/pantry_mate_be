package de.slapps.pantry_mate.pantry.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("item", schema = "pantry_mate")
data class Item(
    @Id val id: Int? = null,
    val name: String,
    val quantity: Int,
    val createdOn: LocalDate,
)
