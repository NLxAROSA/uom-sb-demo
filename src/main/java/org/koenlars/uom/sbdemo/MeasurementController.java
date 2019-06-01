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
	
	/**
	 * 
	 * @param sourceMeasurement
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/conversion")
	public Measurement doTransformFromPrefix(@RequestBody Measurement sourceMeasurement) throws Exception	{
		
		Unit<?> sourceUnit = Units.getInstance().getUnit(sourceMeasurement.getUnit()).prefix(MetricPrefix.valueOf(sourceMeasurement.getPrefix()));
		Unit<?> targetUnit = Units.getInstance().getUnit(sourceMeasurement.getTargetUnit()).prefix(MetricPrefix.valueOf(sourceMeasurement.getTargetPrefix()));
		UnitConverter converter = sourceUnit.getConverterToAny(targetUnit);
		
		Number result = converter.convert(sourceMeasurement.getValue());
		
		Measurement resultMeasurement = new Measurement();
		resultMeasurement.setValue(result);
		resultMeasurement.setUnit(sourceMeasurement.getTargetUnit());
		resultMeasurement.setPrefix(sourceMeasurement.getTargetPrefix());
		
		return resultMeasurement;
	}
}
