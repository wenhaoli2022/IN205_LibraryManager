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

import com.ensta.librarymanager.model.Emprunt;

import com.ensta.librarymanager.service.EmpruntServiceImpl;

public class EmpruntListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
		
		List<Emprunt> emprunts = new ArrayList<Emprunt>();
		try{
			emprunts = empruntService.getListCurrent();
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("emprunts", emprunts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/emprunt_list.jsp");
		dispatcher.forward(request, response);
	}
}
