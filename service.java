package service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.dao;
import dto.profiles;
import dto.user;

public class service {

	dao d = new dao();

	
	public void signup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<profiles> list = null;
		Part image = req.getPart("image");
		String name = req.getParameter("name");
		String fname = req.getParameter("fname");
		String mname = req.getParameter("mname");
		String mobile = req.getParameter("mobile");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String cpassword = req.getParameter("cpassword");
		String gender = req.getParameter("gender");
		String caste = req.getParameter("caste");
		String dob = req.getParameter("dob");
		String qualification = req.getParameter("qualification");
		String work = req.getParameter("work");
		String height = req.getParameter("height");
		String address = req.getParameter("address");

		user u = new user(); // to send data to table
		byte[] pic = new byte[image.getInputStream().available()]; // jpeg to byte[] to store it in database.
		image.getInputStream().read(pic); // [image.getInputStream().available()] = size declare and then conversion.
		u.setImage(pic);
		u.setName(name);
		u.setFname(fname);
		u.setMname(mname);
		u.setMobile(Long.parseLong(mobile));
		u.setEmail(email);
		u.setPassword(password);
		u.setCpassword(cpassword);
		u.setGender(gender);
		u.setCaste(caste);
		u.setDob(LocalDate.parse(dob));
		u.setQualification(qualification);
		u.setWork(work);
		u.setHeight(Double.parseDouble(height));
		u.setAddress(address);

		d.saveUser(u);// table create
		
	
		profiles p = new profiles();
		p.setName(name);
		p.setStatus(false);
		p.setDescription(dob);
		p.setImage(pic);
		user user = (user) req.getSession().getAttribute("userLogin");
	
		d.saveProfile(p);
		
		
		req.getRequestDispatcher("thankyou.html").forward(req, resp);
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String email1 = req.getParameter("emph");
		String password1 = req.getParameter("pass");
		String look = req.getParameter("options");
		List<user> list = null;
		try {
			long mobile = Long.parseLong(email1);
			list = d.finduserbymobile(mobile);
			if (list.isEmpty()) {
				resp.getWriter().print("<h1>Incorrect Mobile number</h1>");
			}
		} catch (NumberFormatException e) {
			String email2 = email1;
			list = d.finduserbyemail(email2);
			if (list.isEmpty())
				resp.getWriter().print("<h1>Incorrect Email</h1>");
		}
		
		if(!list.isEmpty()) {
			if(list.get(0).getPassword().equals(password1)) {
				resp.getWriter().print("<h1>login Success</h1>");
				req.getRequestDispatcher("home.jsp").include(req, resp);
			}else {
				resp.getWriter().print("<h1>Incorrect Password</h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			}
		}
		else {
			req.getRequestDispatcher("Login.html").include(req, resp);
		}
		
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getSession().removeAttribute("userLogin");
		resp.getWriter().print("<h1 style='color:black'>Logged Out Successfully</h1>");
		req.getRequestDispatcher("Login.html").include(req, resp);
	}

	public void viewprofile(HttpServletRequest req, HttpServletResponse resp) {
		
		
	}
	
	
}
