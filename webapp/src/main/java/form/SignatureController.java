package form;

import form.services.SigningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SignatureController {

    @Autowired
    private SigningService signingService;

    @RequestMapping(value="/saveSigned", method=POST)
    @ResponseBody
    public void saveSigned(@RequestBody String signedXml) {
        signingService.saveXml(signedXml);
    }

    @RequestMapping(value="/xml", method=GET)
    @ResponseBody
    public String getXml() {
        return signingService.getXml();
    }

    @RequestMapping(value="/xsd", method=GET)
    @ResponseBody
    public String getXsd() {
        return signingService.getXsd();
    }

    @RequestMapping(value="/xsl", method=GET)
    @ResponseBody
    public String getXsl() {
        return signingService.getXsl();
    }
}
