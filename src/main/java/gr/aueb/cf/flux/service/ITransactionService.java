package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.TransactionInsertDTO;
import gr.aueb.cf.flux.dto.TransactionReadOnlyDTO;
import gr.aueb.cf.flux.model.Transaction;
import gr.aueb.cf.flux.model.User;

import java.util.List;

public interface ITransactionService {

    //POST /api/transactions
    TransactionReadOnlyDTO createTransaction(TransactionInsertDTO dto, User user)
        throws AppObjectNotFoundException;

    List<TransactionReadOnlyDTO> getAllTransactionsByUser(Long userId);

    TransactionReadOnlyDTO getTransactionByUuidAndUser(String uuid, Long userId)
            throws AppObjectNotFoundException;

}
