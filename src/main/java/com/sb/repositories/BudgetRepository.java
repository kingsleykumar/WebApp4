package com.sb.repositories;

import com.sb.domain.Budget;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 25/03/2018 at 22:27.
 */
public interface BudgetRepository extends MongoRepository<Budget, ObjectId>, CommonRepository<Budget, ObjectId> {

    Optional<Budget> findByUserIdAndName(String userId, String name);

    void deleteByUserIdAndName(String userId, String name);

    Optional<List<Budget>> findAllByUserId(String userId);
}
