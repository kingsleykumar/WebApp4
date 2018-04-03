package com.sb.repositories;

import com.sb.domain.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 28/03/2018 at 16:31.
 */
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, ObjectId>,
                                               CommonRepository<Transaction, ObjectId>, CustomTransactionRepository {

    Optional<Transaction> findByUserIdAndDate(String userId, Date date);
}
