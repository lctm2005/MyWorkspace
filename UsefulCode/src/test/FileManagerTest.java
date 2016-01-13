import static file.FileManager.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import org.junit.Test;

public class FileManagerTest {

	private File source = new File("D:\\src.txt"); 
	private File target = new File("D:\\target.txt"); 
	private static final boolean SUCCESS = true;
	
	@Test
	public void testCreateFile() {
		assertEquals(SUCCESS, create(source));
	}
	
	@Test
	public void testAppendFileString() {
		assertEquals(SUCCESS, append(source, "append"));
	}
	
	@Test
	public void testCopy() {
		assertEquals(SUCCESS, copy(source, target));
	}

	@Test
	public void testDelete() {
		assertEquals(SUCCESS, delete(target));
	}
	
	@Test
	public void testCut() {
		assertEquals(SUCCESS, cut(source,target));
	}

	@Test
	public void testCreateFileInputStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(target);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(SUCCESS, create(source, fis));
		delete(source);
	}

	@Test
	public void testCreateFileString() {
		assertEquals(SUCCESS, create(source, "string"));
		delete(source);
	}

	@Test
	public void testCreateFileByteArray() {
		assertEquals(SUCCESS, create(source, "bytes".getBytes()));
	}
	
	@Test
	public void testCoverFileInputStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(target);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(SUCCESS, cover(source, fis));
	}

	@Test
	public void testCoverFileString() {
		assertEquals(SUCCESS, cover(source, "string"));
	}

	@Test
	public void testCoverFileByteArray() {
		assertEquals(SUCCESS, cover(source, "bytes".getBytes()));
	}

	@Test
	public void testAppendFileByteArray() {
		assertEquals(SUCCESS, append(source, "append_bytes".getBytes()));
	}

	@Test
	public void testAppendFileInputStream() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(target);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(SUCCESS, append(source, fis));
	}

	@Test
	public void testRename() {
		assertEquals(SUCCESS, rename(source, "src2"));
	}
}
