<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">信息管理</a> <span class="divider">/</span></li>
		<li><a href="/message/messagelist.html">留言管理</a></li>
	</ul>
</div>

			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i> 留言列表</h2>
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th width="10%">留言会员账号</th>
								  <th>留言内容</th>
								  <th width="15%">状态(已回复/未回复)</th>
								  <th width="10%">留言时间</th>
								  <th width="22%">操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  
						  <c:if test="${page.items != null}">
						  <c:forEach items="${page.items}" var="leaveMessage">
							<tr>
							
								<td class="center">${leaveMessage.createdBy}</td>
								<td class="center" style="word-break:break-all;">${leaveMessage.messageContent}</td>
								<td class="center">
								<c:if test="${leaveMessage.state == 0}">未回复</c:if>
                    			<c:if test="${leaveMessage.state == 1}">已回复</c:if>
								</td>
								<td class="center">
								<fmt:formatDate value="${leaveMessage.createTime}" pattern="yyyy-MM-dd"/>
								</td>
								<td class="center">
									<a class="btn btn-success viewmessage" href="#" id="${leaveMessage.id}">
										<i class="icon-zoom-in icon-white"></i>  
										查看                                           
									</a>
									<a class="btn btn-info replymessage" href="#" id="${leaveMessage.id}">
										<i class="icon-edit icon-white"></i>  
										回复                                            
									</a>
									<a class="btn btn-danger delmessage" href="#" createdBy="${leaveMessage.createdBy}" id="${leaveMessage.id}" >
										<i class="icon-trash icon-white"></i> 
										删除
									</a>
								</td>
							</tr>
						  </c:forEach>
						 </c:if>
						  </tbody>
					  </table>   
					<div class="pagination pagination-centered">
					  <ul>
					   <c:choose>
					  	<c:when test="${page.page == 1}">
					  	<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
					  	</c:when>
					  	<c:otherwise>
					  	<li><a href="/message/messagelist.html?currentpage=1" title="首页">首页</a></li>
					  	</c:otherwise>
					   </c:choose>
						<c:if test="${page.prevPages!=null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href="/message/messagelist.html?currentpage=${num}"
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>
						<li class="active">
						  <a href="#" title="${page.page}">${page.page}</a>
						</li>
						<c:if test="${page.nextPages!=null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a href="/message/messagelist.html?currentpage=${num}" title="${num}">
								${num} </a></li>
							</c:forEach>
						</c:if>
						<c:if test="${page.pageCount !=null}">
							<c:choose>
						  	<c:when test="${page.page == page.pageCount}">
						  	<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
						  	</c:when>
						  	<c:otherwise>
						  	<li><a href="/message/messagelist.html?currentpage=${page.pageCount}" title="尾页">尾页</a></li>
						  	</c:otherwise>
						    </c:choose>
					    </c:if>
						<c:if test="${page.pageCount == null}">
						<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
					  	</c:if>
					  </ul>
				  </div>
				</div>
			</div><!--/span-->
		</div><!--/row-->
	 
	 <div class="modal hide fade" id="replyMessageDiv">
			<div class="modal-header">
				<button type="button" class="close replymessagecancel" data-dismiss="modal">×</button>
				<h3>回复留言</h3>
			</div>
			<div class="modal-body">
				<ul class="topul">
					<li>
					  <label>留言人：</label><p id="message_createdBy" />
					</li>
					<li>
					  <label>留言时间：</label><p id="message_createTime"/>
					</li> 
					<div class="clear"></div>
				</ul>
				<div class="messagecontentdiv">
					  <label class="messagecontentlabel">留言内容：</label>
					  <p id="message_messageContent"></p>
				</div>
			</div>
			<ul id="replylist"></ul>
			<form action="/message/replymessage.html" enctype="multipart/form-data" method="post" onsubmit="return replyMessageFunction();">
			<div class="modal-body">
				<ul id="reply_formtip"></ul>
				<input id="message_id" type="hidden" name="messageId"/>
				<ul class="topul">
					<li>
					  <label>回复人：</label><input type="text" value="${currentUser}" readonly="readonly"/>
					</li>
					<li>
					  <label>回复时间：</label><input type="text" id="reply_createTime" readonly="readonly"/>
					</li> 
					<div class="clear"></div>
				</ul>
				<div class="messagecontentdiv">
					  <label class="messagecontentlabel">回复内容：</label>
					  <textarea id="r_content" name="replyContent"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn replymessagecancel" data-dismiss="modal">取消</a>
				<input type="submit"  class="btn btn-primary" value="保存" />
			</div>
		</form>
	 </div>
	 
	 <div class="modal hide fade" id="viewreplyMessageDiv">
			<div class="modal-header">
				<button type="button" class="close viewreplymessagecancel" data-dismiss="modal">×</button>
				<h3>查看留言回复</h3>
			</div>
			<div class="modal-body messagebody">
				<ul class="topul">
					<li>
					  <label>留言人：</label><p id="viewmessage_createdBy"/>
					</li>
					<li>
					  <label>留言时间：</label><p id="viewmessage_createTime" />
					</li> 
					<div class="clear"></div>
				</ul>
				<div class="messagecontentdiv">
					  <label class="messagecontentlabel">留言内容：</label>
					  <p id="viewmessage_messageContent"></p>
				</div>
			</div>
			<ul id="viewreplylist"></ul>
	 </div>
	 
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/messagelist.js"></script> 
