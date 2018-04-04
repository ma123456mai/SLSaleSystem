<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>


			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-file"></i> ${information.title}</h2>
						<div class="box-icon">
							<a href="javascript:window.history.back(-1);"><span class="icon icon-color icon-undo" title="返回" /></a>
						</div>
					</div>
					<div class="box-content">
					 <h1 style="text-align:center;">${information.title}</h1>
					 	<div style="border-bottom:1px solid #ccc;padding:5px;">发布者： ${information.publisher}  发布时间：<fmt:formatDate value="${information.publishTime}" pattern="yyyy-MM-dd"/></div>
					 	<p style="padding:10px;">
					 		${information.content}
					 	</p>
					 	
					 	<c:if test="${information.filePath == null || information.fileName == null || information.filePath == '' || information.fileName == ''}">
					 	<p>
					 		附件：暂无
					 	</p>
					 	</c:if>
					 	<c:if test="${information.filePath != null && information.fileName != null && information.filePath != '' && information.fileName != ''}">
					 	<p>
					 		附件：<a href="${information.filePath}">${information.fileName} (右键另存为...)</a>
					 	</p>
					 	</c:if>
					 </div>
					</div><!--/span-->
				</div><!--/row-->
				
				
				
				
				
				
				
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
