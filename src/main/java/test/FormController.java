package test;

import org.example.sipvs.Team;
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
        model.addAttribute("team", new Team());
        return "form";
    }

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute Team team) throws MarshalException {
        xmlService.saveXml(team);
        return "result";
    }

    @RequestMapping(value="/form", method=RequestMethod.POST, params="action=validate")
    public String validate(@ModelAttribute Team team, Model model) throws MarshalException {
        boolean validity =xmlService.isXmlValid(team);
        model.addAttribute("valid", validity);
        return "form";
    }
    @RequestMapping(value="/form", method=RequestMethod.POST, params="action=show")
    public String show(@ModelAttribute Team team) throws MarshalException {
        System.out.println("show");
//        TODO make xsl transformation
        return "form";
    }
}