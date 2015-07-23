package uk.co.jemos.podam.test.unit.annotation;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.test.dto.annotations.AnnotatedFieldAndSetterPojo;
import uk.co.jemos.podam.test.dto.annotations.BooleanValuePojo;
import uk.co.jemos.podam.test.dto.annotations.ByteValuePojo;
import uk.co.jemos.podam.test.dto.annotations.ByteValueWithErrorPojo;
import uk.co.jemos.podam.test.dto.annotations.CharValuePojo;
import uk.co.jemos.podam.test.dto.annotations.CollectionAnnotationPojo;
import uk.co.jemos.podam.test.dto.annotations.DoubleValuePojo;
import uk.co.jemos.podam.test.dto.annotations.DoubleValueWithErrorPojo;
import uk.co.jemos.podam.test.dto.annotations.ExcludeAnnotationPojo;
import uk.co.jemos.podam.test.dto.annotations.FloatValuePojo;
import uk.co.jemos.podam.test.dto.annotations.FloatValueWithErrorPojo;
import uk.co.jemos.podam.test.dto.annotations.IntegerValuePojo;
import uk.co.jemos.podam.test.dto.annotations.IntegerValueWithErrorPojo;
import uk.co.jemos.podam.test.dto.annotations.LongValuePojo;
import uk.co.jemos.podam.test.dto.annotations.LongValueWithErrorPojo;
import uk.co.jemos.podam.test.dto.annotations.PodamStrategyPojo;
import uk.co.jemos.podam.test.dto.annotations.ShortValuePojo;
import uk.co.jemos.podam.test.dto.annotations.ShortValueWithErrorPojo;
import uk.co.jemos.podam.test.dto.annotations.StringValuePojo;
import uk.co.jemos.podam.test.dto.annotations.StringWithWrongStrategyTypePojo;
import uk.co.jemos.podam.test.strategies.ByteArrayStrategy;
import uk.co.jemos.podam.test.utils.PodamTestConstants;
import uk.co.jemos.podam.test.utils.PodamTestUtils;

public class AnnotationsUnitTest {

	private static final PodamFactory factory = new PodamFactoryImpl();

	@Test
	public void testAnnotatedFieldAndSetter() {
		AnnotatedFieldAndSetterPojo pojo = factory
				.manufacturePojo(AnnotatedFieldAndSetterPojo.class);
		Assert.assertNotNull("The pojo should not be null!", pojo);
		Assert.assertNotNull(
				"Field must be filled", pojo.getPostCode());
		Assert.assertEquals(
				PodamTestConstants.POST_CODE, pojo.getPostCode());
	}

	@Test
	public void testPodamExcludeAnnotation() {

		ExcludeAnnotationPojo pojo = factory
				.manufacturePojo(ExcludeAnnotationPojo.class);
		Assert.assertNotNull("The pojo should not be null!", pojo);
		int intField = pojo.getIntField();
		Assert.assertTrue("The int field should not be zero!", intField != 0);
		Assert.assertNull(
				"The other object in the pojo should be null because annotated with PodamExclude!",
				pojo.getSomePojo());

	}

