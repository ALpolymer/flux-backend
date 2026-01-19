package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.mapper.CategoryMapper;
import gr.aueb.cf.flux.mapper.WalletMapper;
import gr.aueb.cf.flux.model.Category;
import gr.aueb.cf.flux.model.User;
import gr.aueb.cf.flux.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
