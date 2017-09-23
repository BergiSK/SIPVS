package test;

import org.example.sipvs.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import test.services.XmlService;

import javax.xml.bind.MarshalException;

@Controller
public class FormController {

    @Autowired
    private XmlService xmlService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute User user) throws MarshalException {
        xmlService.validateXml(user);
        xmlService.saveXml(user);
        return "result";
    }

}