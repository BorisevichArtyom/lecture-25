package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.dao.TrainingDAO;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.Training;
import by.itacademy.javaenterprise.borisevich.entity.TrainingSet;
import by.itacademy.javaenterprise.borisevich.services.exception.ServiceException;
import by.itacademy.javaenterprise.borisevich.services.impl.TrainingServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTests {
    @Mock
    private TrainingDAO trainingDAO;
    @InjectMocks
    private static TrainingService<Training> trainingService;
    private Training training = Training.builder().id(1L).trainingDate(LocalDate.now()).start(LocalTime.now()).build();

    @BeforeAll
    public static void setUp() throws Exception {
        trainingService = new TrainingServiceImpl();
    }

    @Test
    public void shouldReturnTraining() throws Exception {
        given(trainingDAO.findById(1L))
                .willReturn(training);
        assertEquals(training, trainingService.showOne(1L));
    }

    @Test
    public void saveTrainingTest() throws Exception {
        trainingService.saveTraining(training);
        Mockito.verify(trainingDAO, times(1)).saveOrUpdate(training);
    }

    @Test
    public void deleteTrainingTest() throws Exception {
        trainingService.delete(training.getId());
        Mockito.verify(trainingDAO, times(1)).deleteById(training.getId());
    }

    @Test
    public void showAllTrainingsTest() throws Exception {
        List<Training> list = new ArrayList<>();
        list.add(training);
        when(trainingDAO.findAllPageByPage(1, 1)).thenReturn(list);
        assertEquals(list, trainingService.showAllPageByPage(1, 1));
    }


    @SneakyThrows
    @Test
    public void showAllSetsNoSuchSetsTest() throws ServiceException {
        Mockito.doThrow(new NullPointerException()).when(trainingDAO).findSetsJoinFetch(0L);
        assertThrows(NullPointerException.class, () -> trainingService.showAllSets(0L));
    }

    @SneakyThrows
    @Test
    public void showAllSetsTest() throws DAOException {
        when(trainingDAO.findSetsJoinFetch(1L)).thenReturn(training);
        assertEquals(training, trainingService.showAllSets(1L));
    }

    @SneakyThrows
    @Test
    public void showSetsOfTrainingTest() throws DAOException {
        when(trainingDAO.findSetsJoinFetch(1L)).thenReturn(training);
        Set<TrainingSet> trainingSets=new HashSet<>();
        TrainingSet trainingSet = TrainingSet.builder().id(2L).approachCounter(1).time(LocalTime.now()).repeats(40).weight(80).build();
        trainingSets.add(trainingSet);
        training.setTrainingSet(trainingSets);
        assertEquals(trainingSet, trainingService.showTrainingSets(1L,2L));

    }

    @Test
    public void showSetsOfTrainingInvalidTest() throws DAOException, ServiceException {
        when(trainingDAO.findSetsJoinFetch(1L)).thenReturn(training);
        Set<TrainingSet> trainingSets=new HashSet<>();
        TrainingSet trainingSet = TrainingSet.builder().id(2L).approachCounter(1).time(LocalTime.now()).repeats(40).weight(80).build();
        trainingSets.add(trainingSet);
        training.setTrainingSet(trainingSets);
        assertThrows(ServiceException.class, () ->  trainingService.showTrainingSets(1L,3L));

    }

}
