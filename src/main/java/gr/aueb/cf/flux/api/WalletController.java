package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;
import gr.aueb.cf.flux.dto.WalletUpdateDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.model.Wallet;
import gr.aueb.cf.flux.service.IWalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final IWalletService walletService;



    @PostMapping("/test")
    public ResponseEntity<String> testAuth(@AuthenticationPrincipal User currentUser) {

        if (currentUser == null) {
            return ResponseEntity.ok("User is NULL");
        }
        return ResponseEntity.ok("User ID: " + currentUser.getId() + ", Name: " + currentUser.getUsername());
    }

    // ═══════════════════════════════════════════
    // GET /api/wallets
    // ═══════════════════════════════════════════
    @GetMapping
    public ResponseEntity<List<WalletReadOnlyDTO> > getAllWallets(
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();

        List<WalletReadOnlyDTO> wallets = walletService.getAllWalletsByUser(userId);


        return ResponseEntity.ok(wallets);
    }

    // ═══════════════════════════════════════════
    // GET /api/wallets/{uuid}
    // ═══════════════════════════════════════════
    @GetMapping("/{uuid}")
    public ResponseEntity<WalletReadOnlyDTO> getWallet(
            @PathVariable String uuid,
            @AuthenticationPrincipal User currentUser
    ) throws AppObjectNotFoundException
    {
        Long userId = currentUser.getId();

        WalletReadOnlyDTO wallet = walletService.getWalletByUuid(uuid, userId);

        return ResponseEntity.ok(wallet);
    }

    // ═══════════════════════════════════════════
    // POST /api/wallets
    // ═══════════════════════════════════════════
    @PostMapping
    public ResponseEntity<WalletReadOnlyDTO> createWallet(
            @RequestBody @Valid WalletInsertDTO dto,
            @AuthenticationPrincipal User currentUser
    )
    {
        WalletReadOnlyDTO createdWallet = walletService.createWallet(dto, currentUser);

        return ResponseEntity.status(201).body(createdWallet);

    }

    // ═══════════════════════════════════════════
    // PUT /api/wallets/{uuid}
    // ═══════════════════════════════════════════
    @PutMapping("/{uuid}")
    public ResponseEntity<WalletReadOnlyDTO> updateWallet(
            @PathVariable String uuid,
            @RequestBody @Valid WalletUpdateDTO dto,
            @AuthenticationPrincipal User currentUser
            ) throws AppObjectNotFoundException {

        Long userId = currentUser.getId();
        WalletReadOnlyDTO updatedWallet = walletService.updateWallet(uuid, userId, dto);

        return ResponseEntity.ok(updatedWallet);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteWallet(
            @PathVariable String uuid,
            @AuthenticationPrincipal User currentUser
    ) throws AppObjectNotFoundException{
        Long userId = currentUser.getId();

        walletService.deleteWallet(uuid, userId);

        return ResponseEntity.noContent().build();
    }

}
