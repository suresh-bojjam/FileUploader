/**
 * suresh bojjam
 * 18-Sep-2017
 */
package com.content.filerepo;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.content.storage.StorageFileNotFoundException;
import com.content.storage.StorageService;

@RestController
@RequestMapping("/v1/repo")
public class FileUploadController {	
	
	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/msg")
	public String hello() {
		return "FileUploader: Hello..!";
	}
	
	@GetMapping("/files/{filename}")
	@ResponseBody
	public FileSystemResource serveFile(@PathVariable String filename) throws IOException {
		
		Resource file = storageService.loadAsResource(filename);
		return new FileSystemResource(file.getFile());
	}

	@PostMapping("/files/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {

		storageService.store(file);

		return "File has been uploaded Succesfully";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}