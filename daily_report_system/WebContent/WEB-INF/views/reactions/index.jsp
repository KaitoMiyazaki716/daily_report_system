<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>いいね！した人一覧</h2>

        <table id="report_list">
            <tbody>
                <tr>
                    <th class="reaction_name">氏名</th>
                    <th class="reaction_created_at">日時</th>
                </tr>
                <c:forEach var="reaction" items="${reactions}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="reaction_name"><c:out value="${reaction.employee.name}" /></td>
                        <td class="reaction_created_at"><fmt:formatDate value="${reaction.created_at}" pattern="yyyy-MM-dd" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全 ${reactions_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((reactions_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
         </div>
        <p><a href="<c:url value='/reports/index' />">日報一覧に戻る</a></p>
    </c:param>
</c:import>