package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.TransactionInsertDTO;
import gr.aueb.cf.flux.dto.TransactionReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionUpdateDTO;
import gr.aueb.cf.flux.model.Transaction;
import gr.aueb.cf.flux.model.User;

import java.util.List;

public interface ITransactionService {

    //POST /api/transactions
    TransactionReadOnlyDTO createTransaction(TransactionInsertDTO dto, User user)
        throws AppObjectNotFoundException;

    // GET /api/transactions
    List<TransactionReadOnlyDTO> getAllTransactionsByUser(Long userId);

    // GET /api/transactions/{uuid}
    TransactionReadOnlyDTO getTransactionByUuidAndUser(String uuid, Long userId)
            throws AppObjectNotFoundException;

    // PUT /api/transactions/{uuid}
    TransactionReadOnlyDTO updateTransaction (String uuid, Long userId, TransactionUpdateDTO dto)
        throws AppObjectNotFoundException;
}
