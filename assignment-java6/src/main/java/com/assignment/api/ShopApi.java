package com.assignment.api;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.util.SessionUtil;

@RestController
@RequestMapping("/api/shop")
public class ShopApi {

	@GetMapping("/update")
	public ResponseEntity<?> doGetUpdate(@RequestParam("maxSizePage") Integer maxSizePage, HttpSession session) {
		SessionUtil.setMaxSizePage(maxSizePage, session);
		Integer smp = SessionUtil.getMaxSizePage(session);
		return ResponseEntity.ok(smp);
	}
}
