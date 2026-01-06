package gr.aueb.cf.flux.mapper;

import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;
import gr.aueb.cf.flux.dto.WalletUpdateDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public Wallet mapToWalletEntityInsert(WalletInsertDTO dto, User user){
        Wallet wallet = new Wallet();
        wallet.setName(dto.name());
        wallet.setDescription(dto.description());
        wallet.setBalance(dto.balance());
        wallet.setUser(user);

        return wallet;
    }

    public Wallet mapToWalletEntityUpdate(WalletUpdateDTO dto, Wallet existingWallet){
        existingWallet.setName(dto.name());
        existingWallet.setDescription(dto.description());
        existingWallet.setBalance(dto.balance());

        return existingWallet;
    }

    public WalletReadOnlyDTO mapToWalletReadOnlyDTO(Wallet wallet){
        return new WalletReadOnlyDTO(
                wallet.getUuid(),
                wallet.getUser().getUuid(),
                wallet.getName(),
                wallet.getDescription(),
                wallet.getBalance(),
                wallet.getCreatedAt(),
                wallet.getUpdatedAt()
        );
    }
}


