package com.labforward.api.hello.service;

import com.labforward.api.core.validation.EntityValidator;
import com.labforward.api.hello.domain.Greeting;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class HelloWorldService {

	public static String DEFAULT_ID = "default";

	public static String DEFAULT_MESSAGE = "Hello World!";

	private Map<String, Greeting> greetings;

	private EntityValidator entityValidator;

	public HelloWorldService(EntityValidator entityValidator) {
		this.entityValidator = entityValidator;

		this.greetings = new HashMap<>(1);
		save(getDefault());
	}

	/**
	 * Returns the default Greeting
	 * @return new Greeting with the default values
	 */
	private static Greeting getDefault() {
		return new Greeting(DEFAULT_ID, DEFAULT_MESSAGE);
	}

	/**
	 * Creates a new greeting with a new message
	 * @param request Greeting object
	 * @return Optional with new greeting if the creation was successful or empty if else
	 */
	public Greeting createGreeting(Greeting request) {
		entityValidator.validateCreate(request);

		request.setId(UUID.randomUUID().toString());
		return save(request);
	}

	/**
	 * Obtains a greeting with a given Id
	 * @param id unique identifier of an Id
	 * @return Optional with the found Greeting or empty if not found
	 */
	public Optional<Greeting> getGreeting(String id) {
		Greeting greeting = greetings.get(id);
		if (greeting == null) {
			return Optional.empty();
		}

		return Optional.of(greeting);
	}

	public Optional<Greeting> getDefaultGreeting() {
		return getGreeting(DEFAULT_ID);
	}

	/**
	 * Saves a new Greeting
	 * @param greeting new greeting
	 * @return newly created greeting
	 */
	private Greeting save(Greeting greeting) {
		this.greetings.put(greeting.getId(), greeting);

		return greeting;
	}

	/**
	 * Updates a Greeting with a given Id
	 * @param id Unique identifier of the Greeting
	 * @param request Greeting object with the new message and Id
	 * @return Optional with modified greeting if the update was successful or empty if else
	 */
	public Optional<Greeting> updateGreeting(String id, Greeting request) {
		entityValidator.validateUpdate(id, request);
		Greeting greeting = greetings.get(id);
		if (greeting == null) {
			return Optional.empty();
		}
		greeting.setMessage(request.getMessage());
		this.greetings.put(id, greeting);

		return Optional.of(greeting);
	}
}
