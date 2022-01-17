package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.dao.ExerciseDAO;
import by.itacademy.javaenterprise.borisevich.entity.Exercise;
import by.itacademy.javaenterprise.borisevich.services.impl.ExerciseServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
public class ExerciseServiceImplTests {
    @Mock
    private ExerciseDAO exerciseDAO;
    @InjectMocks
    private static ExerciseService<Exercise> exerciseService;
    private Exercise exercise = Exercise.builder().id(1L).name("Test").description("No description").build();

    @BeforeAll
    public static void setUp() throws Exception {
        exerciseService = new ExerciseServiceImpl();
    }

    @Test
    public void shouldReturnExercise() throws Exception {
        given(exerciseDAO.findById(1L))
                .willReturn(exercise);
        assertEquals(exercise, exerciseService.showOne(1L));
    }

    @Test
    public void saveExerciseTest() throws Exception {
        exerciseService.save(exercise);
        Mockito.verify(exerciseDAO, times(1)).saveOrUpdate(exercise);
    }

    @Test
    public void deleteExerciseTest() throws Exception {
        exerciseService.delete(exercise.getId());
        Mockito.verify(exerciseDAO, times(1)).deleteById(exercise.getId());
    }

    @Test
    public void showAllExerciseTest() throws Exception {
        List<Exercise> list = new ArrayList<>();
        list.add(exercise);
        when(exerciseDAO.findAll(1, 1)).thenReturn(list);
        assertEquals(list, exerciseService.showAll(1,1));
    }

}
