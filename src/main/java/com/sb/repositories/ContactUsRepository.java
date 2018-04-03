package com.sb.repositories;

import com.sb.domain.ContactUs;
import com.sb.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kingsley Kumar on 23/03/2018 at 22:27.
 */
@Repository
public interface ContactUsRepository extends MongoRepository<ContactUs, ObjectId> {
}
