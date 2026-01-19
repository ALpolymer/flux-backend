package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.dto.CategoryUpdateDTO;
import gr.aueb.cf.flux.model.User;

import java.util.List;

public interface ICategoryService {

    //POST /api/categories
    CategoryReadOnlyDTO createCategory(CategoryInsertDTO dto, User user);

    //GET /api/categories
    List<CategoryReadOnlyDTO> getAllCategoriesByUser(Long userId);

    //GET /api/categories/{uuid}
    CategoryReadOnlyDTO getCategoryByUuid(String uuid, Long userId)
            throws AppObjectNotFoundException;

    //PUT /api/categories/{uuid}
    CategoryReadOnlyDTO updateCategory(String uuid, Long userId, CategoryUpdateDTO dto)
            throws AppObjectNotFoundException;

    void  deleteCategory(String uuid, Long userId)
        throws AppObjectNotFoundException;
}
