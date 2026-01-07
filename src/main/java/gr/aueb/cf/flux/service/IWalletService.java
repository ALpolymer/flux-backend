package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;

public interface IWalletService {
    WalletReadOnlyDTO createWallet(WalletInsertDTO dto, String userUuid)
            throws AppObjectNotFoundException, AppObjectAlreadyExists;
}
