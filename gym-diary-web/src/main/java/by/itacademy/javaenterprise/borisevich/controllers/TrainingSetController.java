package by.itacademy.javaenterprise.borisevich.controllers;

import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.services.TrainingService;
import by.itacademy.javaenterprise.borisevich.services.TrainingSetService;
import by.itacademy.javaenterprise.borisevich.services.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/trainings/{trainingId}/sets")
public class TrainingSetController {
    @Autowired
    private TrainingSetService<TrainingSet> trainingSetService;
    @Autowired
    private TrainingService<Training> trainingService;

    @GetMapping
    public ResponseEntity<?> findAll(@PathVariable Long trainingId) {
        Training training;
        try {
            training = trainingService.showAllSets(trainingId);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<TrainingSet> trainingSet = training.getTrainingSet();
        return new ResponseEntity<>(trainingSet, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSet> findOne(@PathVariable Long trainingId, @PathVariable Long id) {
        TrainingSet trainingSet = null;
        try {
            trainingSet = trainingService.showTrainingSets(trainingId, id);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trainingSet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveSet(@PathVariable Long trainingId, @RequestBody TrainingSet trainingSet) {
        Training training;
        try {
            training = trainingService.showAllSets(trainingId);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        training.getTrainingSet().add(trainingSet);
        trainingService.saveTraining(training);
        return new ResponseEntity<>(trainingSet.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSet(@PathVariable Long trainingId, @PathVariable Long id,
                                       @RequestBody TrainingSet newTrainingSet) {
        TrainingSet trainingSet;
        try {
            trainingSet = trainingService.showTrainingSets(trainingId, id);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        trainingSet.setApproachCounter(newTrainingSet.getApproachCounter());
        trainingSet.setNameExercise(newTrainingSet.getNameExercise());
        trainingSet.setTime(newTrainingSet.getTime());
        trainingSet.setWeight(newTrainingSet.getWeight());
        trainingSet.setRepeats(newTrainingSet.getRepeats());
        trainingSetService.save(trainingSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSet(@PathVariable Long trainingId, @PathVariable Long id) {
        TrainingSet trainingSet;
        try {
            trainingSet = trainingService.showTrainingSets(trainingId, id);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        trainingSetService.delete(trainingSet.getId());
        return ResponseEntity.ok("resource deleted");
    }
}
