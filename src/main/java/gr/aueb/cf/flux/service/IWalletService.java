package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;
import gr.aueb.cf.flux.dto.WalletUpdateDTO;
import gr.aueb.cf.flux.model.User;

import java.util.List;

public interface IWalletService {

    // GET /api/wallets
    List<WalletReadOnlyDTO> getAllWalletsByUser(Long userId);

    // GET /api/wallets/{uuid}
    WalletReadOnlyDTO getWalletByUuid(String uuid, Long userId)
        throws AppObjectNotFoundException;

    // POST /api/wallets
    WalletReadOnlyDTO createWallet(WalletInsertDTO dto, User user);

    // PUT /api/wallets/{uuid}
    WalletReadOnlyDTO updateWallet(String uuid, Long userId, WalletUpdateDTO dto)
        throws AppObjectNotFoundException;

    // DELETE /api/wallets/{uuid}
    void deleteWallet(String uuid, Long userId)
            throws AppObjectNotFoundException;

}
