package com.myfirstservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Login Servlet Testing", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get request parameters for userID and password
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		// accept valid username and password patterns
		Matcher checkName = Pattern.compile("^[A-Z][A-Za-z]{2,}$").matcher(user);
		Matcher checkPassword = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])[\\w^_]*[\\W_][\\w^_]*{8,}$").matcher(pwd);
		if (checkName.matches() && checkPassword.matches()) {
			request.setAttribute("username", user);
			request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			if (!checkName.matches() && !checkPassword.matches())
				out.println("<font color=red>Username and password patterns are wrong.</font>");
			else if (!checkName.matches())
				out.println("<font color=red>Username pattern is wrong.</font>");
			else if (!checkPassword.matches())
				out.println("<font color=red>Password pattern is wrong.</font>");
			rd.include(request, response);
		}
	}
}