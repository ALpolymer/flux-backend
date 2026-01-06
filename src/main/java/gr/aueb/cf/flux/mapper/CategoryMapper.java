package gr.aueb.cf.flux.mapper;

import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.CategoryUpdateDTO;
import gr.aueb.cf.flux.model.Category;
import gr.aueb.cf.flux.model.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category mapToCategoryEntityInsert(CategoryInsertDTO dto, User user){
        Category category = new Category();

        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setUser(user);

        return category;
    }

    public Category mapToCategoryEntityUpdate(CategoryUpdateDTO dto, Category existingCategory){
        existingCategory.setName(dto.name());
        existingCategory.setDescription(dto.description());

        return existingCategory;
    }

    public CategoryReadOnlyDTO mapToCategoryReadOnlyDTO(Category category){
        return new CategoryReadOnlyDTO(
                category.getUuid(),
                category.getUser().getUuid(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
