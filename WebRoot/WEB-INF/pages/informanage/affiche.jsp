<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">信息管理</a> <span class="divider">/</span></li>
		<li><a href="/informanage/affiche.html">公告管理</a></li>
	</ul>
</div>
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i> 公告列表</h2>
						<div class="box-icon">
							<span class="icon32 icon-color icon-add custom-setting addAffiche"/>
						</div>
					</div>
					
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>标题</th>
								  <th>发布者</th>
								  <th>发布时间</th>
								  <th>有效期</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  
						  <c:if test="${page.items != null}">
						  <c:forEach items="${page.items}" var="affiche">
							<tr>
							
								<td class="center">${affiche.title}</td>
								<td class="center">${affiche.publisher}</td>
								<td class="center"><fmt:formatDate value="${affiche.publishTime}" pattern="yyyy-MM-dd"/></td>
								<td class="center"><fmt:formatDate value="${affiche.startTime}" pattern="yyyy-MM-dd"/> - <fmt:formatDate value="${affiche.endTime}" pattern="yyyy-MM-dd"/></td>
								<td class="center">
									<a class="btn btn-success viewaffiche" href="#" id="${affiche.id}">
										<i class="icon-zoom-in icon-white"></i>  
										查看                                           
									</a>
									<a class="btn btn-info modifyaffiche" href="#" id="${affiche.id}" title="${affiche.title}">
										<i class="icon-edit icon-white"></i>  
										修改                                            
									</a>
									<a class="btn btn-danger delaffiche" href="#" id="${affiche.id}" title="${affiche.title}">
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
					  	<li><a href="/informanage/affiche.html?p=1" title="首页">首页</a></li>
					  	</c:otherwise>
					  </c:choose>
						
						<c:if test="${page.prevPages!=null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href="/informanage/affiche.html?p=${num}"
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>
						<li class="active">
						  <a href="#" title="${page.page}">${page.page}</a>
						</li>
						<c:if test="${page.nextPages!=null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a href="/informanage/affiche.html?p=${num}" title="${num}">
								${num} </a></li>
							</c:forEach>
						</c:if>
						<c:if test="${page.pageCount !=null}">
							<c:choose>
						  	<c:when test="${page.page == page.pageCount}">
						  	<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
						  	</c:when>
						  	<c:otherwise>
						  	<li><a href="/informanage/affiche.html?p=${page.pageCount}" title="尾页">尾页</a></li>
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

	<div class="modal hide fade" id="addAfficheDiv">
		<form action="/informanage/addAffiche.html" enctype="multipart/form-data" method="post" onsubmit="return addAfficheFunction();">
			<div class="modal-header">
				<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
				<h3>添加公告信息</h3>
			</div>
			<div class="add_affiche_modal-body">
				<ul id="add_formtip"></ul>
				<ul class="topul">
					<li>
					  <label>公告代码：</label><input id="afficheCode" type="text" name="code" value=""/>
					</li>
					<li>
					  <label>标题：</label><input id="afficheTitle" type="text" name="title" value=""/>
					</li>
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
					<label>有效期：</label> 
					    <!--
					   <input class="input-xlarge datepicker" readonly="readonly" name="startTime" id="startTime" value="" type="text" />  至
					   <input readonly="readonly" class="input-xlarge datepicker" name="endTime" id="endTime" value="" type="text"/> 
					    -->
					     <input class="Wdate" id="startTime" size="15" name="startTime" readonly="readonly" type="text" onClick="WdatePicker();"/>至
					    <input class="Wdate" id="endTime" size="15" name="endTime" readonly="readonly" type="text" onClick="WdatePicker();"/>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<span>公告内容：</span> <br/><textarea class="cleditor" id="afficheContent" name="content" rows="3"></textarea>
					 </li>
				</ul>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn addusercancel" data-dismiss="modal">取消</a>
				<input type="submit"  class="btn btn-primary" value="保存" />
			</div>
		</form>
	 </div>
	 
	 <div class="modal hide fade" id="modifyAfficheDiv">
		<form action="/informanage/modifyAffiche.html" enctype="multipart/form-data" method="post" onsubmit="return modifyAfficheFunction();">
			<div class="modal-header">
				<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
				<h3>修改公告信息</h3>
				<input type="hidden" name="id" id="afficheIdModify"/>
			</div>
			<div class="add_affiche_modal-body">
				<ul id="add_formtipModify"></ul>
				<ul class="topul">
					<li>
					  <label>公告代码：</label><input id="afficheCodeModify" type="text" name="code" value=""/>
					</li>
					<li>
					  <label>标题：</label><input id="afficheTitleModify" type="text" name="title" value=""/>
					</li>
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
					<label>有效期：</label> 
					   <!--
					   <input class="input-xlarge datepicker" readonly="readonly" name="startTime" id="startTimeModify" value="" type="text" />  至
					   <input readonly="readonly" class="input-xlarge datepicker" name="endTime" id="endTimeModify" value="" type="text"/> 
					   -->
					 	<input class="Wdate" id="startTimeModify" size="15" name="startTime" readonly="readonly"  value="" type="text" onClick="WdatePicker();"/>至
					    <input class="Wdate" id="endTimeModify" size="15" name="endTime" readonly="readonly"  value="" type="text" onClick="WdatePicker();"/>
					 </li>
				</ul>
				<ul class="downul">
					<li id="modifyafficheli">
					 </li>
				</ul>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn addusercancel" data-dismiss="modal">取消</a>
				<input type="submit"  class="btn btn-primary" value="保存" />
			</div>
		</form>
	 </div>
	 
	 <div class="modal hide fade" id="viewAfficheDiv">
			<div class="modal-header">
				<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
				<h3>查看公告信息</h3>
			</div>
			<div class="add_affiche_modal-body">
				<ul id="add_formtip"></ul>
				<ul class="topul">
					<li>
					  <label>公告代码：</label><input id="afficheCodeText" readonly="readonly" type="text" name="code" value=""/>
					</li>
					<li>
					  <label>标题：</label><input id="afficheTitleText" readonly="readonly" type="text" name="title" value=""/>
					</li>
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
					<label>有效期：</label> <input id="startTimeText" readonly="readonly" value="" type="text" />  至
					   <input id="endTimeText" readonly="readonly" value="" type="text"/> 
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<span>公告内容：</span> <br/><div id="afficheContentText" readonly="readonly" rows="3"></div>
					 </li>
				</ul>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">关闭</a>
			</div>
	 </div>
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/affiche.js"></script> 
