package gr.aueb.cf.flux.service;

import gr.aueb.cf.flux.dto.CategoryInsertDTO;
import gr.aueb.cf.flux.dto.CategoryReadOnlyDTO;
import gr.aueb.cf.flux.model.User;

import java.util.List;

public interface ICategoryService {

    //POST /api/categories
    CategoryReadOnlyDTO createCategory(CategoryInsertDTO dto, User user);

    //GET /api/categories
    List<CategoryReadOnlyDTO> getAllCategoriesByUser(Long userId);
}
