package de.slapps.pantry_mate.inventory

import de.slapps.pantry_mate.inventory.model.Inventory
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository : CoroutineCrudRepository<Inventory, Int>