package com.ando.exam.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.ando.exam.base.BaseController;
import com.ando.exam.sys.config.Constant;
import com.ando.exam.sys.entity.Photo;
import com.ando.exam.sys.entity.User;
import com.ando.exam.sys.repository.PhotoRepository;

@RestController
@RequestMapping(Constant.APP_SYS)
public class PhotoController extends BaseController {

	@Autowired
	private PhotoRepository photoRepository;
	
	@RequestMapping("/photo")
	public void photo(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Optional<Photo> opt = photoRepository.findById(user.getEmpid());
//		res.setCharacterEncoding("utf-8");
		if(opt.isPresent()) {
			try {
				OutputStream outputStream = res.getOutputStream();
				outputStream.write(opt.get().getPhoto());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			defaultPhoto(res);
		}
	}

	
	public void defaultPhoto(HttpServletResponse res) {
		ClassPathResource cpr = new ClassPathResource("static/resources/images/user-profile/2.png");
		 try {
			OutputStream outputStream = res.getOutputStream();
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			outputStream.write(bdata);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping("/upload")
	public String upload(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		User user = (User) session.getAttribute("user");
		StandardMultipartHttpServletRequest httpServletRequest = (StandardMultipartHttpServletRequest) req;
		Iterator<String> iterator = httpServletRequest.getFileNames();
		while (iterator.hasNext()) {
			MultipartFile file = httpServletRequest.getFile(iterator.next());
			try {
				Photo p = new Photo(user.getEmpid(),file.getBytes());
				photoRepository.save(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "success";
	}
}