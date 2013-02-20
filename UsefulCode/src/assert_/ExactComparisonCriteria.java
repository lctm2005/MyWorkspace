package assert_;

/**
 * 精确比较
 * 
 * @author James-li
 *
 * 2013-2-19
 */
public class ExactComparisonCriteria extends ComparisonCriteria {

	@Override
	protected void assertElementsEqual(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

}
