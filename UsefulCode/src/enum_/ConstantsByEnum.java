package enum_;

/**
 * 使用枚举代替int常量
 * 
 * @author James-li
 *
 * 2013-2-13
 */
public enum ConstantsByEnum {

	APPLE(0,"苹果"),
	BANANA(1,"香蕉"),
	PEAR(2,"梨");
	
	private final int index;
	private final String desc;
	
	ConstantsByEnum(int index, String desc) {
		this.index = index;
		this.desc = desc;
	}
	
	public int getIndex() {
		return index;
	}

	public String getDesc() {
		return desc;
	}
	
}
