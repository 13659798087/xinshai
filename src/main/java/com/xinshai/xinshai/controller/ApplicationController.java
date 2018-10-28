package com.xinshai.xinshai.controller;

import com.sinosoft.demo.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {
	@RequestMapping("/")
	public String index( Model model) {
		UserInfo user=new UserInfo();
		user.setUserName("admin");
		model.addAttribute("data", user);
		return "index";
	}

	@RequestMapping("/test/grid")
	public String grid( Model model) {
		return "test/grid";
	}
	@RequestMapping("/test/button")
	public String button( Model model) {
		return "test/button";
	}
	@RequestMapping("/test/flex")
	public String flex( Model model) {
		return "test/flex";
	}
	@RequestMapping("/test/cell")
	public String cell( Model model) {
		return "test/cell";
	}
	@RequestMapping("/test/slider")
	public String slider( Model model) {
		return "test/slider";
	}
	@RequestMapping("/test/swipeout")
	public String swipeout( Model model) {
		return "test/swipeout";
	}
	@RequestMapping("/test/form")
	public String form( Model model) {
		return "test/form";
	}
	@RequestMapping("/test/uploader")
	public String uploader( Model model) {
		return "test/uploader";
	}
	@RequestMapping("/test/gallery")
	public String gallery( Model model) {
		return "test/gallery";
	}
	@RequestMapping("/test/msg")
	public String msg( Model model) {
		return "test/msg";
	}
	@RequestMapping("/test/progress")
	public String progress( Model model) {
		return "test/progress";
	}
	@RequestMapping("/test/dialog")
	public String dialog( Model model) {
		return "test/dialog";
	}
	@RequestMapping("/test/loadmore")
	public String loadmore( Model model) {
		return "test/loadmore";
	}
	@RequestMapping("/test/actionSheet")
	public String actionSheet( Model model) {
		return "test/actionSheet";
	}
	@RequestMapping("/test/toast")
	public String toast( Model model) {
		return "test/toast";
	}
	@RequestMapping("/test/toptip")
	public String toptip( Model model) {
		return "test/toptip";
	}
	@RequestMapping("/test/tabbar")
	public String tabbar( Model model) {
		return "test/tabbar";
	}
	@RequestMapping("/test/navbar")
	public String navbar( Model model) {
		return "test/navbar";
	}
	@RequestMapping("/test/panel")
	public String panel( Model model) {
		return "test/panel";
	}
	@RequestMapping("/test/preview")
	public String preview( Model model) {
		return "test/preview";
	}
	@RequestMapping("/test/searchbar")
	public String searchbar( Model model) {
		return "test/searchbar";
	}
	@RequestMapping("/test/footer")
	public String footer( Model model) {
		return "test/footer";
	}
	@RequestMapping("/test/icons")
	public String icons( Model model) {
		return "test/icons";
	}

	@RequestMapping("/test/test")
	public String test( Model model) {
		return "test/test";
	}

	@RequestMapping("/test/test1")
	public String test1( Model model) {
		return "test/test1";
	}

}
