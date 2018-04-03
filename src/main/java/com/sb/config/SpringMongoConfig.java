package com.sb.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Created by Kingsley Kumar on 22/03/2018 at 17:20.
 */
@Configuration
public class SpringMongoConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoDbFactory mongoDbFactory() {

//        MongoCredential credential = MongoCredential
//                .createCredential(Constants.MONGO_UN, Constants.MONGO_DB_NAME, Constants.MONGO_PW);

//        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(host, port), Arrays.asList(credential)), database);
        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(host, port)), database);
    }

    @Bean
    public MongoTemplate mongoTemplate() {

        MongoDbFactory mongoDbFactory = mongoDbFactory();

        MappingMongoConverter converter =
                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null)); //To remove the _class field which is added on every document saving.

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;

    }

}