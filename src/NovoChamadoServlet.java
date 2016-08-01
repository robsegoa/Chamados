

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
//import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NovoChamadoServlet extends HttpServlet {
//iText
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Novo Chamado</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha as informações do chamado</h1>");
			out.println("<hr/>");
			out.println("<form method='POST'>");
			out.println("Título:<br><input type='text' name='txtTitulo'><br>");
			out.println("Conteúdo:<br> <textarea name='txtConteudo' rows='10' cols'40'></textarea>");
			out.println("<br>");
			out.println("<input type='submit' value='Abrir Chamado'>");
			out.println("</form>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamado</a>");
			out.println("<br/>");
			out.println("<a href='http://localhost:8080/Chamados/Logoff'>Sair</a>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			//  Auto-generated catch block
			
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
				
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
			
			String SQL = "insert into chamados (titulo,conteudo,data) values(?, ? ,?)";
			
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
				//importar para data sempre o pacote de JavaUTIL
				//o Date é na aula 12
				Date dataSQL = new Date(new java.util.Date().getTime());
				
				//Date data = new Date();
				//java.sql.Date dataSQL = new java.sql.Date(data.getTime());
				//SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
				//dt.fotmat(data);
				
				
				pstm.setString(1, titulo);
				pstm.setString(2, conteudo);
				pstm.setDate(3, dataSQL);
				//pstm.setDate(3, dt.format(data));
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
