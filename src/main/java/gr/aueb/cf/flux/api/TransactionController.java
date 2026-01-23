package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionInsertDTO;
import gr.aueb.cf.flux.dto.TransactionReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionUpdateDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.service.ICategoryService;
import gr.aueb.cf.flux.service.ITransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    // ═══════════════════════════════════════════
    // POST /api/transactions
    // ═══════════════════════════════════════════
    @PostMapping
    public ResponseEntity<TransactionReadOnlyDTO> createTransaction(
            @RequestBody @Valid TransactionInsertDTO dto,
            @AuthenticationPrincipal User currentUser
            ) throws AppObjectNotFoundException
    {
        TransactionReadOnlyDTO createdTransaction = transactionService.createTransaction(dto, currentUser);

        return ResponseEntity.status(201).body(createdTransaction);
    }

    // ═══════════════════════════════════════════
    // GET /api/transactions
    // ═══════════════════════════════════════════
    @GetMapping
    public ResponseEntity<List<TransactionReadOnlyDTO>> getAllTransactionsByUserId(
            @AuthenticationPrincipal User currentUser
    ){
        Long userId = currentUser.getId();

        List<TransactionReadOnlyDTO> transactions = transactionService.getAllTransactionsByUser(userId);

        return ResponseEntity.ok(transactions);
    }


    // ═══════════════════════════════════════════
    // GET /api/transactions/{uuid}
    // ═══════════════════════════════════════════
    @GetMapping("/{uuid}")
    public ResponseEntity<TransactionReadOnlyDTO> getTransactionByUuidAndUser(
            @PathVariable String uuid,
            @AuthenticationPrincipal User currentUser
    ) throws AppObjectNotFoundException
    {
        Long userId = currentUser.getId();

        TransactionReadOnlyDTO transaction = transactionService.getTransactionByUuidAndUser(uuid, userId);

        return ResponseEntity.ok(transaction);
    };

    // ═══════════════════════════════════════════
    // PUT /api/transactions/{uuid}
    // ═══════════════════════════════════════════
    @PutMapping("/{uuid}")
    public ResponseEntity<TransactionReadOnlyDTO> updateTransaction(
            @PathVariable String uuid,
            @RequestBody @Valid TransactionUpdateDTO dto,
            @AuthenticationPrincipal User currentUser
            ) throws AppObjectNotFoundException
    {
        Long userId = currentUser.getId();

        TransactionReadOnlyDTO updatedTransaction = transactionService.updateTransaction(uuid, userId, dto);

        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable String uuid,
            @AuthenticationPrincipal User currentUser
    ) throws AppObjectNotFoundException{
        Long userId = currentUser.getId();

        transactionService.deleteTransaction(uuid, userId);

        return ResponseEntity.noContent().build();
    }


}
