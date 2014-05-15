package com.licong.filemanager.test;

import static com.licong.filemanager.FileManager.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import org.junit.Test;

import com.licong.filemanager.SingleFileSearchResult;

public class FileManagerTest {

	private File source = new File("D:\\src.zip"); 
	private File target = new File("D:\\target.zip"); 
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
		long start = System.currentTimeMillis();
		assertEquals(SUCCESS, copy(source, target));
		long end = System.currentTimeMillis();
		System.out.println(end - start);
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
	
	@Test
	public void TestSearch() {
		SingleFileSearchResult result = new SingleFileSearchResult();
		assertEquals(SUCCESS, search(new File("I:\\h.log"), "test", result));
		System.out.println(result.toString());
	}
	
	@Test
	public void TestTruncate() {
		File file = new File("I:\\h.log");
		System.out.println(file.length());
		assertEquals(SUCCESS, truncate(file, 1L));
	}
}
