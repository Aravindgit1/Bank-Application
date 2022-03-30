package acc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleDriver;

/**
 * Servlet implementation class withdraw
 */
@WebServlet("/withdraw")
public class withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public withdraw() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter p=response.getWriter();
    	try{
    		
		int bala=Integer.parseInt(request.getParameter("balance"));
		
		HttpSession ses=request.getSession();
		String s1=(String)ses.getAttribute("pwd");
		
			DriverManager.registerDriver(new OracleDriver());
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			Statement st=con.createStatement();
			String sql1="select balance from bankapp1 where password='"+s1+"'";
			ResultSet rs=st.executeQuery(sql1);
			
			if(rs.next()){


				
			int l=rs.getInt(1);
			
			if(l>=bala){
				
				
				
			
			l=l-bala;
			String sql="update bankapp1 set balance="+l+"where password='"+s1+"'";
						int i=st.executeUpdate(sql);
						
						
			if(i>0){
				String s2="select balance from bankapp1 where password='"+s1+"'";
				ResultSet s3=st.executeQuery(s2);	
				if(s3.next()){
					int l2=s3.getInt(1);
				ses.setAttribute("bal",l2);
				
				response.sendRedirect("http://localhost:8065/Bank_App/withdraw1.jsp");
			
			}}
			}
			else{
				response.setContentType("text/html");
				p.println("You dont have enough money");
				RequestDispatcher rd=request.getRequestDispatcher("/with.html");
				rd.include(request,response);
				
				
				}
			}
			}
			
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			p.println(e);
		}
		
	}


}
