package by.itacademy.javaenterprise.borisevich.controllers;

import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    @Autowired
    private TrainingService<Training> trainingService;

    @GetMapping
    public ModelAndView findAll() {
        List<Training> trainings = trainingService.showAll();
        ModelAndView view = new ModelAndView();
        view.setViewName("trainings");
        view.addObject("trainings", trainings);
        view.addObject("appName", "Gym Diary");
        return view;
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<Training>> findAllPageByPage(@RequestParam("page") int page,
                                                            @RequestParam("size") int size) {
        List<Training> trainings = trainingService.showAllPageByPage(page, size);
        if (trainings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Training> findOne(@PathVariable Long id) {
        Training training = trainingService.showOne(id);
        if (training == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(training, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String saveTraining(@RequestBody Training training) {
         trainingService.saveTraining(training);
        return  "resource saved" + training.getId();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTraining(@PathVariable Long id, @RequestBody Training newTraining) {
        Training training = trainingService.showOne(id);
        if (training == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        training.setTrainingDate(newTraining.getTrainingDate());
        training.setStart(newTraining.getStart());
        training.setEnd(newTraining.getEnd());
        trainingService.saveTraining(training);
        return new ResponseEntity<>(training, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTraining(@PathVariable Long id) {
        trainingService.delete(id);
        return ResponseEntity.ok("resource deleted");
    }

}
