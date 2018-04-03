package com.sb.repositories;

import com.sb.domain.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 24/03/2018 at 22:29.
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId>, CommonRepository<Category, ObjectId> {

    Optional<Category> findByUserIdAndCategory(String userId, String category);

    void deleteByUserIdAndCategory(String userId, String category);

    Optional<List<Category>> findAllByUserId(String userId);
}
