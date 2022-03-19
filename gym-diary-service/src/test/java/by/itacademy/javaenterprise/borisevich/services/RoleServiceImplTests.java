package by.itacademy.javaenterprise.borisevich.services;

import by.itacademy.javaenterprise.borisevich.dao.RoleDAO;
import by.itacademy.javaenterprise.borisevich.entity.Role;
import by.itacademy.javaenterprise.borisevich.entity.RoleType;
import by.itacademy.javaenterprise.borisevich.services.impl.RoleServiceImpl;
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
public class RoleServiceImplTests {
    @Mock
    private RoleDAO roleDAO;
    @InjectMocks
    private static RoleService<Role> roleService;
    private Role role = Role.builder().id(1L).name(RoleType.ATHLETE).build();

    @BeforeAll
    public static void setUp() throws Exception {
        roleService = new RoleServiceImpl();
    }

    @Test
    public void shouldReturnUser() throws Exception {
        given(roleDAO.findById(1L))
                .willReturn(role);
        assertEquals(role, roleService.showOne(1L));
    }

    @Test
    public void saveUserTest() throws Exception {
        roleService.save(role);
        Mockito.verify(roleDAO, times(1)).saveOrUpdate(role);
    }

    @Test
    public void deleteUserTest() throws Exception {
        roleService.delete(role.getId());
        Mockito.verify(roleDAO, times(1)).deleteById(role.getId());
    }

    @Test
    public void showAllUsersTest() throws Exception {
        List<Role> list = new ArrayList<>();
        list.add(role);
        when(roleDAO.findAll()).thenReturn(list);
        assertEquals(list, roleService.showAll());
    }
}
