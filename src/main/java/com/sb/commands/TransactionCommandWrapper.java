package com.sb.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Kingsley Kumar on 28/03/2018 at 16:11.
 */
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@ToString
public class TransactionCommandWrapper {
    private List<TransactionCommand> transactionCommands;
}
