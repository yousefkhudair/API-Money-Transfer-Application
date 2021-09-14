package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.wireTransfer;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal accountBalance(int user_id);
    int updateTheSendersBalance(int userId, wireTransfer transfer);
    int updateTheReceiversBalance(int userId, wireTransfer transfer);

}
