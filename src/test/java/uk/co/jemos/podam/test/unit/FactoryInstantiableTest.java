package uk.co.jemos.podam.test.unit;

import java.util.Date;
import java.util.TimeZone;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import uk.co.jemos.podam.api.RandomDataProviderStrategy;
import uk.co.jemos.podam.test.dto.FactoryInstantiablePojo;

/**
 * @author daivanov
 *
 */
public class FactoryInstantiableTest {

	/** The default factory */
	private static final PodamFactory factory = new PodamFactoryImpl();

	/** The default data strategy */
	private static final RandomDataProviderStrategy strategy
			= (RandomDataProviderStrategy) factory.getStrategy();

	@Test
	public void testFactoryInstantiation() {
		TimeZone pojo = factory.manufacturePojo(TimeZone.class);
		Assert.assertNotNull(pojo);
	}

	@Test
	public void testFactoryInstantiationWithGenerics() {
		FactoryInstantiablePojo<?> pojo = factory.manufacturePojo(
				FactoryInstantiablePojo.class, Date.class);
		Assert.assertNotNull(pojo);

		Object value = pojo.getTypedValue();
		Assert.assertNotNull(value);
		Assert.assertEquals(Date.class, value.getClass());
	}

	@Test
	public void testAbstractClassInstantiationWithFactory() {
		strategy.addFactory(Transformer.class, TransformerFactory.class);
		Transformer pojo = factory.manufacturePojo(Transformer.class);
		Assert.assertNotNull(
				"The abstract pojo should not be null since a factory has been specified",
				pojo);
		strategy.removeFactory(Transformer.class);
	}

	@Test
	public void testAbstractClassInstantiationWithStaticFactory() {
		strategy.addFactory(SOAPMessage.class, PowerMockito.class);
		SOAPMessage pojo = factory.manufacturePojo(SOAPMessage.class);
		Assert.assertNotNull(
				"The abstract pojo should not be null since a factory has been specified",
				pojo);
		strategy.removeFactory(SOAPMessage.class);
	}

}
