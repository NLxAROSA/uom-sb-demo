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
	 * @param sourceMeasurement
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/conversion")
	public ConversionUnit doTransformFromPrefix(@RequestBody ConversionUnit conversionUnit) throws Exception	{
		if (conversionUnit.getTargetPrefix()==null) {
			conversionUnit.setTargetPrefix(conversionUnit.getPrefix());
		}
		
		Unit<?> sourceUnit = createUnit(conversionUnit.getUnit(),conversionUnit.getPrefix());
		Unit<?> targetUnit = createUnit(conversionUnit.getTargetUnit(),conversionUnit.getTargetPrefix());
		
		ConversionUnit resultMeasurement = new ConversionUnit();
		resultMeasurement.setValue(conversionOfUnits(sourceUnit, targetUnit, conversionUnit.getValue()));
		resultMeasurement.setUnit(conversionUnit.getTargetUnit());
		resultMeasurement.setPrefix(conversionUnit.getTargetPrefix());
		
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
