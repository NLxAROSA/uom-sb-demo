package org.koenlars.uom.sbdemo;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Length;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import tech.units.indriya.unit.Units;

@RestController
public class MeasurementController {
	@GetMapping("/meters/{number}")
	public Number doTransformIntoMeters(@PathVariable("number") Number number) {
		Unit<Length> sourceUnit = MetricPrefix.KILO(Units.METRE);
		Unit<Length> targetUnit = Units.METRE;
		UnitConverter converter = sourceUnit.getConverterTo(targetUnit);
		return converter.convert(number); 
	}
}
