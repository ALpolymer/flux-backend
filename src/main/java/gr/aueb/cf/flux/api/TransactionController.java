package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionInsertDTO;
import gr.aueb.cf.flux.dto.TransactionReadOnlyDTO;
import gr.aueb.cf.flux.dto.TransactionUpdateDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.service.ICategoryService;
import gr.aueb.cf.flux.service.ITransactionService;
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
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final ITransactionService transactionService;

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Wallet or Category not found")
    })
    @PostMapping
    public ResponseEntity<TransactionReadOnlyDTO> createTransaction(
            @RequestBody @Valid TransactionInsertDTO dto,
            @AuthenticationPrincipal User currentUser
            ) throws AppObjectNotFoundException
    {
        TransactionReadOnlyDTO createdTransaction = transactionService.createTransaction(dto, currentUser);

        return ResponseEntity.status(201).body(createdTransaction);
    }

    @Operation(summary = "Get all transactions for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<TransactionReadOnlyDTO>> getAllTransactionsByUserId(
            @AuthenticationPrincipal User currentUser
    ){
        Long userId = currentUser.getId();

        List<TransactionReadOnlyDTO> transactions = transactionService.getAllTransactionsByUser(userId);

        return ResponseEntity.ok(transactions);
    }


    @Operation(summary = "Get transaction by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
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

    @Operation(summary = "Update an existing transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
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

    @Operation(summary = "Delete a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
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
