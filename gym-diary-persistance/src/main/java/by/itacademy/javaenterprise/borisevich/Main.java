package by.itacademy.javaenterprise.borisevich;

import by.itacademy.javaenterprise.borisevich.config.PersistanceConfig;
import by.itacademy.javaenterprise.borisevich.dao.*;
import by.itacademy.javaenterprise.borisevich.dao.exception.DAOException;
import by.itacademy.javaenterprise.borisevich.entity.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public class Main {
    public static void main(String[] args) throws DAOException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistanceConfig.class);
//        RoleDAO daoImpl = context.getBean(RoleDAO.class);
//        User user = User.builder().email("griiib@tut.by")
//                .userPassword("Bicep").firstName("Denis")
//                .lastName("Samusenko").age(40).weight(200).balanceAmount(1333L).role(Role.builder().id(1L).name(RoleType.ATHLETE).build()).build();

//        ExerciseDAO daoImpl1 = context.getBean(ExerciseDAO.class);
//        Optional<Role> byName = daoImpl.findByName(RoleType.ADMIN);
//        System.out.println("AAAHHTUNG "+byName);
//        try {
//            Exercise byId = daoImpl1.findById(null);
//            System.out.println(""+byId);
//        } catch (DAOException e) {
//            e.printStackTrace();
//            System.out.println("XXX");
//        }
//        UserDAO daoImpl2 = context.getBean(UserDAO.class);
//        daoImpl2.saveOrUpdate(user);


//        TrainingDAO daoImpl3 = context.getBean(TrainingDAO.class);
//        daoImpl3.deleteById(5L);
//        Training training = daoImpl3.findById(2L);
//        Set<TrainingSet> trainingSet = training.getTrainingSet();
//        for (TrainingSet train:trainingSet) {
//            if (train.getId().equals(7L)) {
//                System.out.println(train);
//                trainingSet.remove(train);
//            }
//        }

//        TrainingSetDAO daoImpl4 = context.getBean(TrainingSetDAO.class);
//        daoImpl4.findAll();
        int a = 2;
        int b = 3;
        int c = a + b;
        System.out.println(c);

    }
}
