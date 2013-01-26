package com.github.javarch.support;
/*
 * 	 @(#)ZipFileCreator.java	0.9 10/11/22
 * 
 *	Copyright (c) 2010 Felipe Priuli
 *
 *	This file is part of OpenSutils-Br4J.
 *
 *	OpenSutils-Br4J is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, any later version.
 *
 *	OpenSutils-Br4J is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public License
 *	along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <code>ZipFileCreator</code>
 * Class to compress files in zip format.
 * @author Felipe Priuli
 * @version 0.9
 */
public class ZipFileCreator {
	
	private FileOutputStream out;
	private ZipOutputStream zipOut;  
	private BufferedInputStream bufferIn;
	private ZipEntry zipEntry;
	private String outputAbsoluteDir;
	
	/**
	 * Constructor
	 * @param outputAbsoluteDir - java.lang.String, absolute directory name to out files
	 * @throws FileNotFoundException
	 */
	public ZipFileCreator(String outputAbsoluteDir) throws FileNotFoundException{
		this.outputAbsoluteDir = outputAbsoluteDir;
		this.out = null;
		this.zipOut = null;
		this.zipEntry = null;
		this.bufferIn = null;
	}
	
	/**
	 * Adds a file to be compressed.
	 * @param in - FileInputStream
	 * @param fileName - Name of file
	 * @throws IOException - If a problem I/O occurs
	 */
	public void addFile(FileInputStream in, String fileName) throws IOException{
		if(!isOpen()){open();};
		
		if( fileName!=null){
			this.bufferIn = new BufferedInputStream(in);
			this.zipEntry = new ZipEntry(fileName);
			this.zipOut.putNextEntry(this.zipEntry);
			
			int i;
			while((i = this.bufferIn.read()) != -1) {   
				this.zipOut.write(i);   
			 }   
			in.close();
			this.bufferIn.close();
			this.bufferIn = null;
			this.zipEntry = null;
		}
	}
	
	/**
	 * Adds a directory to be compressed.
	 * <br><i>Add in version 0.2</i>
	 * @param dir - the directory
	 * @throws IOException - If a problem I/O occurs
	 */
	public void addDirectory(File dir) throws IOException{
		if(dir.isDirectory()){
			
			zipAll(dir.listFiles());

		}else
			throw new IOException("dir not is a directory");
	}
	
	private void zipAll(File[] files) throws FileNotFoundException, IOException{
		for (File file : files) {
			if(file.isDirectory()){
				zipAll(file.listFiles());
			}else{
				addFile(new FileInputStream(file),file.getAbsolutePath());
			}
		}
	}
	
	/**
	 * Compress all files that have been added
	 * @return the file compressed
	 * @throws IOException - If a problem I/O occurs
	 */
	public File compress() throws IOException{
		File file = null;
		if( isOpen() ){
			zipOut.flush();
			file = new File(outputAbsoluteDir);
		}	
		return file;
	}
	
	/**
	 * Close all files that were manipulated to compression.
	 * @throws IOException 
	 */
	public void close() throws IOException{
		this.zipOut.finish();
		this.zipOut.close();
		this.out.close();
	}
	

	public void open() throws IOException {
		this.out = new FileOutputStream(outputAbsoluteDir);
		this.zipOut = new ZipOutputStream(new BufferedOutputStream(this.out));
	}
	/**
	 * Checks if a open
	 */
	public boolean isOpen(){
		if(this.out == null || this.zipOut == null ){
			return false;
		}else{
			return true;
		}
	}
	
}
