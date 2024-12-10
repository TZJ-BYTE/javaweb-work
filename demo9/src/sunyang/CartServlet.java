package sunyang;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] items = { "ͼ��", "��ױƷ", "�·�" }; // ��Ʒ

		HttpSession session = req.getSession(); // ��ȡHttpSession����
		Integer itemCount = (Integer) session.getAttribute("itemCount");// ��Session�л�ȡѡ�����Ʒ��Ŀ
		if (itemCount == null) { // �������Ʒ����ĿΪ0
			itemCount = new Integer(0);
		}

		resp.setCharacterEncoding("GBK"); // ������Ӧ�ı�������ΪGBK
		PrintWriter out = resp.getWriter(); // ��ȡ�������
		String[] itemsSelected = req.getParameterValues("item");// ȡ�ñ���ѡ�е���Ʒ
		if (itemsSelected != null) {// �ж��Ƿ�ѡ����Ʒ
			for (int i = 0; i < itemsSelected.length; i++) {
				itemCount = new Integer(itemCount.intValue() + 1);//ѡ�е���Ʒ����
				session.setAttribute("Item" + itemCount, itemsSelected[i]);// ��ѡ�е���Ʒ����Ự��
				session.setAttribute("itemCount", itemCount);// ����Ʒ��������Ự��

			}
		}

		out.println("<html>");
		out.println("<head>");
		out.println("<title>���ﳵ</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center><h2>���ﳵ</h2></center>");
		out.println("<table border=1 align=center bgcolor=#WFFFFF width=250> ");
		out.println("<tr>");
		out.println("<td align=center>��Ʒ����</td>");
		out.println("</tr>");
		for (int i = 0; i < itemCount.intValue(); i++) {
			String item = (String) session.getAttribute("Item" + (i+1));
			out.println("<tr>");
			out.println("<td>");			
			out.println(items[Integer.parseInt(item)]);// ������ﳵ�е���Ʒ
			out.println("</td>");
			out.println("</tr>");
		}
		out.println("<tr>");
		out.println("<td>");
		out.println("�����ﳵ�й���"+itemCount+"����Ʒ");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</body>");
		out.println("</html>");
		out.close();//�ر��������

	}

}
