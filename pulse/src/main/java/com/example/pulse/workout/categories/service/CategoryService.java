package com.example.pulse.workout.categories.service;

import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.categories.entity.Category;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public Category findById(int categoryId) throws PulseException;
    public List<Category> findAllCategories() throws PulseException;
    public Category add(Category category) throws PulseException;
    public Category update(int id,Category category) throws PulseException;
    public boolean deleteById(int id)throws PulseException;
}
