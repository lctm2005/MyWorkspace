package enum_;

public class Test {

	public static void main(String[] args) {
		
		//Test ConstantsByEnum
		System.out.println( ConstantsByEnum.APPLE.getIndex());
		System.out.println( ConstantsByEnum.APPLE.getDesc());
		System.out.println( ConstantsByEnum.BANANA.getIndex());
		System.out.println( ConstantsByEnum.PEAR.getDesc());
		
		//Test SingletonByEnum
		SingletonByEnum.INSTANCE.count();
		SingletonByEnum.INSTANCE.count();
	}

}