	@Test
	public void testIntegerValueAnnotation() {

		IntegerValuePojo pojo = factory.manufacturePojo(IntegerValuePojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		int intFieldWithMinValueOnly = pojo.getIntFieldWithMinValueOnly();
		Assert.assertTrue("The int field with only minValue should be >= 0",
				intFieldWithMinValueOnly >= 0);
		int intFieldWithMaxValueOnly = pojo.getIntFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The int field with maximum value only should have a maximum value of 100",
				intFieldWithMaxValueOnly <= 100);
		int intObjectFieldWithMinAndMaxValue = pojo
				.getIntFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The int field with both min and max value should have a value comprised between",
				intObjectFieldWithMinAndMaxValue >= 0
				&& intObjectFieldWithMinAndMaxValue <= 1000);
		Integer integerObjectFieldWithMinValueOnly = pojo
				.getIntegerObjectFieldWithMinValueOnly();
		Assert.assertNotNull(
				"The integer field with minimum value only should not be null!",
				integerObjectFieldWithMinValueOnly);
		Assert.assertTrue(
				"The integer field with minimum value only should have a minimum value greater or equal to zero!",
				integerObjectFieldWithMinValueOnly.intValue() >= 0);
		Integer integerObjectFieldWithMaxValueOnly = pojo
				.getIntegerObjectFieldWithMaxValueOnly();
		Assert.assertNotNull(
				"The integer field with maximum value only should not be null!",
				integerObjectFieldWithMaxValueOnly);
		Assert.assertTrue(
				"The integer field with maximum value only should have a maximum value of 100",
				integerObjectFieldWithMaxValueOnly.intValue() <= 100);
		Integer integerObjectFieldWithMinAndMaxValue = pojo
				.getIntegerObjectFieldWithMinAndMaxValue();
		Assert.assertNotNull(
				"The integer field with minimum and maximum value should not be null!",
				integerObjectFieldWithMinAndMaxValue);
		Assert.assertTrue(
				"The integer field with minimum and maximum value should have value comprised between 0 and 1000",
				integerObjectFieldWithMinAndMaxValue.intValue() >= 0
				&& integerObjectFieldWithMinAndMaxValue.intValue() <= 1000);

		int intFieldWithPreciseValue = pojo.getIntFieldWithPreciseValue();
		Assert.assertTrue(
				"The integer field with precise value must have a value of: "
						+ PodamTestConstants.INTEGER_PRECISE_VALUE,
						intFieldWithPreciseValue == Integer
						.valueOf(PodamTestConstants.INTEGER_PRECISE_VALUE));

		Integer integerObjectFieldWithPreciseValue = pojo
				.getIntegerObjectFieldWithPreciseValue();
		Assert.assertNotNull(
				"The integer object field with precise value cannot be null!",
				integerObjectFieldWithPreciseValue);
		Assert.assertTrue(
				"The integer object field with precise value should have a value of "
						+ PodamTestConstants.INTEGER_PRECISE_VALUE,
						integerObjectFieldWithPreciseValue.intValue() == Integer
						.valueOf(PodamTestConstants.INTEGER_PRECISE_VALUE));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntegerValueAnnotationWithNumberFormatError() {
		factory.manufacturePojo(IntegerValueWithErrorPojo.class);
	}

