package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TenmoController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao UserDao;

    @Autowired
    private TransferDao transferDao;



    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){

        System.out.println(principal.getName() + " Balance: ");
        return accountDao.accountBalance(UserDao.findIdByUsername(principal.getName()));
    }

      @RequestMapping(path = "/myTransfers", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal){

        System.out.println(principal.getName() + " My Transfers: ");
        return transferDao.getAll(principal);
    }


    @RequestMapping(path = "/myTransfer/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable("id") int transfer_type_id, Principal principal){

        System.out.println(principal.getName() + "Transfer Details: ");
        return transferDao.get(transfer_type_id);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> getUsers(){
        return UserDao.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/createTransfer", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer){
        System.out.println(transfer);
        return transferDao.createTransfer(transfer);
    }



    //myTransfers has to include an account so only transfers for a given user can be viewed
    //myTransfers has to return both request and sent
    // getAll join for accounts so we can get an account id (Where account id = ?)

}
