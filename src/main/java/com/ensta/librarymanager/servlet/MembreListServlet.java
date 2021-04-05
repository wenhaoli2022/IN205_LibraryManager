package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.model.Membre;

import com.ensta.librarymanager.service.MembreServiceImpl;

public class MembreListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MembreServiceImpl membreService = MembreServiceImpl.getInstance();
		
		List<Membre> membres = new ArrayList<Membre>();
		try{
			membres = membreService.getList();
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		request.setAttribute("membres", membres);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/membre_list.jsp");
		dispatcher.forward(request, response);
	}
}
