package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    JdbcTemplate jdbcTemplate;
    public  JdbcTransferDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public Transfer get(int transferId){
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfer createTransfer(Transfer transfer){

        // get account id for send and account id for receive
        // pass to transfer

        //Get Account information for sender
        String  sql = "SELECT * FROM accounts WHERE user_id = ?;";
        SqlRowSet result= jdbcTemplate.queryForRowSet(sql,transfer.getAccountFrom());
        Account account = null;

        if (result.next()){ account = mapRowToAccount(result);}
        int account_from = account.getId();

        sql = "SELECT * FROM accounts WHERE user_id = ?;";
        result= jdbcTemplate.queryForRowSet(sql,transfer.getAccountTo());

        account = null;
        if (result.next()){ account = mapRowToAccount(result);}
        int account_to = account.getId();

        transfer.setAccountFrom(account_from);
        transfer.setAccountTo(account_to);


        if (account.getBalance().compareTo(transfer.getAmount())>0){

            sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                    "VALUES (?,?,?,?,?) RETURNING transfer_id";

            Integer newId = jdbcTemplate.queryForObject(sql,Integer.class,
                    transfer.getTransferType(), transfer.getTransferStatus(),transfer.getAccountFrom(),transfer.getAccountTo(),transfer.getAmount());

            subtractAmount(transfer, account.getBalance());
            addAmount(transfer, account.getBalance());

            return get(newId);
        }

            return transfer;

        // if false throw exception

    }

    @Override
    public void subtractAmount(Transfer transfer, BigDecimal balance){

        BigDecimal newBalance = balance.subtract(transfer.getAmount());

        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql,newBalance, transfer.getAccountFrom());

    }

    @Override
    public void addAmount(Transfer transfer, BigDecimal balance){
        BigDecimal newBalance = transfer.getAmount().add(balance);

        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql,newBalance, transfer.getAccountTo());
    }

    @Override // Change passing object to fit sql request ;Can use a union for tables; Change param to be a String
              // Pass principal name to string
    public List<Transfer> getAll(Principal principal){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, account_from, account_to, amount " +
                     "FROM transfers JOIN accounts ON transfers.account_from = " +
                     "accounts.account_id JOIN users ON users.user_id = accounts.user_id WHERE username = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, principal.getName());
        while (results.next()) {
            Transfer theTransfer = mapRowToTransfer(results);
            transfers.add(theTransfer);
        }

        sql = "SELECT transfer_id, account_from, account_to, amount " +
              "FROM transfers JOIN accounts ON transfers.account_to = " +
              "accounts.account_id JOIN users ON users.user_id = accounts.user_id WHERE username = ?";

        results = jdbcTemplate.queryForRowSet(sql, principal.getName());
        while (results.next()) {
            Transfer theTransfer = mapRowToTransfer(results);
            transfers.add(theTransfer);

        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet results){
        Transfer transfer = new Transfer();
        transfer.setId(results.getInt("transfer_id"));
        transfer.setTransferType(results.getInt("transfer_type_id"));
        transfer.setTransferStatus(results.getInt("transfer_status_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));

        return transfer;
    }
    private Account mapRowToAccount(SqlRowSet results){
        Account account = new Account();
        account.setId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));

        return account;
    }
}
