package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.wireTransfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao  implements AccountDao{

    JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    @Override
    public BigDecimal accountBalance(int user_id){
        BigDecimal balance = new BigDecimal("0");
        String sql  = "SELECT balance FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,user_id);

        if (result.next()){
           balance = result.getBigDecimal("balance");

        }

        return balance;
    }

    public int updateTheSendersBalance(int userId, wireTransfer transfer){return 0;}
    public int updateTheReceiversBalance(int userId, wireTransfer transfer){return 0;}



}
