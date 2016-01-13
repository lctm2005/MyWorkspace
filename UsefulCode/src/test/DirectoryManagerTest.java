import static file.DirectoryManager.*;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

/**
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
	
	@Test
	public void testRename() {
		assertEquals(SUCCESS, rename(new File("D:\\src"),"src2"));
	}
}
