package com.sb.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * Created by Kingsley Kumar on 20/03/2018 at 23:59.
 */
@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean updateFields(String userId, Map<String, Object> fieldValueMap) {

        Query query = new Query();
//        final List<Criteria> criteria = new ArrayList<>();
//        criteria.add(Criteria.where("userid").is(userId));
//        query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

        query.addCriteria(Criteria.where("userid")
                                  .is(userId));

        Update update = new Update();

        for (String field : fieldValueMap.keySet()) {

            Object value = fieldValueMap.get(field);

            update = update.set(field, value);
        }

        mongoTemplate.updateFirst(query, update, "users");

        return true;
    }
}
