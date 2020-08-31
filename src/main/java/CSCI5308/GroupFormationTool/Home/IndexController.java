package CSCI5308.GroupFormationTool.Home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String greeting(Model model) {
        return "index";
    }
}
