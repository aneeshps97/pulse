package com.example.pulse.workout.categories.service;

import com.example.pulse.constants.StatusCodes;
import com.example.pulse.exception.PulseException;
import com.example.pulse.workout.categories.entity.Category;
import com.example.pulse.workout.categories.repository.CategoryRepository;
import com.example.pulse.workout.exercise.entity.Exercise;
import com.example.pulse.workout.exercise.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
CategoryRepository categoryRepository;
ExerciseRepository exerciseRepository;
    @Override
    public Category findById(int categoryId) throws PulseException {
        Category category =null;
        try {
              category = categoryRepository.findById(categoryId).orElseThrow(()->new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED));
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED);
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories() throws PulseException {
        List<Category> categories = new ArrayList<>();
        try {
            categories = categoryRepository.findAll();
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED);
        }
        return categories;
    }

    @Override
    public Category add(Category category) throws PulseException {
        try {
            List<Exercise> exercises = category.getExercises();
            List<Exercise> existingExercises = new ArrayList<>();
            if (exercises != null && !exercises.isEmpty()) {
                for (Exercise exercise : exercises) {
                    if (exercise.getId() > 0) {
                        Exercise existingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(() -> new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        Category existingCategory = existingExercise.getCategory();
                        if (existingCategory != null && existingCategory.getId() > 0) {
                            throw new PulseException(StatusCodes.EXERCISE_ALREADY_ASSIGNED_FOR_ANOTHER_CATEGORY);
                        } else {
                            existingExercise.setCategory(category);
                            existingExercises.add(existingExercise);
                        }
                    } else {
                        exercise.setCategory(category);
                        existingExercises.add(exercise);
                    }
                }
            }

            category.getExercises().clear();
            category.setExercises(existingExercises);
            category = categoryRepository.save(category);

        } catch (DataAccessException e ) {
            throw new PulseException(StatusCodes.CATEGORY_ADDING_FAILED);
        }
        return category;
    }

    @Override
    public Category update(int id, Category category) throws PulseException {
        Category existingCategory = null;
        List<Exercise> existingExercises = new ArrayList<>();
        List<Exercise> exercises = null;
        try {
            exercises = category.getExercises();
            existingCategory = categoryRepository.findById(id).orElseThrow(()-> new PulseException(StatusCodes.CATEGORY_FETCHING_FAILED));
            if (exercises!=null && !exercises.isEmpty()){
                for (Exercise exercise : exercises){
                    if (exercise.getId()>0){
                        Exercise exisingExercise = exerciseRepository.findById(exercise.getId()).orElseThrow(()->new PulseException(StatusCodes.EXERCISE_FETCHING_FAILED));
                        exisingExercise.setCategory(existingCategory);
                        existingExercises.add(exisingExercise);
                    }else {
                        exercise.setCategory(existingCategory);
                        existingExercises.add(exercise);
                    }
                }
            }
            existingCategory.getExercises().clear();
            existingCategory.setExercises(existingExercises);

        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.CATEGORY_UPDATING_FAILED);
        }
        return existingCategory;
    }

    @Override
    public boolean deleteById(int id) throws PulseException {
        boolean isCategoryDeleted = false;
        try {
            categoryRepository.deleteById(id);
            isCategoryDeleted = true;
        }catch (DataAccessException e){
            throw new PulseException(StatusCodes.CATEGORY_DELETION_FAILED);
        }
        return isCategoryDeleted;
    }
}