	@Test
	public void testLongValueAnnotation() {

		LongValuePojo pojo = factory.manufacturePojo(LongValuePojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);
		long longFieldWithMinValueOnly = pojo.getLongFieldWithMinValueOnly();
		Assert.assertTrue(
				"The long field with min value only should have a value >= 0",
				longFieldWithMinValueOnly >= 0);
		long longFieldWithMaxValueOnly = pojo.getLongFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The long field with maximumm value only should have a maximum value of 100",
				longFieldWithMaxValueOnly <= 100);
		long longFieldWithMinAndMaxValue = pojo
				.getLongFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The long field with both min and max value should have a value comprised between 0 and 1000!",
				longFieldWithMinAndMaxValue >= 0
				&& longFieldWithMinAndMaxValue <= 1000);

		Long longObjectFieldWithMinValueOnly = pojo
				.getLongObjectFieldWithMinValueOnly();
		Assert.assertNotNull(
				"The Long Object field with min value only cannot be null!",
				longObjectFieldWithMinValueOnly);
		Assert.assertTrue(
				"The Long Object field with min value only should have a value >= 0",
				longObjectFieldWithMinValueOnly >= 0);

		Long longObjectFieldWithMaxValueOnly = pojo
				.getLongObjectFieldWithMaxValueOnly();
		Assert.assertNotNull(
				"The Long Object field with max value only cannot be null!",
				longObjectFieldWithMaxValueOnly);
		Assert.assertTrue(
				"The Long Object field with max value only should have a value <= 100",
				longObjectFieldWithMaxValueOnly <= 100);

		Long longObjectFieldWithMinAndMaxValue = pojo
				.getLongObjectFieldWithMinAndMaxValue();
		Assert.assertNotNull(
				"The Long Object field with min and max value cannot be null!",
				longObjectFieldWithMinAndMaxValue);
		Assert.assertTrue(
				"The Long object field with min and max value should have a value comprised between 0 and 1000",
				longObjectFieldWithMinAndMaxValue >= 0L
				&& longObjectFieldWithMinAndMaxValue <= 1000L);

		long longFieldWithPreciseValue = pojo.getLongFieldWithPreciseValue();
		Assert.assertTrue(
				"The long field with precise value must have a value of "
						+ PodamTestConstants.LONG_PRECISE_VALUE,
						longFieldWithPreciseValue == Long
						.valueOf(PodamTestConstants.LONG_PRECISE_VALUE));

		Long longObjectFieldWithPreciseValue = pojo
				.getLongObjectFieldWithPreciseValue();
		Assert.assertNotNull(
				"The long object with precise value should not be null!",
				longObjectFieldWithPreciseValue);
		Assert.assertTrue(
				"The long object field with precise value must have a value of "
						+ PodamTestConstants.LONG_PRECISE_VALUE,
						longObjectFieldWithPreciseValue.longValue() == Long.valueOf(
								PodamTestConstants.LONG_PRECISE_VALUE).longValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testLongValueAnnotationWithNumberFormatException() {
		factory.manufacturePojo(LongValueWithErrorPojo.class);
	}

	@Test
	public void testByteValueAnnotation() {

		ByteValuePojo pojo = factory.manufacturePojo(ByteValuePojo.class);
		Assert.assertNotNull("The Pojo cannot be null!", pojo);
		byte byteFieldWithMinValueOnly = pojo.getByteFieldWithMinValueOnly();
		Assert.assertTrue(
				"The byte field with min value only should have a minimum value of zero!",
				byteFieldWithMinValueOnly >= PodamTestConstants.NUMBER_INT_MIN_VALUE);
		byte byteFieldWithMaxValueOnly = pojo.getByteFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The byte field value cannot be greater than: "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						byteFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);
		byte byteFieldWithMinAndMaxValue = pojo
				.getByteFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The byte field value must be between: "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						byteFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_INT_MIN_VALUE
						&& byteFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);
		Byte byteObjectFieldWithMinValueOnly = pojo
				.getByteObjectFieldWithMinValueOnly();
		Assert.assertNotNull(
				"The byte object with min value only cannot be null!",
				byteObjectFieldWithMinValueOnly);
		Assert.assertTrue(
				"The byte object value must be greate or equal than: "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE,
						byteObjectFieldWithMinValueOnly >= PodamTestConstants.NUMBER_INT_MIN_VALUE);

		Byte byteObjectFieldWithMaxValueOnly = pojo
				.getByteObjectFieldWithMaxValueOnly();
		Assert.assertNotNull("The byte object field cannot be null",
				byteObjectFieldWithMaxValueOnly);
		Assert.assertTrue(
				"The byte object field must have a value less or equal to  "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						byteObjectFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		Byte byteObjectFieldWithMinAndMaxValue = pojo
				.getByteObjectFieldWithMinAndMaxValue();
		Assert.assertNotNull("The byte object must not be null!",
				byteObjectFieldWithMinAndMaxValue);
		Assert.assertTrue(
				"The byte object must have a value between: "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_INT_MAX_VALUE,
						byteObjectFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_INT_MIN_VALUE
						&& byteObjectFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_INT_MAX_VALUE);
		byte byteFieldWithPreciseValue = pojo.getByteFieldWithPreciseValue();
		Assert.assertTrue("The byte with precise value should have value: "
				+ PodamTestConstants.BYTE_PRECISE_VALUE,
				byteFieldWithPreciseValue == Byte
				.valueOf(PodamTestConstants.BYTE_PRECISE_VALUE));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testByteAnnotationWithNumberFormatError() {
		factory.manufacturePojo(ByteValueWithErrorPojo.class);
	}

	@Test
	public void testShortValueAnnotation() {

		ShortValuePojo pojo = factory.manufacturePojo(ShortValuePojo.class);
		Assert.assertNotNull("The Pojo cannot be null!", pojo);

		short shortFieldWithMinValueOnly = pojo.getShortFieldWithMinValueOnly();
		Assert.assertTrue(
				"The short attribute with min value only should have a value greater than "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE,
						shortFieldWithMinValueOnly >= PodamTestConstants.NUMBER_INT_MIN_VALUE);

		short shortFieldWithMaxValueOnly = pojo.getShortFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The short attribute with max value only should have a value less than: "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						shortFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		short shortFieldWithMinAndMaxValue = pojo
				.getShortFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The short field with min and max values should have a value beetween "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_INT_MAX_VALUE,
						shortFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_INT_MIN_VALUE
						&& shortFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		Short shortObjectFieldWithMinValueOnly = pojo
				.getShortObjectFieldWithMinValueOnly();
		Assert.assertNotNull(
				"The short object field with min value only should not be null!",
				shortObjectFieldWithMinValueOnly);
		Assert.assertTrue(
				"The short object attribute with min value only should have a value greater than "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE,
						shortObjectFieldWithMinValueOnly >= PodamTestConstants.NUMBER_INT_MIN_VALUE);

		Short shortObjectFieldWithMaxValueOnly = pojo
				.getShortObjectFieldWithMaxValueOnly();
		Assert.assertNotNull(
				"The short object field with max value only should not be null!",
				shortObjectFieldWithMaxValueOnly);
		Assert.assertTrue(
				"The short object attribute with max value only should have a value less than: "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						shortObjectFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		Short shortObjectFieldWithMinAndMaxValue = pojo
				.getShortObjectFieldWithMinAndMaxValue();
		Assert.assertNotNull(
				"The short object field with max value only should not be null!",
				shortObjectFieldWithMinAndMaxValue);
		Assert.assertTrue(
				"The short object field with min and max values should have a value beetween "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						shortObjectFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_INT_MIN_VALUE
						&& shortObjectFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		short shortFieldWithPreciseValue = pojo.getShortFieldWithPreciseValue();
		Assert.assertTrue(
				"The short attribute with precise value should have a value of "
						+ PodamTestConstants.SHORT_PRECISE_VALUE
						+ " but instead it had a value of "
						+ shortFieldWithPreciseValue,
						shortFieldWithPreciseValue == Short
						.valueOf(PodamTestConstants.SHORT_PRECISE_VALUE));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testShortValueAnnotationWithNumberFormatException() {
		factory.manufacturePojo(ShortValueWithErrorPojo.class);
	}

	@Test
	public void testCharacterValueAnnotation() {

		CharValuePojo pojo = factory.manufacturePojo(CharValuePojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		char charFieldWithMinValueOnly = pojo.getCharFieldWithMinValueOnly();
		Assert.assertTrue(
				"The char attribute with min value only should have a value greater than "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE,
						charFieldWithMinValueOnly >= PodamTestConstants.NUMBER_INT_MIN_VALUE);

		char charFieldWithMaxValueOnly = pojo.getCharFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The char attribute with max value only should have a value less or equal than "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						charFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		char charFieldWithMinAndMaxValue = pojo
				.getCharFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The char attribute with min and max value must have a value between "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						charFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_INT_MIN_VALUE
						&& charFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		Character charObjectFieldWithMinValueOnly = pojo
				.getCharObjectFieldWithMinValueOnly();
		Assert.assertNotNull(
				"The char object attribute with min value only  cannot be null!",
				charObjectFieldWithMinValueOnly);
		Assert.assertTrue(
				"The char object attribute with min value only should have a value greater than "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE,
						charObjectFieldWithMinValueOnly >= PodamTestConstants.NUMBER_INT_MIN_VALUE);

		Character charObjectFieldWithMaxValueOnly = pojo
				.getCharObjectFieldWithMaxValueOnly();
		Assert.assertNotNull(
				"The char object attribute with max value only cannot be null!",
				charObjectFieldWithMaxValueOnly);
		Assert.assertTrue(
				"The char object attribute with max value only should have a value less or equal than "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						charObjectFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		Character charObjectFieldWithMinAndMaxValue = pojo
				.getCharObjectFieldWithMinAndMaxValue();
		Assert.assertNotNull(
				"The char object attribute with min and max value cannot be null!",
				charObjectFieldWithMinAndMaxValue);
		Assert.assertTrue(
				"The char object attribute with min and max value must have a value between "
						+ PodamTestConstants.NUMBER_INT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_INT_ONE_HUNDRED,
						charObjectFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_INT_MIN_VALUE
						&& charObjectFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_INT_ONE_HUNDRED);

		char charFieldWithPreciseValue = pojo.getCharFieldWithPreciseValue();
		Assert.assertTrue(
				"The character field with precise value should have a value of "
						+ PodamTestConstants.CHAR_PRECISE_VALUE,
						charFieldWithPreciseValue == PodamTestConstants.CHAR_PRECISE_VALUE);

		char charFieldWithBlankInPreciseValue = pojo
				.getCharFieldWithBlankInPreciseValue();

		Assert.assertTrue(
				"The value for the char field with an empty char in the precise value and no other annotation attributes should be zero",
				charFieldWithBlankInPreciseValue == 0);
	}

	@Test
	public void testBooleanValueAnnotation() {

		BooleanValuePojo pojo = factory.manufacturePojo(BooleanValuePojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		boolean boolDefaultToTrue = pojo.isBoolDefaultToTrue();
		Assert.assertTrue(
				"The boolean attribute forced to true should be true!",
				boolDefaultToTrue);

		boolean boolDefaultToFalse = pojo.isBoolDefaultToFalse();
		Assert.assertFalse(
				"The boolean attribute forced to false should be false!",
				boolDefaultToFalse);

		Boolean boolObjectDefaultToFalse = pojo.getBoolObjectDefaultToFalse();
		Assert.assertNotNull(
				"The boolean object forced to false should not be null!",
				boolObjectDefaultToFalse);
		Assert.assertFalse(
				"The boolean object forced to false should have a value of false!",
				boolObjectDefaultToFalse);

		Boolean boolObjectDefaultToTrue = pojo.getBoolObjectDefaultToTrue();
		Assert.assertNotNull(
				"The boolean object forced to true should not be null!",
				boolObjectDefaultToTrue);
		Assert.assertTrue(
				"The boolean object forced to true should have a value of true!",
				boolObjectDefaultToTrue);

	}

	@Test
	public void testFloatValueAnnotation() {

		FloatValuePojo pojo = factory.manufacturePojo(FloatValuePojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		float floatFieldWithMinValueOnly = pojo.getFloatFieldWithMinValueOnly();
		Assert.assertTrue(
				"The float field with min value only must have value greater than "
						+ PodamTestConstants.NUMBER_FLOAT_MIN_VALUE,
						floatFieldWithMinValueOnly >= PodamTestConstants.NUMBER_FLOAT_MIN_VALUE);

		float floatFieldWithMaxValueOnly = pojo.getFloatFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The float field with max value only can only have a value less or equal than "
						+ PodamTestConstants.NUMBER_FLOAT_ONE_HUNDRED,
						floatFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_FLOAT_ONE_HUNDRED);

		float floatFieldWithMinAndMaxValue = pojo
				.getFloatFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The float field with min and max value must have a value between "
						+ PodamTestConstants.NUMBER_FLOAT_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_FLOAT_MAX_VALUE,
						floatFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_FLOAT_MIN_VALUE
						&& floatFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_FLOAT_MAX_VALUE);

		Float floatObjectFieldWithMinValueOnly = pojo
				.getFloatObjectFieldWithMinValueOnly();
		Assert.assertNotNull(
				"The float object attribute with min value only cannot be null!",
				floatObjectFieldWithMinValueOnly);
		Assert.assertTrue(
				"The float object attribute with min value only must have a value greater or equal than "
						+ PodamTestConstants.NUMBER_FLOAT_MIN_VALUE,
						floatObjectFieldWithMinValueOnly >= PodamTestConstants.NUMBER_FLOAT_MIN_VALUE);

		Float floatObjectFieldWithMaxValueOnly = pojo
				.getFloatObjectFieldWithMaxValueOnly();
		Assert.assertNotNull(
				"The float object attribute with max value only cannot be null!",
				floatObjectFieldWithMaxValueOnly);
		Assert.assertTrue(
				"The float object attribute with max value only must have a value less than or equal to "
						+ PodamTestConstants.NUMBER_FLOAT_ONE_HUNDRED,
						floatObjectFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_FLOAT_ONE_HUNDRED);

		Float floatObjectFieldWithMinAndMaxValue = pojo
				.getFloatObjectFieldWithMinAndMaxValue();
		Assert.assertNotNull(
				"The float object attribute with min and max value cannot be null!",
				floatObjectFieldWithMinAndMaxValue);
		Assert.assertTrue(
				"The float object attribute with min and max value only must have a value between "
						+ PodamTestConstants.NUMBER_FLOAT_MIN_VALUE
						+ " and "
						+ PodamTestConstants.NUMBER_FLOAT_MAX_VALUE,
						floatObjectFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_FLOAT_MIN_VALUE
						&& floatObjectFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_FLOAT_MAX_VALUE);

		float floatFieldWithPreciseValue = pojo.getFloatFieldWithPreciseValue();
		Assert.assertTrue(
				"The float field with precise value should have a value of "
						+ PodamTestConstants.FLOAT_PRECISE_VALUE,
						floatFieldWithPreciseValue == Float
						.valueOf(PodamTestConstants.FLOAT_PRECISE_VALUE));

		Float floatObjectFieldWithPreciseValue = pojo
				.getFloatObjectFieldWithPreciseValue();
		Assert.assertNotNull(
				"The float object field with precise value cannot be null!",
				floatObjectFieldWithPreciseValue);
		Assert.assertTrue(
				"The float object field with precise value should have a value of "
						+ PodamTestConstants.FLOAT_PRECISE_VALUE,
						floatObjectFieldWithPreciseValue.floatValue() == Float.valueOf(
								PodamTestConstants.FLOAT_PRECISE_VALUE).floatValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testFloatValueAnnotationWithNumberFormatError() {
		factory.manufacturePojo(FloatValueWithErrorPojo.class);
	}

	@Test
	public void testDoubleValueAnnotation() {

		DoubleValuePojo pojo = factory.manufacturePojo(DoubleValuePojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		double doubleFieldWithMinValueOnly = pojo
				.getDoubleFieldWithMinValueOnly();
		Assert.assertTrue(
				"The double attribute with min value only must have a value greater than "
						+ PodamTestConstants.NUMBER_DOUBLE_MIN_VALUE,
						doubleFieldWithMinValueOnly >= PodamTestConstants.NUMBER_DOUBLE_MIN_VALUE);

		double doubleFieldWithMaxValueOnly = pojo
				.getDoubleFieldWithMaxValueOnly();
		Assert.assertTrue(
				"The double attribute with max value only must have a value less or equal to "
						+ PodamTestConstants.NUMBER_DOUBLE_ONE_HUNDRED,
						doubleFieldWithMaxValueOnly <= PodamTestConstants.NUMBER_DOUBLE_ONE_HUNDRED);

		double doubleFieldWithMinAndMaxValue = pojo
				.getDoubleFieldWithMinAndMaxValue();
		Assert.assertTrue(
				"The double attribute with min and mx value must have a value between "
						+ PodamTestConstants.NUMBER_DOUBLE_MIN_VALUE + " and "
						+ PodamTestConstants.NUMBER_DOUBLE_MAX_VALUE,
						doubleFieldWithMinAndMaxValue >= PodamTestConstants.NUMBER_DOUBLE_MIN_VALUE
						&& doubleFieldWithMinAndMaxValue <= PodamTestConstants.NUMBER_DOUBLE_MAX_VALUE);

		double doubleFieldWithPreciseValue = pojo
				.getDoubleFieldWithPreciseValue();
		Assert.assertTrue(
				"The double field with precise value should have a value of: "
						+ PodamTestConstants.DOUBLE_PRECISE_VALUE,
						doubleFieldWithPreciseValue == Double
						.valueOf(PodamTestConstants.DOUBLE_PRECISE_VALUE));

		Double doubleObjectFieldWithPreciseValue = pojo
				.getDoubleObjectFieldWithPreciseValue();
		Assert.assertNotNull(
				"The double object field with precise value cannot be null!",
				doubleObjectFieldWithPreciseValue);
		Assert.assertTrue(
				"The double object field with precise value should have a value of: "
						+ PodamTestConstants.DOUBLE_PRECISE_VALUE,
						doubleObjectFieldWithPreciseValue.doubleValue() == Double
						.valueOf(PodamTestConstants.DOUBLE_PRECISE_VALUE)
						.doubleValue());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoubleValueAnnotationWithError() {

		factory.manufacturePojo(DoubleValueWithErrorPojo.class);

	}

	@Test
	public void testStringValueAnnotation() {

		StringValuePojo pojo = factory.manufacturePojo(StringValuePojo.class);
		String twentyLengthString = pojo.getTwentyLengthString();
		Assert.assertNotNull("The twentyLengthString cannot be null!",
				twentyLengthString);
		Assert.assertTrue(
				"The twenty length string must have a length of "
						+ PodamTestConstants.STR_ANNOTATION_TWENTY_LENGTH
						+ "! but it did have a length of "
						+ twentyLengthString.length(),
						twentyLengthString.length() == PodamTestConstants.STR_ANNOTATION_TWENTY_LENGTH);

		String preciseValueString = pojo.getPreciseValueString();
		Assert.assertNotNull("The precise value string cannot be null!",
				preciseValueString);
		Assert.assertEquals(
				"The expected and actual String values don't match",
				PodamTestConstants.STR_ANNOTATION_PRECISE_VALUE,
				preciseValueString);

	}

	@Test
	public void testCollectionAnnotation() {

		CollectionAnnotationPojo pojo = factory
				.manufacturePojo(CollectionAnnotationPojo.class);
		Assert.assertNotNull("The pojo cannot be null!", pojo);

		List<String> strList = pojo.getStrList();
		Assert.assertNotNull("The string list cannot be null!", strList);
		Assert.assertFalse("The string list cannot be empty!",
				strList.isEmpty());
		Assert.assertTrue(
				"The string list must have "
						+ PodamTestConstants.ANNOTATION_COLLECTION_NBR_ELEMENTS
						+ " elements but it had only " + strList.size(),
						strList.size() == PodamTestConstants.ANNOTATION_COLLECTION_NBR_ELEMENTS);

		String[] strArray = pojo.getStrArray();
		Assert.assertNotNull("The array cannot be null!", strArray);
		Assert.assertFalse("The array cannot be empty!", strArray.length == 0);
		Assert.assertTrue(
				"The number of elements in the array (" + strArray.length
				+ ") does not match "
				+ PodamTestConstants.ANNOTATION_COLLECTION_NBR_ELEMENTS,
				strArray.length == PodamTestConstants.ANNOTATION_COLLECTION_NBR_ELEMENTS);

		Map<String, String> stringMap = pojo.getStringMap();
		Assert.assertNotNull("The map cannot be null!", stringMap);
		Assert.assertFalse("The map of strings cannot be empty!",
				stringMap.isEmpty());
		Assert.assertTrue(
				"The number of elements in the map (" + stringMap.size()
				+ ") does not match "
				+ PodamTestConstants.ANNOTATION_COLLECTION_NBR_ELEMENTS,
				stringMap.size() == PodamTestConstants.ANNOTATION_COLLECTION_NBR_ELEMENTS);

	}

	@Test
	public void testPodamStrategyValueAnnotation() {

		PodamStrategyPojo pojo = factory
				.manufacturePojo(PodamStrategyPojo.class);
		Assert.assertNotNull("The post code pojo cannot be null!", pojo);

		String postCode = pojo.getPostCode();
		Assert.assertNotNull("The post code cannot be null!", postCode);
		Assert.assertEquals("The post code does not match the expected value",
				PodamTestConstants.POST_CODE, postCode);

		String postCode2 = pojo.getPostCode2();
		Assert.assertNotNull("The post code 2 cannot be null!", postCode2);
		Assert.assertEquals("The post code 2 does not match the expected value",
				PodamTestConstants.POST_CODE, postCode2);

		String postCode3 = pojo.getPostCode3();
		Assert.assertNotNull("The post code 3 cannot be null!", postCode3);
		Assert.assertEquals("The post code 3 does not match the expected value",
				PodamTestConstants.POST_CODE, postCode3);

		Calendar expectedBirthday = PodamTestUtils.getMyBirthday();

		Calendar myBirthday = pojo.getMyBirthday();

		Assert.assertNotNull("byte array manufacturing failed",
				pojo.getByteData());
		Assert.assertEquals("byte array wrong length",
				ByteArrayStrategy.LENGTH, pojo.getByteData().length);

		Assert.assertEquals(
				"The expected and actual calendar objects are not the same",
				expectedBirthday.getTime(), myBirthday.getTime());

		List<Calendar> myBirthdays = pojo.getMyBirthdays();
		Assert.assertNotNull("The birthdays collection cannot be null!",
				myBirthdays);
		Assert.assertFalse("The birthdays collection cannot be empty!",
				myBirthdays.isEmpty());

		for (Calendar birthday : myBirthdays) {
			Assert.assertEquals(
					"The expected birthday element does not match the actual",
					expectedBirthday.getTime(), birthday.getTime());
		}

		Calendar[] myBirthdaysArray = pojo.getMyBirthdaysArray();
		Assert.assertNotNull("The birthdays array cannot be null!",
				myBirthdaysArray);
		Assert.assertFalse("The birthdays array cannot be empty!",
				myBirthdaysArray.length == 0);

		for (Calendar birthday : myBirthdaysArray) {
			Assert.assertEquals(
					"The expected birthday element does not match the actual",
					expectedBirthday.getTime(), birthday.getTime());
		}

		List<Object> objectList = pojo.getObjectList();
		Assert.assertNotNull("The list of objects cannot be null!", objectList);
		Assert.assertFalse("The list of objects cannot be empty!",
				objectList.isEmpty());

		Object[] myObjectArray = pojo.getMyObjectArray();
		Assert.assertNotNull("The array of objects cannot be null!",
				myObjectArray);
		Assert.assertTrue("The array of objects cannot be empty",
				myObjectArray.length > 0);

		@SuppressWarnings("rawtypes")
		List nonGenericObjectList = pojo.getNonGenericObjectList();
		Assert.assertNotNull("The non generified object list cannot be null!",
				nonGenericObjectList);
		Assert.assertFalse("The non generified object list cannot be empty!",
				nonGenericObjectList.isEmpty());

		Map<String, Calendar> myBirthdaysMap = pojo.getMyBirthdaysMap();
		Assert.assertNotNull("The birthday map cannot be null!", myBirthdaysMap);
		Assert.assertFalse("The birthday map cannot be empty!",
				myBirthdaysMap.isEmpty());

		Set<String> keySet = myBirthdaysMap.keySet();
		for (String key : keySet) {

			Assert.assertEquals("The map element is not my birthday!",
					expectedBirthday.getTime(), myBirthdaysMap.get(key)
					.getTime());

		}

	}

	@Test(expected = IllegalArgumentException.class)
	public void testStringPojoWithWrongTypeForAnnotationStrategy() {

		factory.manufacturePojo(StringWithWrongStrategyTypePojo.class);

	}

}
