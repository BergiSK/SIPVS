package form;

import org.example.sipvs.Team;
import org.example.sipvs.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import form.services.XmlService;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.MarshalException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class FormController {

    @Autowired
    private XmlService xmlService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value="/xml", method=GET)
    @ResponseBody
    public String getXml() {
        return xmlService.getXml();
    }

    @RequestMapping(value="/xsd", method=GET)
    @ResponseBody
    public String getXsd() {
        return xmlService.getXsd();
    }

    @RequestMapping(value="/xsl", method=GET)
    @ResponseBody
    public String getXsl() {
        return xmlService.getXsl();
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        Team team = new Team();
        team.setUser(new User());
        model.addAttribute("team", team);
        return "form";
}

    @PostMapping("/form")
    public String formSubmit(@ModelAttribute Team team, Model model) throws MarshalException {
        xmlService.saveXml(team);
        model.addAttribute("saveResult", "XML successfully saved!");
        return "/form";
    }

    @RequestMapping(value="/form", method=RequestMethod.POST, params="action=validate")
    public String validate(@ModelAttribute Team team, Model model) throws MarshalException {
        String validationResult = xmlService.isXmlValid(team);
        model.addAttribute("validationResult", validationResult);
        return "form";
    }
    @RequestMapping(value="/form", method=RequestMethod.POST, params="action=show")
    public ModelAndView show(@ModelAttribute Team team) throws MarshalException {
        Source source = new StreamSource(new ByteArrayInputStream(xmlService.getXmlStream(team).toByteArray()));
        // adds the XML source file to the model so the XsltView can detect
        ModelAndView model = new ModelAndView("show");
        model.addObject("xmlSource", source);
        return model;
    }
}