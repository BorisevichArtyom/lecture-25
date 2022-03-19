package by.itacademy.javaenterprise.borisevich.controllers;

import by.itacademy.javaenterprise.borisevich.services.impl.GymDiaryApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @Autowired
    private GymDiaryApplicationService applicationService;

    @GetMapping("/")
    public ModelAndView modelAndView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        view.addObject("controllerVariable", "Start page");
        view.addObject("appName", applicationService.getApplicationName());
        return view;
    }
}
