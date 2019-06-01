package org.koenlars.uom.sbdemo;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.units.indriya.unit.Units;

@RestController
public class MeasurementController {
	@PostMapping("/meters")
	public Measurement doTransformIntoMeters(@RequestBody Measurement measurement) throws Exception	{
		Unit<?> sourceUnit = Units.getInstance().getUnit("m").prefix(MetricPrefix.valueOf("KILO"));
		Unit<?> targetUnit = Units.getInstance().getUnit("m");
		UnitConverter converter = sourceUnit.getConverterToAny(targetUnit);
		Number result = converter.convert(measurement.getValue());
		Measurement resultMeasurement = new Measurement();
		resultMeasurement.setValue(result);
		return resultMeasurement;
	}
}
