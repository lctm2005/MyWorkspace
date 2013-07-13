package file.test;

import static file.DirectoryManager.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

/**
 * D:\\src需要手动创建</br>
 * 执行测试用例后，预期结果：</br>
 * 	1、用例通过</br>
 * 2、最终保留D:\\create D:\\target2</br>
 * 3、不存在D:\\src D:\\target</br>
 * @author James-li
 *
 * 2013-7-13
 */
public class DirectoryManagerTest {

	private static final boolean SUCCESS = true;
	
	@Test
	public void testCopy() {
		assertEquals(SUCCESS, copy(new File("D:\\src") , new File("D:\\target")));
	}

	@Test
	public void testCut() {
		assertEquals(SUCCESS, cut(new File("D:\\target") , new File("D:\\target2")));
	}

	@Test
	public void testCreate() {
		assertEquals(SUCCESS, create(new File("D:\\create")));
	}

	@Test
	public void testDelete() {
		assertEquals(SUCCESS, delete(new File("D:\\src")));
	}
}
