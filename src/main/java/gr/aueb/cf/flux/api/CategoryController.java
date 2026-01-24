package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.CategoryUpdateDTO;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.service.ICategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final ICategoryService categoryService;


    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<CategoryReadOnlyDTO> createCategory(
            @RequestBody @Valid CategoryInsertDTO dto,
            @AuthenticationPrincipal User currentUser
    ){
        CategoryReadOnlyDTO createdCategory = categoryService.createCategory(dto, currentUser);

        return ResponseEntity.status(201).body(createdCategory);
    }


    @Operation(summary = "Get all categories for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<CategoryReadOnlyDTO>> getAllCategoriesByUser(
            @AuthenticationPrincipal User currentUser
            ){
        Long userId = currentUser.getId();

        List<CategoryReadOnlyDTO> categories = categoryService.getAllCategoriesByUser(userId);

        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get category by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<CategoryReadOnlyDTO> getCategoryByUuid(
            @PathVariable String uuid,
            @AuthenticationPrincipal User currentUser
    ) throws AppObjectNotFoundException
    {
        Long userId = currentUser.getId();

        CategoryReadOnlyDTO category = categoryService.getCategoryByUuid(uuid, userId);

        return ResponseEntity.ok(category);
    }


    @Operation(summary = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<CategoryReadOnlyDTO> updateCategory(
            @PathVariable String uuid,
            @RequestBody @Valid CategoryUpdateDTO dto,
            @AuthenticationPrincipal User currentUser
            ) throws AppObjectNotFoundException
    {
        Long userId = currentUser.getId();

        CategoryReadOnlyDTO updatedCategory = categoryService.updateCategory(uuid,userId, dto);

        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable String uuid,
            @AuthenticationPrincipal User currentUser
    ) throws AppObjectNotFoundException
    {
        Long userId = currentUser.getId();

        categoryService.deleteCategory(uuid, userId);

        return ResponseEntity.noContent().build();
    }
}
