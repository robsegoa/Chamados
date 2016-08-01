

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditarChamadoServlet extends HttpServlet {
//iText
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		try{
			
			
			Class.forName("com.mysql.jdbc.Driver");
						
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root", "1234banana");
			//ou endereço IP
						
				String SQL = "select * from chamados where id= ?";
				
				PreparedStatement pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, Integer.parseInt(request.getParameter("id")));
				
				
				ResultSet rs = pstm.executeQuery();
				
				if(rs.next()){
					
				
			
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Editar Chamado</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Preencha as informações do chamado</h1>");
				out.println("<hr/>");
				out.println("<form method='POST'>");
				out.println("ID do Chamado:<br><input type='text' name='txtID' readonly='readonly' value ='"+ rs.getString("id") +"' ><br>");
				//readonly para abrir somente o que tiver id
				out.println("Título:<br><input type='text' name='txtTitulo' value ='"+ rs.getString("titulo") +"'><br>");
				out.println("Conteúdo:<br> <textarea name='txtConteudo' rows='10' cols'40'>"+ rs.getString("conteudo")+"</textarea>");
				out.println("<br>");
				out.println("<input type='submit' value='Atualizar Chamado'>");
				out.println("</form>");
				out.println("<br/>");
				out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamado</a>");
				out.println("<br/>");
				out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair</a>");
				out.println("</body>");
				out.println("</html>");
				} else {
					out.println("esse chamado não existe");
				}
				
				
				pstm.close();
				
				conn.close();
				/*
				PreparedStatement pstm = conn.prepareStatement(SQL);
				
				pstm.setString(1, titulo);
				pstm.setString(2, conteudo);
				//out.println(SQL);
				pstm.execute();
				
				pstm.close();
				
				conn.close();
			*/
			} catch (SQLException e) {
				
				out.println("Problema no banco de dados"+ e.getMessage());
			
			}catch (ClassNotFoundException ex){
				out.println("Problema ao carregar a conexao");
			}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String titulo = request.getParameter("txtTitulo");
		String conteudo = request.getParameter("txtConteudo");
		//out.println(titulo);
		
		if(titulo.length()< 4){
			out.println("Preencha o campo titulo");
		}else if(conteudo.equals("")){
			out.println("Preencha o campo conteudo");
		}else {
			try{
			Class.forName("com.mysql.jdbc.Driver");
			//String SQL = "insert into chamados (titulo,conteudo) values (";
			//SQL +=" '"+ titulo +"', '"+conteudo +"') ";
			
			String SQL = "update chamados set titulo = ?, conteudo = ?";
					SQL += " where id = ? ";
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/chamados_rlsystem", "root", "1234banana");
				//ou endereço IP
				/*		
				Statement stm = conn.createStatement();
				stm.execute(SQL);
				stm.close();
				conn.close();
				*/
				PreparedStatement pstm = conn.prepareStatement(SQL);
				
				pstm.setString(1, titulo);
				pstm.setString(2, conteudo);
				pstm.setInt(3, id);
				//out.println(SQL);
				pstm.execute();
				
				pstm.close();
				
				conn.close();
				response.sendRedirect("http://localhost:8080/Chamados/ListarChamados");
			
			} catch (SQLException e) {
				
				out.println("Problema no banco de dados"+ e.getMessage());
			}
			
			
			
			}catch (ClassNotFoundException ex){
				out.println("Problema ao carregar a conexao");
			}
		}
		
	}

}
