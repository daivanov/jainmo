package uk.co.jemos.podam.test.dto.annotations;

import uk.co.jemos.podam.annotation.PodamStringValue;
import uk.co.jemos.podam.test.utils.TestConstants;

/**
 * POJO to with annotated field and field setter
 *
 * @author daivanov
 *
 */
public class AnnotatedFieldAndSetterPojo {

	@PodamStringValue(strValue = TestConstants.POST_CODE)
	private String postCode;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(@PodamStringValue(strValue = TestConstants.POST_CODE) String postCode) {
		this.postCode = postCode;
	}


}
