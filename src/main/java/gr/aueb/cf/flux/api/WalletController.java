package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.dto.WalletReadOnlyDTO;
import gr.aueb.cf.flux.dto.WalletUpdateDTO;
import gr.aueb.cf.flux.model.User;

import gr.aueb.cf.flux.service.IWalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
@Tag(name = "Wallets", description = "Wallet management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class WalletController {

    private final IWalletService walletService;

    @Operation(summary = "Get all wallets for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallets retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<WalletReadOnlyDTO> > getAllWallets(
            @AuthenticationPrincipal User currentUser
    ) {
        Long userId = currentUser.getId();

        List<WalletReadOnlyDTO> wallets = walletService.getAllWalletsByUser(userId);


        return ResponseEntity.ok(wallets);
    }

    @Operation(summary = "Get wallet by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
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

    @Operation(summary = "Create a new wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<WalletReadOnlyDTO> createWallet(
            @RequestBody @Valid WalletInsertDTO dto,
            @AuthenticationPrincipal User currentUser
    )
    {
        WalletReadOnlyDTO createdWallet = walletService.createWallet(dto, currentUser);

        return ResponseEntity.status(201).body(createdWallet);

    }

    @Operation(summary = "Update an existing wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
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

    @Operation(summary = "Delete a wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Wallet deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
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
