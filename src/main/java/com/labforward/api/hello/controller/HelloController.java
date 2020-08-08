package com.labforward.api.hello.controller;

import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Greetings Controller", description = "Manages greetings.")
public class HelloController {

	public static final String GREETING_NOT_FOUND = "Greeting Not Found";

	private HelloWorldService helloWorldService;

	public HelloController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Obtains the default greeting.", response = Greeting.class)
	public Greeting helloWorld() {
		return getGreeting(HelloWorldService.DEFAULT_ID);
	}

	@RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Obtains a greeting with a given id.", response = Greeting.class)
	public Greeting getGreeting(@PathVariable String id) {
		return helloWorldService.getGreeting(id)
		                        .orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}

	@RequestMapping(value = "/hello", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a greeting with a given message.", response = Greeting.class)
	public Greeting createGreeting(@RequestBody Greeting request) {
		return helloWorldService.createGreeting(request);
	}

	@RequestMapping(value = "/hello/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Updates an existing greeting with a given id.", response = Greeting.class)
	public Greeting updateGreeting(@PathVariable String id, @RequestBody Greeting request) {
		return helloWorldService.updateGreeting(id, request)
				.orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
	}
}
