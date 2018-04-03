package com.sb.commands;

import com.sb.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Kingsley Kumar on 30/03/2018 at 01:04.
 */
@Slf4j
@Getter
@AllArgsConstructor
public class TransactionWrapper {

    private Transaction transaction;
    private boolean delete;
}

