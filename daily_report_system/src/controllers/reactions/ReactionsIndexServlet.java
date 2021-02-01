package controllers.reactions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Reaction;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReactionsIndexServlet
 */
@WebServlet("/reactions/index")
public class ReactionsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReactionsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Report report = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception re) {
            page = 1;
        }
        List<Reaction> reactions = em.createNamedQuery("getMyAllReactions", Reaction.class)
                                     .setParameter("report", report)
                                     .setFirstResult(15 * (page - 1))
                                     .setMaxResults(15)
                                     .getResultList();

        long reactions_count = (long)em.createNamedQuery("getMyReactionsCount", Long.class)
                                     .setParameter("report", report)
                                     .getSingleResult();

        em.close();

        request.setAttribute("reactions", reactions);
        request.setAttribute("reactions_count", reactions_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reactions/index.jsp");
        rd.forward(request, response);
    }

}