package com.github.amanganiello90.swagger.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.amanganiello90.swagger.application.storage.StorageFileNotFoundException;
import com.github.amanganiello90.swagger.application.storage.StorageService;
import com.github.amanganiello90.swagger.application.util.SwaggerCodeGen;
import com.github.amanganiello90.swagger.application.util.Utility;

@Controller
public class SwaggerRegistryController {

	private final StorageService storageService;

	@Autowired
	public SwaggerRegistryController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/clientTypescript/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveTypescriptClient(@PathVariable String filename) {

		SwaggerCodeGen.DownloadClient(filename, "typescript-angular");

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);

	}

	@GetMapping("/clientJava/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveJavaClient(@PathVariable String filename) {

		SwaggerCodeGen.DownloadClient(filename, "java");

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);

	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		Utility.writeTextYAML(file.getOriginalFilename());

		return "redirect:/swagger-ui";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
