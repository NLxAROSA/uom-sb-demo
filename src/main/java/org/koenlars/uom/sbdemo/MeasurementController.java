package org.koenlars.uom.sbdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MeasurementController {
	@GetMapping("/meters/{number}")
	public int doTransformIntoMeters(@PathVariable("number") String number) {
		return new Integer(number) * 1000; 
	}
}
