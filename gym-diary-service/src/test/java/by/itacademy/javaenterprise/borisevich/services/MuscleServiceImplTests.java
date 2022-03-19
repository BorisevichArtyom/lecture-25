package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.dao.MuscleDAO;
import by.itacademy.javaenterprise.borisevich.entity.Muscle;
import by.itacademy.javaenterprise.borisevich.services.impl.MuscleServiceImpl;
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
public class MuscleServiceImplTests {
    @Mock
    private MuscleDAO muscleDAO;
    @InjectMocks
    private static MuscleService<Muscle> muscleService;
    private Muscle muscle = Muscle.builder().id(1L).muscleName("Test").description("No description").build();

    @BeforeAll
    public static void setUp() throws Exception {
        muscleService = new MuscleServiceImpl();
    }

    @Test
    public void shouldReturnMuscle() throws Exception {
        given(muscleDAO.findById(1L))
                .willReturn(muscle);
        assertEquals(muscle, muscleService.showOne(1L));
    }

    @Test
    public void saveMuscleTest() throws Exception {
        muscleService.save(muscle);
        Mockito.verify(muscleDAO, times(1)).saveOrUpdate(muscle);
    }

    @Test
    public void deleteMuscleTest() throws Exception {
        muscleService.delete(muscle.getId());
        Mockito.verify(muscleDAO, times(1)).deleteById(muscle.getId());
    }

    @Test
    public void showAllMusclesTest() throws Exception {
        List<Muscle> list = new ArrayList<>();
        list.add(muscle);
        when(muscleDAO.findAllPageByPage(1, 1)).thenReturn(list);
        assertEquals(list, muscleService.showAllPageByPage(1, 1));
    }

}
