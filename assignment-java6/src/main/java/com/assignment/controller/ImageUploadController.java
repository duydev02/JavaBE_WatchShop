package com.assignment.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageUploadController {

	@GetMapping("getuserimage/{photo}")
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
		if (!photo.equals("") || photo != null) {
			try {
				Path filename = Paths.get("images/user-avatar", photo);
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("getproductimage/{photo}")
	public ResponseEntity<ByteArrayResource> getImageProduct(@PathVariable("photo") String photo) {
		if (!photo.equals("") || photo != null) {
			try {
				Path filename = Paths.get("images/product-image", photo);
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ResponseEntity.badRequest().build();
	}

}
