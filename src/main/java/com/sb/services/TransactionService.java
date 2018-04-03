package com.sb.services;

import com.sb.services.containers.ResultMessage;
import com.sb.commands.TransactionCommand;
import com.sb.commands.TransactionCommandWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 28/03/2018 at 16:30.
 */
public interface TransactionService {

    Optional<ResultMessage> addTransactions(TransactionCommandWrapper transactionCommandWrapper, HttpSession session);

    Optional<ResultMessage> updateTransactions(TransactionCommandWrapper transactionCommandWrapper, HttpSession session);

    List<TransactionCommand> retrieveTransactions(HttpServletRequest request, HttpSession session);
}
