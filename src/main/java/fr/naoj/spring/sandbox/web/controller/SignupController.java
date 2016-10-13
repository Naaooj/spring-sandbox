package fr.naoj.spring.sandbox.web.controller;

import fr.naoj.spring.sandbox.event.OnRegistrationCompleteEvent;
import fr.naoj.spring.sandbox.model.Signup;
import fr.naoj.spring.sandbox.persistence.entity.User;
import fr.naoj.spring.sandbox.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Johann Bernez
 */
@Controller
@RequestMapping("/")
public class SignupController {

	private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("signup", new Signup());
		return "signup";
	}

    @Transactional
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute Signup signupModel, BindingResult result, final HttpServletRequest request) {
		if (result.hasErrors()) {
			LOG.info("Signup form has error(s)");
			return "signup";
		}

		final User createdUser = userService.createUser(signupModel);
		if (createdUser == null) {
			result.rejectValue("email", "email.error.already.registered");
		} else {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(createdUser, getUrl(request), request.getLocale()));
		}

		return "signup";
	}

	private String getUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
