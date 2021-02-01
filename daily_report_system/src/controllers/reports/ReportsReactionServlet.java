package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Reaction;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsReactionServlet
 */
@WebServlet("/reports/reaction")
public class ReportsReactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsReactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        r.setReaction_count(r.getReaction_count() + 1);

        Reaction re = new Reaction();

        re.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        re.setReport(r);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        re.setCreated_at(currentTime);
        re.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(re);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("reaction", re);
        request.getSession().setAttribute("flush", "いいね！しました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");

  }
}