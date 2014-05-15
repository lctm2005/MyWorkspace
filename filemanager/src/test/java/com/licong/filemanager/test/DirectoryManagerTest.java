package com.licong.filemanager.test;

import static com.licong.filemanager.DirectoryManager.copy;
import static com.licong.filemanager.DirectoryManager.create;
import static com.licong.filemanager.DirectoryManager.cut;
import static com.licong.filemanager.DirectoryManager.delete;
import static com.licong.filemanager.DirectoryManager.rename;
import static com.licong.filemanager.DirectoryManager.search;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.licong.filemanager.FileSearchResult;

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
	
	@Test
	public void testSearch() {
		FileSearchResult result = new FileSearchResult();
		assertEquals(SUCCESS, search(new File("I:\\h\\"), "test", result));
		System.out.println(result);
	}
}
