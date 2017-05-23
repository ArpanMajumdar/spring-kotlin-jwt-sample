package com.coldstart.Model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by quangio.
 */

@Document
data class User @PersistenceConstructor constructor(@Indexed(unique = true) val username: String,
                                                    @JsonIgnore val password: String,
                                                    val roles: MutableList<String> = mutableListOf("GUEST"),
                                                    @Id val id: ObjectId? = null)