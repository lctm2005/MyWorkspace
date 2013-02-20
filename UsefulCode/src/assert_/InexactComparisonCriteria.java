package assert_;

/**
 * 非精准比较，允许误差
 * 
 * @author James-li
 *
 * 2013-2-19
 */
public class InexactComparisonCriteria extends ComparisonCriteria {
	 public Object fDelta;

	    public InexactComparisonCriteria(double delta) {
	        fDelta = delta;
	    }

	    public InexactComparisonCriteria(float delta) {
	        fDelta = delta;
	    }

	    @Override
	    protected void assertElementsEqual(Object expected, Object actual) {
	        if (expected instanceof Double) {
	            Assert.assertEquals((Double) expected, (Double) actual, (Double) fDelta);
	        } else {
	            Assert.assertEquals((Float) expected, (Float) actual, (Float) fDelta);
	        }
	    }
}
