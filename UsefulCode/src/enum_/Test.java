package enum_;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		
		//Test ConstantsByEnum
		System.out.println( FruitType.APPLE.getIndex());
		System.out.println( FruitType.APPLE.getDesc());
		System.out.println( FruitType.BANANA.getIndex());
		System.out.println( FruitType.PEAR.getDesc());
		
		//Test SingletonByEnum
		SingletonByEnum.INSTANCE.count();
		SingletonByEnum.INSTANCE.count();
		
		//Test EnumSet
		testEnumSet(EnumSet.of(FruitType.APPLE,FruitType.APPLE,FruitType.BANANA));
		
		//EnumMap
		EnumMap<FruitType,Set<FruitType>> map = new EnumMap<FruitType,Set<FruitType>>(FruitType.class);
		map.values();
	}
	
	public static void testEnumSet(Set<FruitType> types) {
		Iterator<FruitType> it = types.iterator();
		FruitType type = null;
		int sum = 0;
		while(it.hasNext()) {
			type = it.next();
			System.out.println(type.getDesc());
			sum += type.getIndex();
		}
		System.out.println(sum);
	}
	

}
