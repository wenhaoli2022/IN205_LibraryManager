package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;

import com.ensta.librarymanager.model.Livre;

import com.ensta.librarymanager.service.LivreServiceImpl;

public class LivreDeleteServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		
		String inputId = request.getParameter("id");
		int id = -1;
		
		Livre livre;
		
		try{
			id = Integer.parseInt(inputId);
			livre = livreService.getById(id);
			
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			livre = new Livre();
			
		} catch(NumberFormatException ebis){
			livre = new Livre();
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		request.setAttribute("livre", livre);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_delete.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LivreServiceImpl livreService = LivreServiceImpl.getInstance();
		
		String inputId = request.getParameter("id");
		int id = -1;
		
		try{
			id = Integer.parseInt(inputId);
			livreService.delete(id);
			
		} catch(ServiceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		} catch(NumberFormatException ebis){
			throw new ServletException("Erreur lors du parsing : id="+inputId,ebis);
		} catch(Exception e){
			throw new ServletException("Erreur au niveau du servlet : ",e);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/livre_list.jsp");
		dispatcher.forward(request, response);
	}
}
