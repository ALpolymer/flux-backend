package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.model.Wallet;
import gr.aueb.cf.flux.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final IWalletService walletService;

    @GetMapping
    public ResponseEntity<List<WalletReadOnlyDTO>> getAllWallets(
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();

        List<WalletReadOnlyDTO> wallets = walletService.getAllWalletsByUser(userId);
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<WalletReadOnlyDTO> getWallet(@PathVariable String uuid, @AuthenticationPrincipal User currentUser) throws AppObjectNotFoundException {

        WalletReadOnlyDTO wallet = walletService.getWalletByUuid(uuid, currentUser.getId());

        return ResponseEntity.ok(wallet);
    }


}
