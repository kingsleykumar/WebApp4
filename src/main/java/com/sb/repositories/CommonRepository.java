package com.sb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Kingsley Kumar on 27/03/2018 at 16:46.
 */
@NoRepositoryBean
public interface CommonRepository<T, ID> extends MongoRepository<T, ID> {

    void deleteByUserId(String userId);
}
