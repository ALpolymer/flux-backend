package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;
import gr.aueb.cf.flux.dto.WalletUpdateDTO;
import gr.aueb.cf.flux.mapper.WalletMapper;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.model.Wallet;
import gr.aueb.cf.flux.repository.WalletRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService implements IWalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WalletReadOnlyDTO> getAllWalletsByUser(Long userId) {
        List<Wallet> allWallets = walletRepository.findByUserId(userId);

        return allWallets.stream()
                .map(walletMapper::mapToWalletReadOnlyDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WalletReadOnlyDTO getWalletByUuid(String uuid, Long userId) throws AppObjectNotFoundException {

        return walletRepository.findByUuidAndUserId(uuid, userId)
                .map(walletMapper::mapToWalletReadOnlyDTO)
                .orElseThrow(()-> new AppObjectNotFoundException("Wallet", "Wallet with uuid " + uuid +" not found"));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletReadOnlyDTO createWallet(WalletInsertDTO dto, User user) {
        Wallet walletToCreate = walletMapper.mapToWalletEntityInsert(dto, user);

        Wallet createdWallet = walletRepository.save(walletToCreate);

        log.info("Wallet with id={} created", createdWallet.getId());

        return walletMapper.mapToWalletReadOnlyDTO(createdWallet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WalletReadOnlyDTO updateWallet(String uuid, Long userId ,WalletUpdateDTO dto) throws AppObjectNotFoundException {
        Optional<Wallet> existingWallet = walletRepository.findByUuidAndUserId(uuid, userId);

        if(existingWallet.isEmpty()) throw new AppObjectNotFoundException("Wallet", "Wallet with uuid " + uuid +" not found");

        Wallet updatedWallet = walletMapper.mapToWalletEntityUpdate(dto, existingWallet.get());

        Wallet savedUpdatedWallet =  walletRepository.save(updatedWallet);

        log.info("Wallet with uuid={} updated", savedUpdatedWallet.getUuid());

        return walletMapper.mapToWalletReadOnlyDTO(savedUpdatedWallet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWallet(String uuid, Long userId) throws AppObjectNotFoundException {
        Optional<Wallet> walletToDelete = walletRepository.findByUuidAndUserId(uuid, userId);

        if(walletToDelete.isEmpty()) throw new AppObjectNotFoundException("Wallet", "Wallet with uuid " + uuid +" not found");

        walletRepository.delete(walletToDelete.get());
    }
}
