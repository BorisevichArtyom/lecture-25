package by.itacademy.javaenterprise.borisevich.controllers;

import by.itacademy.javaenterprise.borisevich.entity.Muscle;
import by.itacademy.javaenterprise.borisevich.services.MuscleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/muscles")
public class MuscleController {
    @Autowired
    private MuscleService<Muscle> muscleService;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<Muscle>> findAll(@RequestParam("page") int page,
                                                @RequestParam("size") int size) {
        List<Muscle> muscles = muscleService.showAllPageByPage(page, size);
        if (muscles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(muscles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Muscle> findOne(@PathVariable("id") Long id) {
        Muscle muscle = muscleService.showOne(id);
        if (muscle == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(muscle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveMuscle(@RequestBody Muscle muscle) {
        muscleService.save(muscle);
        return new ResponseEntity<>(muscle.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMuscle(@PathVariable Long id, @RequestBody Muscle muscle) {
        Muscle oldMuscle = muscleService.showOne(id);
        if (oldMuscle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldMuscle.setMuscleName(muscle.getMuscleName());
        oldMuscle.setDescription(muscle.getDescription());
        muscleService.save(oldMuscle);
        return new ResponseEntity<>(oldMuscle, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMuscle(@PathVariable Long id) {
        muscleService.delete(id);
        return ResponseEntity.ok("resource deleted");
    }
}
