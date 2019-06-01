package org.koenlars.uom.sbdemo;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.units.indriya.unit.Units;

@RestController
public class MeasurementController {
	
	@GetMapping("/meters/{number}")
	public Measurement doTransformIntoMeters(@PathVariable("number") String number) throws Exception	{
		Measurement measurement = new Measurement(); 
		measurement.setPrefix("KILO");
		measurement.setUnit("m");
		measurement.setValue(new Long(number));
		return doTransformFromPrefix(measurement); 
	}
	
	/**
	 * 
	 * @param measurement
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/measurement")
	public Measurement doTransformFromPrefix(@RequestBody Measurement measurement) throws Exception	{
		Unit<?> sourceUnit = Units.getInstance().getUnit(measurement.getUnit()).prefix(MetricPrefix.valueOf(measurement.getPrefix()));
		Unit<?> targetUnit = Units.getInstance().getUnit(measurement.getUnit());
		UnitConverter converter = sourceUnit.getConverterToAny(targetUnit);
		Number result = converter.convert(measurement.getValue());
		Measurement resultMeasurement = new Measurement();
		resultMeasurement.setValue(result);
		resultMeasurement.setUnit(measurement.getUnit());
		resultMeasurement.setPrefix(measurement.getPrefix());
		
		return resultMeasurement;
	}
}
