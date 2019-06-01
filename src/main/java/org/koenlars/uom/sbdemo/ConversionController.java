package org.koenlars.uom.sbdemo;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.units.indriya.unit.Units;

@RestController
public class ConversionController {

	/**
	 * 
	 * @param sourceMeasurement
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/conversion")
	public ConversionOutput doTransformFromPrefix(@RequestBody ConversionInput conversionInput)	{

		Unit<?> sourceUnit = createUnit(conversionInput.getUnit(), conversionInput.getPrefix());
		Unit<?> targetUnit = createUnit(conversionInput.getTargetUnit(), conversionInput.getTargetPrefix());

		ConversionOutput conversionOutput = new ConversionOutput();
		conversionOutput.setInput(conversionInput);
		conversionOutput.setTargetValue(conversionOfUnits(sourceUnit, targetUnit, conversionInput.getValue()));

		return conversionOutput;
	}

	private Unit<?> createUnit(String unit, String prefix)	{

		try {
			Unit<?> targetUnit = Units.getInstance().getUnit(unit);
			if (prefix != null) {
				targetUnit = targetUnit.prefix(MetricPrefix.valueOf(prefix));
			}
			return targetUnit;
		} catch (Exception e) {
			throw new UserInputException(e.getMessage());
		}
	}

	private Number conversionOfUnits(Unit<?> sourceUnit, Unit<?> targetUnit, Number value) {
		try {
			UnitConverter converter = sourceUnit.getConverterToAny(targetUnit);
			Number result = converter.convert(value);
			return result;
		} catch (Exception e) {
			throw new UserInputException(e.getMessage());
		}
	}
}
