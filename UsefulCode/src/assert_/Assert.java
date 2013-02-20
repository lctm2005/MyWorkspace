package assert_;

import util.StringUtils;


/**
 * Assert
 * 
 * @author James-li
 *
 * 2013-2-18
 */
public final class Assert {
	
	private static final boolean IS_SHUTDOWN = false;	//断言开关，如果为false，Assert的所有方法将无效
	
	/**
	 * Private constructor since it is a static only class
	 */
	private Assert() {}
	
	/**
     * 格式化message
     * @param message
     * @param expected
     * @param actual
     * @return
     */
   private  static String format(String message, Object expected, Object actual) {
        StringBuilder formatted = new StringBuilder();
        if (StringUtils.isEmpty(message)) {
            formatted.append(message) ;
        }
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        if (expectedString.equals(actualString)) {
            return formatted
            		.append("expected: ")
            		.append(formatClassAndValue(expected, expectedString))
            		.append(" but was: ")
            		.append(formatClassAndValue(actual, actualString)).toString();
        } else {
            return formatted.append("expected:<")
            		.append(expectedString)
            		.append("> but was:<")
            		.append(actualString)
            		.append(">").toString();
        }
    }

   /**
    * 格式化类和值
    * @param value
    * @param valueString
    * @return
    */
    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return new StringBuilder().append(className).append("<").append(valueString).append(">").toString();
    }
    
	 /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @see AssertionFailtureException
     */
    private static void fail(String message) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (message == null) {
            throw new AssertionFailtureException();
        }
        throw new AssertionFailtureException(message);
    }
    
    /**
     * 判断不相等失败后调用
     * @param message  信息
     * @param actual		实际值
     */
    private static void failEquals(String message, Object actual) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
    	StringBuilder formatted = new StringBuilder();
    	formatted.append(	"Values should be different. ");
        if (message != null) {
        	formatted.append(message).append(",");
        }
        formatted.append("Actual: ").append(actual);
        fail(formatted.toString());
    }
    
    /**
     * 判断相等失败后调用
     * @param message	信息
     * @param expected	期望值
     * @param actual		实际值
     */
    private static  void failNotEquals(String message, Object expected,
            Object actual) {
        fail(format(message, expected, actual));
    }
    
    /**
     * 判断为空失败后调用
     * @param message	信息
     * @param actual		实际值
     */
    private static  void failNotNull(String message, Object actual) {
        StringBuilder formatted = new StringBuilder();
        if (message != null) {
            formatted.append(message);
        }
        fail(formatted.append(" expected null, but was:<").append(actual).append(">").toString());
    }
    
    /**
     * 判断不相同失败后调用
     * @param message 信息
     */
    private static  void failSame(String message) {
    	StringBuilder formatted = new StringBuilder();
        if (message != null) {
        	formatted.append(message);
        }
        fail(formatted.append(" expected not same").toString());
    }

    /**
     * 判断相同失败后调用
     * @param message	信息
     * @param expected	期望值
     * @param actual		实际值
     */
    private static  void failNotSame(String message, Object expected,
            Object actual) {
    	StringBuilder formatted = new StringBuilder();
        if (message != null) {
        	formatted.append(message);
        }
        fail(formatted.append(" expected same:<")
        		.append(expected)
        		.append("> was not:<")
        		.append(actual)
        		.append(">")
        		.toString());
    }
    
    /**
     * 判断期望值与实际值是否相等
     * @param expected 		期望值
     * @param actual			实际值
     * @return
     */
    private static boolean isEqual(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }
        return expected.equals(actual);
    }
    
    private static  boolean doubleIsDifferent(double d1, double d2, double delta) {
        if (Double.compare(d1, d2) == 0) {
            return false;
        }
        if ((Math.abs(d1 - d2) <= delta)) {
            return false;
        }
        return true;
    }

    private static  boolean floatIsDifferent(float f1, float f2, float delta) {
        if (Float.compare(f1, f2) == 0) {
            return false;
        }
        if ((Math.abs(f1 - f2) <= delta)) {
            return false;
        }

        return true;
    }

	 /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionFailtureException} with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param condition condition to be checked
     */
    public static void assertTrue(String message, boolean condition) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (!condition) {
            fail(message);
        }
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionFailtureException} without a message.
     *
     * @param condition condition to be checked
     */
    public static void assertTrue(boolean condition) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        assertTrue(null, condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an
     * {@link AssertionFailtureException} with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param condition condition to be checked
     */
    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an
     * {@link AssertionFailtureException} without a message.
     *
     * @param condition condition to be checked
     */
    public static void assertFalse(boolean condition) {
    	assertTrue(condition);
    }

   

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionFailtureException} is thrown with the given message. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param expected expected value
     * @param actual actual value
     */
    public static void assertEquals(String message, Object expected, Object actual) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (isEqual(expected, actual)) {
            return;
        } else {
            failNotEquals(message, expected, actual);
        }
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionFailtureException} without a message is thrown. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     */
    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link AssertionFailtureException} is thrown with the given message. If
     * <code>unexpected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    public static void assertNotEquals(String message, Object unexpected,
            Object actual) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (isEqual(unexpected, actual)) {
            failEquals(message, actual);
        }
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an
     * {@link AssertionFailtureException} without a message is thrown. If
     * <code>unexpected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    public static void assertNotEquals(Object unexpected, Object actual) {
        assertNotEquals(null, unexpected, actual);
    }

  

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link AssertionFailtureException} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    public static void assertNotEquals(String message, long unexpected, long actual) {
        assertNotEquals(message, (Long) unexpected, (Long) actual);
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an
     * {@link AssertionFailtureException} without a message is thrown.
     *
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    public static void assertNotEquals(long unexpected, long actual) {
        assertNotEquals(null, unexpected, actual);
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta.
     * If they are, an {@link AssertionFailtureException} is thrown with the given
     * message. If the unexpected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertNotEquals(String message, double unexpected,
            double actual, double delta) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (!doubleIsDifferent(unexpected, actual, delta)) {
            failEquals(message, new Double(actual));
        }
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta.
     * If they are, an {@link AssertionFailtureException} is thrown. If the unexpected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertNotEquals(double unexpected, double actual, double delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link AssertionFailtureException} is thrown. If the unexpected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertNotEquals(float unexpected, float actual, float delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta.
     * If they are, an {@link AssertionFailtureException} is thrown with the given
     * message. If the unexpected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertNotEquals(String message, float unexpected,
            float actual, float delta) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (!floatIsDifferent(unexpected, actual, delta)) {
            failEquals(message, new Float(actual));
        }
    }
    
    /**
     * Asserts that two doubles are equal to within a positive delta.
     * If they are not, an {@link AssertionFailtureException} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertEquals(String message, double expected,
            double actual, double delta) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (doubleIsDifferent(expected, actual, delta)) {
            failNotEquals(message, new Double(expected), new Double(actual));
        }
    }

    /**
     * Asserts that two floats are equal to within a positive delta.
     * If they are not, an {@link AssertionFailtureException} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     * <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertEquals(String message, float expected,
            float actual, float delta) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (floatIsDifferent(expected, actual, delta)) {
            failNotEquals(message, new Float(expected), new Float(actual));
        }
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionFailtureException} is thrown.
     *
     * @param expected expected long value.
     * @param actual actual long value
     */
    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionFailtureException} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param expected long expected value.
     * @param actual long actual value
     */
    public static void assertEquals(String message, long expected, long actual) {
        assertEquals(message, (Long) expected, (Long) actual);
    }

    /**
     * Asserts that two doubles are equal to within a positive delta.
     * If they are not, an {@link AssertionFailtureException} is thrown. If the expected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */
    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that two floats are equal to within a positive delta.
     * If they are not, an {@link AssertionFailtureException} is thrown. If the expected
     * value is infinity then the delta value is ignored. NaNs are considered
     * equal: <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     */

    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionFailtureException} is
     * thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param object Object to check or <code>null</code>
     */
    public static void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionFailtureException} is
     * thrown.
     *
     * @param object Object to check or <code>null</code>
     */
    public static void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    /**
     * Asserts that an object is null. If it is not, an {@link AssertionFailtureException}
     * is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param object Object to check or <code>null</code>
     */
    public static void assertNull(String message, Object object) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (object == null) {
            return;
        }
        failNotNull(message, object);
    }

    /**
     * Asserts that an object is null. If it isn't an {@link AssertionFailtureException} is
     * thrown.
     *
     * @param object Object to check or <code>null</code>
     */
    public static void assertNull(Object object) {
        assertNull(null, object);
    }


    /**
     * Asserts that two objects refer to the same object. If they are not, an
     * {@link AssertionFailtureException} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param expected the expected object
     * @param actual the object to compare to <code>expected</code>
     */
    public static void assertSame(String message, Object expected, Object actual) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (expected == actual) {
            return;
        }
        failNotSame(message, expected, actual);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not the
     * same, an {@link AssertionFailtureException} without a message is thrown.
     *
     * @param expected the expected object
     * @param actual the object to compare to <code>expected</code>
     */
    public static void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do
     * refer to the same object, an {@link AssertionFailtureException} is thrown with the
     * given message.
     *
     * @param message the identifying message for the {@link AssertionFailtureException} (<code>null</code>
     * okay)
     * @param unexpected the object you don't expect
     * @param actual the object to compare to <code>unexpected</code>
     */
    public static void assertNotSame(String message, Object unexpected,
            Object actual) {
    	if(IS_SHUTDOWN) {
    		return;
    	}
        if (unexpected == actual) {
            failSame(message);
        }
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do
     * refer to the same object, an {@link AssertionFailtureException} without a message is
     * thrown.
     *
     * @param unexpected the object you don't expect
     * @param actual the object to compare to <code>unexpected</code>
     */
    public static void assertNotSame(Object unexpected, Object actual) {
        assertNotSame(null, unexpected, actual);
    }

}
