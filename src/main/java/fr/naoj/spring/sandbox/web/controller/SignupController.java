package fr.naoj.spring.sandbox.web.controller;

import fr.naoj.spring.sandbox.model.Signup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Johann Bernez
 */
@Controller
@RequestMapping("/")
public class SignupController {

	private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("signup", new Signup());
		return "signup";
	}

	@Transactional
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute Signup signupModel, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			LOG.info("Signup form has error(s)");
		}
		return "signup";
	}
}
