package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.wireTransfer;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDao {
    public Transfer createTransfer(Transfer transfer);

    public Transfer get(int transferId);

    public List<Transfer> getAll(Principal principal);

    public void subtractAmount(Transfer transfer, BigDecimal balance);

    public void addAmount(Transfer transfer, BigDecimal balance);

    //public int sendRequestToUser(int accountFrom, wireTransfer transfer);

    // Step 5
    //List<Transfer> getAllTransfersSent(int accountFrom);

    //List<Transfer> getAllTransfersReceived(int accountTo);

    //Step 6
    // Transfer getTransferDetails(int Transfer_id);

}
