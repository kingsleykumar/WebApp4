package com.sb.repositories;

import com.sb.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Kingsley Kumar on 20/03/2018 at 14:27.
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>, CustomUserRepository {

    Optional<User> findByUsername(String username);

    Optional<User> findByUserid(String userid);

    Optional<User> deleteByUserid(String userid);
}
