package org.koenlars.uom.sbdemo;

import javax.measure.IncommensurableException;
import javax.measure.MetricPrefix;
import javax.measure.UnconvertibleException;
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
	 * @param measurement
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/conversion")
	public Measurement doTransformFromPrefix(@RequestBody Measurement measurement) throws Exception	{
		if (measurement.getTargetPrefix()==null) {
			measurement.setTargetPrefix(measurement.getPrefix());
		}
		
		Unit<?> sourceUnit = createUnit(measurement.getUnit(),measurement.getPrefix());
		Unit<?> targetUnit = createUnit(measurement.getTargetUnit(),measurement.getTargetPrefix());
		
		Measurement resultMeasurement = new Measurement();
		resultMeasurement.setValue(conversionOfUnits(sourceUnit, targetUnit, measurement.getValue()));
		resultMeasurement.setUnit(measurement.getUnit());
		resultMeasurement.setPrefix(measurement.getTargetPrefix());
		return resultMeasurement;
	}
	
	private Unit<?> createUnit( String unit, String prefix) throws UnconvertibleException, IncommensurableException {
		Unit<?> targetUnit = Units.getInstance().getUnit(unit);
		if (prefix!=null) {
			targetUnit = targetUnit.prefix(MetricPrefix.valueOf(prefix));
		}
		return targetUnit; 
	}
	
	private Number conversionOfUnits(Unit<?> sourceUnit, Unit<?> targetUnit, Number value) throws UnconvertibleException, IncommensurableException {
		UnitConverter converter = sourceUnit.getConverterToAny(targetUnit);
		Number result = converter.convert(value);
		return result;
	}
}
