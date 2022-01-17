package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.dao.TrainingSetDAO;
import by.itacademy.javaenterprise.borisevich.entity.Exercise;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.services.impl.TrainingSetServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class TrainingSetServiceImplTests {
    @Mock
    private TrainingSetDAO trainingSetDAO;
    @InjectMocks
    private static TrainingSetService<TrainingSet> trainingSetService;
    private TrainingSet trainingSet = TrainingSet.builder().id(1L).nameExercise(Exercise.builder().name("pull ups").build())
            .approachCounter(2).time(LocalTime.now()).weight(100).repeats(10).build();

    @BeforeAll
    public static void setUp() throws Exception {
        trainingSetService = new TrainingSetServiceImpl();
    }

    @Test
    public void shouldReturnSet() throws Exception {
        given(trainingSetDAO.findById(1L))
                .willReturn(trainingSet);
        assertEquals(trainingSet, trainingSetService.showOne(1L));
    }

    @Test
    public void saveSetTest() throws Exception {
        trainingSetService.save(trainingSet);
        Mockito.verify(trainingSetDAO, times(1)).saveOrUpdate(trainingSet);
    }

    @Test
    public void deleteSetTest() throws Exception {
        trainingSetService.delete(trainingSet.getId());
        Mockito.verify(trainingSetDAO, times(1)).deleteById(trainingSet.getId());
    }

    @Test
    public void showAllSetPageByPageTest() throws Exception {
        List<TrainingSet> list = new ArrayList<>();
        list.add(trainingSet);
        when(trainingSetDAO.findAllPageByPage(1, 1)).thenReturn(list);
        assertEquals(list, trainingSetService.showAllPageByPage(1, 1));
    }

    @Test
    public void showAllSetTest() throws Exception {
        List<TrainingSet> list = new ArrayList<>();
        list.add(trainingSet);
        when(trainingSetDAO.findAll()).thenReturn(list);
        assertEquals(list, trainingSetService.showAll());
    }
}
