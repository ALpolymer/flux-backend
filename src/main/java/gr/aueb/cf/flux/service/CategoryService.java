package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.CategoryUpdateDTO;
import gr.aueb.cf.flux.mapper.CategoryMapper;
import gr.aueb.cf.flux.model.Category;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryReadOnlyDTO createCategory(CategoryInsertDTO dto, User user) {
        Category categoryToCreate = categoryMapper.mapToCategoryEntityInsert(dto, user);

        Category createdCategory = categoryRepository.save(categoryToCreate);

        log.info("Category with id={} created", createdCategory.getId());
        return categoryMapper.mapToCategoryReadOnlyDTO(createdCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryReadOnlyDTO> getAllCategoriesByUser(Long userId) {

        List<Category> allCategories = categoryRepository.findByUserId(userId);
        return allCategories.stream()
                .map(categoryMapper :: mapToCategoryReadOnlyDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryReadOnlyDTO getCategoryByUuid(String uuid, Long userId) throws AppObjectNotFoundException {

        return categoryRepository.findByUuidAndUserId(uuid, userId)
                .map(categoryMapper :: mapToCategoryReadOnlyDTO)
                .orElseThrow(()-> new AppObjectNotFoundException("Category", "Category with uuid " + uuid +" not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryReadOnlyDTO updateCategory(String uuid, Long userId, CategoryUpdateDTO dto) throws AppObjectNotFoundException {
        Optional<Category> savedCategory = categoryRepository.findByUuidAndUserId(uuid, userId);

        if(savedCategory.isEmpty()) throw new AppObjectNotFoundException("Category", "Category with uuid " + uuid +" not found");

        Category updatedCategory = categoryMapper.mapToCategoryEntityUpdate(dto, savedCategory.get());

        Category savedUpdatedCategory = categoryRepository.save(updatedCategory);

        log.info("Category with uuid={} updated", savedUpdatedCategory.getUuid());

        return categoryMapper.mapToCategoryReadOnlyDTO(savedUpdatedCategory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(String uuid, Long userId) throws AppObjectNotFoundException {
        Optional<Category> categoryToDelete = categoryRepository.findByUuidAndUserId(uuid, userId);

        if(categoryToDelete.isEmpty()) throw new AppObjectNotFoundException("Category", "Category with uuid " + uuid +" not found");

        categoryRepository.delete(categoryToDelete.get());
    }
}
