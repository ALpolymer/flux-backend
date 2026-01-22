package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.TransactionInsertDTO;
import gr.aueb.cf.flux.dto.TransactionReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionUpdateDTO;
import gr.aueb.cf.flux.mapper.TransactionMapper;
import gr.aueb.cf.flux.model.Category;
import gr.aueb.cf.flux.model.Transaction;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.model.Wallet;
import gr.aueb.cf.flux.repository.CategoryRepository;
import gr.aueb.cf.flux.repository.TransactionRepository;
import gr.aueb.cf.flux.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WalletRepository walletRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionReadOnlyDTO createTransaction(TransactionInsertDTO dto, User user)
        throws AppObjectNotFoundException
    {

        Long userId = user.getId();
        String walletUuid = dto.walletId();
        String categoryUuid = dto.categoryId();

        Optional<Wallet> wallet = walletRepository.findByUuidAndUserId(walletUuid, userId);

        Optional<Category> category = categoryRepository.findByUuidAndUserId(categoryUuid, userId);

        if(wallet.isEmpty()) throw new AppObjectNotFoundException("Wallet", "Wallet with uuid " + walletUuid +" not found");

        if(category.isEmpty()) throw new AppObjectNotFoundException("Category", "Category with uuid " + categoryUuid +" not found");

        Transaction transactionToCreate = transactionMapper.mapToTransactionEntityInsert(dto, wallet.get(), category.get());

        Transaction createdTransaction = transactionRepository.save(transactionToCreate);

        log.info("Transaction with uuid={} created", createdTransaction.getUuid());

        return transactionMapper.mapToTransactionReadOnlyDTO(createdTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionReadOnlyDTO> getAllTransactionsByUser(Long userId) {

        List<Transaction> allTransactions = transactionRepository.findByWalletUserId(userId);

      return allTransactions.stream()
                .map(transactionMapper :: mapToTransactionReadOnlyDTO)
              .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionReadOnlyDTO getTransactionByUuidAndUser(String uuid, Long userId) throws AppObjectNotFoundException {
        Optional<Transaction> transaction = transactionRepository.findByUuidAndWalletUserId(uuid, userId);

        if(transaction.isEmpty()) throw new AppObjectNotFoundException("Transaction", "Transaction with uuid " + uuid + " not found.");

        return transactionMapper.mapToTransactionReadOnlyDTO(transaction.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionReadOnlyDTO updateTransaction(String uuid, Long userId, TransactionUpdateDTO dto)
            throws AppObjectNotFoundException {
        Optional<Transaction> savedTransaction = transactionRepository.findByUuidAndWalletUserId(uuid, userId);

        if(savedTransaction.isEmpty()) throw new AppObjectNotFoundException("Transaction", "Transaction with uuid " + uuid +" not found");

        Optional<Wallet> wallet = walletRepository.findByUuidAndUserId(dto.walletId(), userId);

        Optional<Category> category = categoryRepository.findByUuidAndUserId(dto.categoryId(), userId);

        if(wallet.isEmpty()) throw new AppObjectNotFoundException("Wallet", "Wallet with uuid " + dto.walletId() +" not found");

        if(category.isEmpty()) throw new AppObjectNotFoundException("Category", "Category with uuid " + dto.categoryId() +" not found");


        Transaction updatedCategory = transactionMapper.mapToTransactionEntityUpdate(dto, savedTransaction.get(), wallet.get(), category.get());

        Transaction savedUpdatedTransaction = transactionRepository.save(updatedCategory);

        log.info("Category with uuid={} updated", savedUpdatedTransaction.getUuid());

        return transactionMapper.mapToTransactionReadOnlyDTO(savedUpdatedTransaction);
    }


}
