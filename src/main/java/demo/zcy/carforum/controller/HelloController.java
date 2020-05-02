package demo.zcy.carforum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @param name
 * @param model
 * @return
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
	public String hello(@RequestParam(name="name", required=false, defaultValue="2020 毕业生") String name, Model model) {
		model.addAttribute("name", name);
		return "hello";
	}
}