package com.coldstart.Repository

import com.coldstart.Model.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Created by quangio.
 */

@RepositoryRestResource
interface UserRepo : MongoRepository<User, ObjectId> {
    fun findByUsername(username: String): User?
}
