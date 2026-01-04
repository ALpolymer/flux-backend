package gr.aueb.cf.flux.repository;

import gr.aueb.cf.flux.model.Category;
import gr.aueb.cf.flux.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>
, JpaSpecificationExecutor<Wallet> {
    Optional<Wallet> findByUuid(String uuid);
    List<Wallet> findByUserId(Long walletId);
}
