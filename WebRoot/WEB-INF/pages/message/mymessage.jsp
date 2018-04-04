<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">信息管理</a> <span class="divider">/</span></li>
		<li><a href="/message/mymessage.html">我的留言</a></li>
	</ul>
</div>
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i> 我的留言</h2>
					</div>
					
					<div class="box-content">
						
		<div class="mymessagediv">
			<c:if test="${page.items != null}">
				<ul>
			  <c:forEach items="${page.items}" var="message">
			  <li>
			  	<input type="hidden" value="${message.id}"/>
			  	<div class="msgContent" style="word-break:break-all;"><label>留言： <fmt:formatDate value="${message.createTime}" pattern="yyyy-MM-dd HH:MM:ss"/></label>${message.messageContent}</div>
			  	<c:choose>
			  	<c:when test="${message.state == 1}">
			  		<div class="msgReply" mid="${message.id}"></div>
			  	</c:when>
			  	<c:otherwise>
			  		<div class="msgReply" mid="noid">未回复</div>
			  	</c:otherwise>
			  	</c:choose>
				</li>
			  </c:forEach>
			  </ul>
			 </c:if>
		</div>

  
		<div class="pagination pagination-centered">
		  <ul>
		   <c:choose>
		  	<c:when test="${page.page == 1}">
		  	<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
		  	</c:when>
		  	<c:otherwise>
		  	<li><a href="/message/mymessage.html?currentpage=1" title="首页">首页</a></li>
		  	</c:otherwise>
		   </c:choose>
			<c:if test="${page.prevPages!=null}">
				<c:forEach items="${page.prevPages}" var="num">
					<li><a href="/message/mymessage.html?currentpage=${num}"
						class="number" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>
			<li class="active">
			  <a href="#" title="${page.page}">${page.page}</a>
			</li>
			<c:if test="${page.nextPages!=null}">
				<c:forEach items="${page.nextPages}" var="num">
					<li><a href="/message/mymessage.html?currentpage=${num}" title="${num}">
					${num} </a></li>
				</c:forEach>
			</c:if>
			<c:if test="${page.pageCount !=null}">
				<c:choose>
			  	<c:when test="${page.page == page.pageCount}">
			  	<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
			  	</c:when>
			  	<c:otherwise>
			  	<li><a href="/message/mymessage.html?currentpage=${page.pageCount}" title="尾页">尾页</a></li>
			  	</c:otherwise>
			    </c:choose>
		    </c:if>
			<c:if test="${page.pageCount == null}">
			<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
		  	</c:if>
		  </ul>
	  </div>
				  
				  
				  
	  <!-- message form -->
	  <div class="messsageform">
	  	<form action="/message/addmessage.html" method="post" onsubmit="return addMsg();">
	  		<label>留言内容：<span id="tip"></span></label><textarea id="messageContent" name="messageContent"></textarea>
	  		<p class="center span5">
			<button type="submit" class="btn btn-primary">提交留言</button>
			</p>
	  	</form>
	  </div>
				  
				  
				  
				  
				</div>
			</div><!--/span-->
		</div><!--/row-->

	 
	 
	 
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/mymessage.js"></script> 