package gr.aueb.cf.flux.mapper;

import gr.aueb.cf.flux.dto.TransactionInsertDTO;
import gr.aueb.cf.flux.dto.TransactionReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionUpdateDTO;
import gr.aueb.cf.flux.model.Category;
import gr.aueb.cf.flux.model.Transaction;
import gr.aueb.cf.flux.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction mapToTransactionEntityInsert(TransactionInsertDTO dto, Wallet wallet, Category category){
        Transaction transaction = new Transaction();

        transaction.setType(dto.type());
        transaction.setAmount(dto.amount());
        transaction.setDescription(dto.description());
        transaction.setDate(dto.date());
        transaction.setWallet(wallet);
        transaction.setCategory(category);

        return transaction;
    }

    public Transaction mapToTransactionEntityUpdate(TransactionUpdateDTO dto, Transaction existingTransaction, Wallet wallet, Category category){
        existingTransaction.setType(dto.type());
        existingTransaction.setAmount(dto.amount());
        existingTransaction.setDate(dto.date());
        existingTransaction.setDescription(dto.description());
        existingTransaction.setWallet(wallet);
        existingTransaction.setCategory(category);

        return existingTransaction;
    }

    public TransactionReadOnlyDTO mapToTransactionReadOnlyDTO(Transaction transaction){
        return new TransactionReadOnlyDTO(
                transaction.getUuid(),
                transaction.getWallet().getUuid(),
                transaction.getCategory().getUuid(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

}
