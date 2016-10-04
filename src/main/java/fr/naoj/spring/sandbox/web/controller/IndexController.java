package fr.naoj.spring.sandbox.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.naoj.spring.sandbox.model.SandboxUser;

/**
 * @author Johann Bernez
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		final ModelAndView mv = new ModelAndView("index");
		final SandboxUser principal = (SandboxUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mv.getModel().put("user", principal.getDisplayName());
		return mv;
	}
}
