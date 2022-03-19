package by.itacademy.javaenterprise.borisevich.controllers;

import by.itacademy.javaenterprise.borisevich.entity.Exercise;
import by.itacademy.javaenterprise.borisevich.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    @Autowired
    private ExerciseService<Exercise> exerciseService;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<Exercise>> findAllPagination(@RequestParam("page") int page,
                                                            @RequestParam("size") int size) {
        List<Exercise> exercises = exerciseService.showAll(page, size);
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> findOne(@PathVariable("id") Long id) {
        Exercise exercise = exerciseService.showOne(id);
        if (exercise == null) {
            return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<?> saveExercise(@RequestBody Exercise exercise) {
        exerciseService.save(exercise);
        return new ResponseEntity<>(exercise.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExercise(@PathVariable Long id, @RequestBody Exercise newExercise) {
        Exercise oldExercise = exerciseService.showOne(id);
        if (oldExercise == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldExercise.setName(newExercise.getName());
        oldExercise.setDescription(newExercise.getDescription());
        exerciseService.save(oldExercise);
        return new ResponseEntity<>(oldExercise, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        exerciseService.delete(id);
        return ResponseEntity.ok("resource deleted");
    }

}
