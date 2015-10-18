package uk.co.jemos.podam.test.unit;

import org.junit.Assert;
import org.junit.Test;

import uk.co.jemos.podam.common.PodamConstants;

import java.lang.reflect.Constructor;

/**
 * Created by tedonema on 06/05/2015.
 */
public class ConstantsUnitTest {

    @Test
    public void testNonInstantiableClass() {

        Constructor<?>[] constructors = PodamConstants.class.getDeclaredConstructors();
        Assert.assertNotNull(constructors);
        Assert.assertTrue(constructors.length == 1);
        constructors[0].setAccessible(true);
        try {
            constructors[0].newInstance();
            Assert.fail();
        } catch (Throwable e) {
            //OK
        }

    }
}
