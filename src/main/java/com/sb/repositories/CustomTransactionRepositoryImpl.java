package com.sb.repositories;

import com.sb.domain.Transaction;
import com.sb.domain.TransactionContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by Kingsley Kumar on 29/03/2018 at 14:43.
 */
@Slf4j
@Repository
public class CustomTransactionRepositoryImpl implements CustomTransactionRepository {

    private static final String USER_ID = "userId";
    private static final String DATE = "date";
    //    private static final String TRANSACTIONS_ARRAY = "transactions.category";
    private static final String TRANSACTIONS_ARRAY = "transactions";
    private static final String TRANSACTIONS_CATEGORY = "transactions.category";
    private static final String CATEGORY = "category";
    private static final String COLLECTION_NAME = "transaction";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTransactionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Transaction> retrieveTransactionsBetweenDates(String userId,
                                                              String category,
                                                              Date fromDate,
                                                              Date toDate) {

        if (!StringUtils.isBlank(category)) {

            return retrieveTransactionsByAggregation(userId, category, fromDate, toDate);
        }

        Query query = new Query();

        query.addCriteria(Criteria.where(USER_ID).is(userId));

        query.addCriteria(Criteria.where(DATE).gte(fromDate).lte(toDate));

        query.with(new Sort(Sort.Direction.ASC, DATE));

        List<Transaction> transactions = mongoTemplate.find(query, Transaction.class, COLLECTION_NAME);

        return transactions;
    }

    public List<Transaction> retrieveTransactionsByAggregation(String userId,
                                                               String category,
                                                               Date fromDate,
                                                               Date toDate) {

        Criteria criteria = Criteria.where(USER_ID)
                                    .is(userId)
                                    .andOperator(Criteria.where(DATE).gte(fromDate).lte(toDate));

//        Criteria criteriaTx = Criteria.where(CATEGORY).is(category);

        MatchOperation matchBetweenDates = match(criteria);
        SortOperation sortByDate = sort(new Sort(Sort.Direction.ASC, DATE));

        UnwindOperation unwindOperation = unwind(TRANSACTIONS_ARRAY);
        MatchOperation matchCategory = match(Criteria.where(TRANSACTIONS_CATEGORY).is(category));

        GroupOperation group = Aggregation.group("_id", "userId", "date").push("transactions").as("transactions");

        //TODO: Try with ProjectionOperation

//        ProjectionOperation projectionOperation = project().andExpression("setEquals(a, new int[]{5, 8, 13})").previousOperation();

        //        ProjectionOperation projectionOperation = project().and(new AggregationExpression() {
//
//            @Override
//            public Document toDocument(AggregationOperationContext aggregationOperationContext) {
//
//                DBObject filterExpression = new BasicDBObject();
//
//                filterExpression.put("input", "$transactions");
//                filterExpression.put("as", "transaction");
//                filterExpression.put("cond", new BasicDBObject("$eq", new Document("$transaction.category", category)));
//
//                return new Document("$filter", filterExpression);
//            }
//        }).as("transaction");


        Aggregation aggregation = Aggregation.newAggregation(
                matchBetweenDates, unwindOperation, matchCategory, group, sortByDate);

        AggregationResults<Transaction> result = mongoTemplate.aggregate(
                aggregation, COLLECTION_NAME, Transaction.class);

        return result.getMappedResults();
    }

    @Override
    public boolean deleteTransactions(String userId, Date date, List<Integer> txIds) {

        Query query = new Query();

        query.addCriteria(Criteria.where(USER_ID).is(userId));

        query.addCriteria(Criteria.where(DATE).is(date));

        Update update = new Update();
        update.pull(TRANSACTIONS_ARRAY, Query.query(Criteria.where("id").in(txIds)));

        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

        return true;
    }

    @Override
    public boolean updateTransactions(String userId, Date date, List<TransactionContent> transactionContents) {

        for (TransactionContent transactionContent : transactionContents) { //TODO: Check if update in single command is possible.

            Query query = new Query();

            query.addCriteria(Criteria.where(USER_ID).is(userId));

            query.addCriteria(Criteria.where(DATE).is(date));

            query.addCriteria(Criteria.where("transactions.id").is(transactionContent.getId()));

            Update update = new Update();
            update.set("transactions.$", transactionContent);

            mongoTemplate.updateFirst(query, update, COLLECTION_NAME);
        }

        return true;
    }

    @Override
    public boolean insertTransactions(String userId, Date date, List<TransactionContent> transactionContents) {

        Query query = new Query();

        query.addCriteria(Criteria.where(USER_ID).is(userId));

        query.addCriteria(Criteria.where(DATE).is(date));

        Update update = new Update();

        update.push("transactions").each(transactionContents);

        mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

        return true;
    }

    @Override
    public boolean anyTransactionExistsForCategory(String userId, String categoryName) {

        Query query = new Query();

        query.addCriteria(Criteria.where(USER_ID).is(userId));

        query.addCriteria(Criteria.where("transactions.category").is(categoryName));

        Transaction transaction = mongoTemplate.findOne(query, Transaction.class, COLLECTION_NAME);

        return (transaction != null);
    }

    @Override
    public boolean anyTransactionExistsForBudget(String userId, String budgetName) {

        Query query = new Query();

        query.addCriteria(Criteria.where(USER_ID).is(userId));

        query.addCriteria(Criteria.where("transactions.budget").is(budgetName));

        Transaction transaction = mongoTemplate.findOne(query, Transaction.class, COLLECTION_NAME);

        return (transaction != null);
    }
}
