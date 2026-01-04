package gr.aueb.cf.flux.repository;

import gr.aueb.cf.flux.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findByUuid(String uuid);
    List<Transaction> findByWalletId(Long walletId);
    List<Transaction> findByCategoryId(Long categoryId);

}
