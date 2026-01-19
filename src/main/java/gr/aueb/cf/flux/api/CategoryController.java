package gr.aueb.cf.flux.api;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.CategoryUpdateDTO;
import gr.aueb.cf.flux.dto.WalletInsertDTO;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    // ═══════════════════════════════════════════
    // POST /api/categories
    // ═══════════════════════════════════════════
    @PostMapping
    public ResponseEntity<CategoryReadOnlyDTO> createCategory(
            @RequestBody @Valid CategoryInsertDTO dto,
            @AuthenticationPrincipal User currentUser
    ){
        CategoryReadOnlyDTO createdCategory = categoryService.createCategory(dto, currentUser);

        return ResponseEntity.status(201).body(createdCategory);
    }


    // ═══════════════════════════════════════════
    // GET /api/categories
    // ═══════════════════════════════════════════
    @GetMapping
    public ResponseEntity<List<CategoryReadOnlyDTO>> getAllCategoriesByUser(
            @AuthenticationPrincipal User currentUser
            ){
        Long userId = currentUser.getId();

        List<CategoryReadOnlyDTO> categories = categoryService.getAllCategoriesByUser(userId);

        return ResponseEntity.ok(categories);
    }

    // ═══════════════════════════════════════════
    // GET /api/categories/{uuid}
    // ═══════════════════════════════════════════
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

    // ═══════════════════════════════════════════
    // PUT /api/categories/{uuid}
    // ═══════════════════════════════════════════
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

    // ═══════════════════════════════════════════
    // DELETE /api/categories/{uuid}
    // ═══════════════════════════════════════════
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
