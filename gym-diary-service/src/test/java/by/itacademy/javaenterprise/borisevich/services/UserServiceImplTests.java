package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.dao.UserDAO;
import by.itacademy.javaenterprise.borisevich.entity.User;
import by.itacademy.javaenterprise.borisevich.services.impl.UserServiceImpl;
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
public class UserServiceImplTests {
    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private static UserService<User> userService;
    private User user = User.builder().id(1L).age(30).email("560ttt@tut.by")
            .userPassword("100obezyan").firstName("Frank").lastName("Ferdinand").weight(100).build();

    @BeforeAll
    public static void setUp() throws Exception {
        userService = new UserServiceImpl();
    }

    @Test
    public void shouldReturnUser() throws Exception {
        given(userDAO.findById(1L))
                .willReturn(user);
        assertEquals(user, userService.showOne(1L));
    }

    @Test
    public void saveUserTest() throws Exception {
        userService.save(user);
        Mockito.verify(userDAO, times(1)).saveOrUpdate(user);
    }

    @Test
    public void deleteUserTest() throws Exception {
        userService.delete(user.getId());
        Mockito.verify(userDAO, times(1)).deleteById(user.getId());
    }

    @Test
    public void showAllUsersTest() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(user);
        when(userDAO.findAllPageByPage(1, 1)).thenReturn(list);
        assertEquals(list, userService.showAllPageByPage(1, 1));
    }
}
