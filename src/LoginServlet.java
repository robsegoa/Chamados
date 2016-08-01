

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//exemplo para apagar cookie
		Cookie[] ck2 = request.getCookies();
		
		if(ck2 != null){
		for(Cookie cookie : ck2){
			if (cookie.getName().equals("login_name")){
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		}
		
		//fim do exemplo de apagar cookie
		try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			
			//Para Sair aula 8
			if(request.getParameter("msg") != null){
				if(request.getParameter("msg").equals("logoff")){
					HttpSession sessao = request.getSession();
					sessao.invalidate();
					out.println("<span style='color:red'>Deslogado com sucesso! </span>");
				}
			}
			//inserido na aula 8
			if(request.getParameter("msg") != null){
				if(request.getParameter("msg").equals("error")){
					out.println("<span style='color:red'>Login e/ou senhas incorretos! </span>");
				}
			}
			
			String login_name = "";
			
			Cookie[] ck = request.getCookies();
			
			if(ck != null){
			for(Cookie cookie : ck){
				if (cookie.getName().equals("login_name")){
					login_name = cookie.getValue();
				}
			}
			}
			
			
			out.println("<head>");
			out.println("<title>Login</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Preencha seu login e senha</h1>");
			out.println("<hr/>");
			out.println("<form method='POST'>");
			out.println("Login:<br> <input type='text' name='txtLogin' value='"+ login_name +"'><br>");
			out.println("Senha:<br> <input type='password' name='txtSenha'><br>");
			//out.println("Conteúdo:<br> <textarea name='txtConteudo' rows='10' cols'40'></textarea>");
			out.println("<br>");
			out.println("<input type='submit' value='Logar'>");
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
				
				
		
		String login = request.getParameter("txtLogin");
		String senha = request.getParameter("txtSenha");
		//out.println(titulo);
		
		//aula 9
		Cookie ck = new Cookie("login_name", login);
		ck.setMaxAge(3600*24*90);//em segundos a durabilidade do cookie
		response.addCookie(ck);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			
			String SQL = "Select * from usuarios where login = ? and senha = ? ";
			//out.println(SQL);
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
				
				pstm.setString(1, login);
				pstm.setString(2, senha);
				//out.println(SQL);
				//pstm.execute();
				
				ResultSet rs = pstm.executeQuery();
				
				
				
				if (rs.next()){
					pstm.close();
					conn.close();
					
					HttpSession sessao = request.getSession();
					sessao.setAttribute("login", login);
					sessao.setAttribute("info", request.getRemoteAddr());
					//Teste de erro de login esta na listar chamados
					
					
					
					response.sendRedirect("http://localhost:8080/Chamados/ListarChamados");
				}else {
					
					pstm.close();
					conn.close();
					
					response.sendRedirect("http://localhost:8080/Chamados/Login?msg=error");
				}
				
				
			
			} catch (SQLException e) {
				
				out.println("Problema no banco de dados"+ e.getMessage());
			}
			
			
			
			}catch (ClassNotFoundException ex){
				out.println("Problema ao carregar a conexao");
			}
		
		
	}
}
