package com.github.javarch.jsf;

import java.io.Serializable;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Component;

import com.github.javarch.support.data.FileData;

@Component
public class UploadFileUtils implements Serializable {

	private static final long serialVersionUID = 6006690611341559417L;

	public FileData toFileData(UploadedFile uploadFile){
		if (uploadFile == null ){
			return null;
		}
		String contentType = uploadFile.getContentType();
		String fileName = uploadFile.getFileName();
		byte [] bytes = uploadFile.getContents();
		
		return new FileData(fileName, bytes, contentType);
	}
	
	public FileData toFileData(FileUploadEvent uploadFileEvent){
		return toFileData(uploadFileEvent.getFile());
	}
}
