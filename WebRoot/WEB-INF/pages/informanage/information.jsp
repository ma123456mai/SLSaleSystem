<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">信息管理</a> <span class="divider">/</span></li>
		<li><a href="/informanage/information.html">资讯管理</a></li>
	</ul>
</div>
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i class="icon-user"></i> 资讯列表</h2>
						<div class="box-icon">
							<span class="icon32 icon-color icon-add custom-setting addInformation"/>
						</div>
					</div>
					
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>标题</th>
								  <th>文件大小</th>
								  <th>状态（发布/不发布）</th>
								  <th>发布者</th>
								  <th>最后修改时间</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
						  
						  <c:if test="${page.items != null}">
						  <c:forEach items="${page.items}" var="infor">
							<tr>
							
								<td class="center">${infor.title}</td>
								<td class="center">${infor.fileSize/1000}KB</td>
								<td class="center">
									<input type="checkbox" title="直接勾选修改状态，立即生效" data-rel="tooltip" class="modifyInformationState" inforstate="${infor.state}" inforid="${infor.id}" <c:if test="${infor.state == 1}">checked="true"</c:if>/>
								</td>
								<td class="center">${infor.publisher}</td>
								<td class="center"><fmt:formatDate value="${infor.publishTime}" pattern="yyyy-MM-dd"/></td>
								<td class="center">
									<a class="btn btn-success viewinformation" href="#" id="${infor.id}" title="${infor.title}">
										<i class="icon-zoom-in icon-white"></i>  
										查看                                           
									</a>
									<a class="btn btn-info modifyinformation" href="#" id="${infor.id}" title="${infor.title}">
										<i class="icon-edit icon-white"></i>  
										修改                                            
									</a>
									<a class="btn btn-danger delinformation" href="#" id="${infor.id}" title="${infor.title}">
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
					  	<li><a href="/informanage/information.html?p=1" title="首页">首页</a></li>
					  	</c:otherwise>
					  </c:choose>
						
						<c:if test="${page.prevPages!=null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href="/informanage/information.html?p=${num}"
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>
						<li class="active">
						  <a href="#" title="${page.page}">${page.page}</a>
						</li>
						<c:if test="${page.nextPages!=null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a href="/informanage/information.html?p=${num}" title="${num}">
								${num} </a></li>
							</c:forEach>
						</c:if>
						<c:if test="${page.pageCount !=null}">
							<c:choose>
						  	<c:when test="${page.page == page.pageCount}">
						  	<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
						  	</c:when>
						  	<c:otherwise>
						  	<li><a href="/informanage/information.html?p=${page.pageCount}" title="尾页">尾页</a></li>
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

	<div class="modal hide fade" id="addInformationDiv">
		<form action="/informanage/addInformation.html" enctype="multipart/form-data" method="post" onsubmit="return addInfoFunction();">
			<div class="modal-header">
				<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
				<h3>添加资讯信息</h3>
			</div>
			<div class="add_information_modal-body">
				<ul id="add_formtip"></ul>
				<ul class="topul">
					<li>
					  <label>标题：</label><input id="informationTitle" type="text" name="title" value=""/>
					</li>
					<li>
					  <label>资讯类型：</label>
					  <select id="docType" name="typeId" style="width:100px;">
					 			<option value="" selected="selected">--请选择--</option>
					 			<c:if test="${dicList != null}">
					 				<c:forEach items="${dicList}" var="dic">
					 					<option value="${dic.id}">${dic.valueName}</option>
					 				</c:forEach>
					 			</c:if>
					 		</select>
					</li>
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
						<span>上传附件：</span><input id="uploadInformationFile" name="uploadInformationFile" type="file" /> <input type="button" id="informationuploadbtn" value="上传"/>
						<span style="color:red;font-weight: bold;">*注：上传大小不得超过 500M</span>
					 	<input type="hidden" id="uploadfilepathhide" name="filePath" />
					 	<input type="hidden" id="uploadfilenamehide" name="fileName" />
					 	<input type="hidden" id="typeNamehide" name="typeName" />
					 	<input type="hidden" id="fileSizehide" name="fileSize" />
					 </li>
					 <li id="filearea">
					 	
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<span>资讯内容：</span> <br/><textarea class="cleditor" id="informationContent" name="content" rows="3"></textarea>
					 </li>
				</ul>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn addinfocancel" data-dismiss="modal">取消</a>
				<input type="submit"  class="btn btn-primary" value="保存" />
			</div>
		</form>
	 </div>
	 
	 <div class="modal hide fade" id="modifyInfoDiv">
		<form action="/informanage/modifyinformation.html" enctype="multipart/form-data" method="post" onsubmit="return modifyInfoFunction();">
			<div class="modal-header">
				<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
				<h3>修改资讯信息</h3>
			</div>
			<div class="add_information_modal-body">
				<ul id="modify_formtip"></ul>
				<ul class="topul">
					<li>
					  <label>标题：</label><input id="informationTitleModify" type="text" name="title" value=""/>
					</li>
					<li>
					  <label>资讯类型：</label>
					  <select id="docTypeModity" name="typeId" style="width:100px;"></select>
					</li>
				</ul>
				<div class="clear"></div>
				<ul class="downul">
					<li>
						<span>上传附件：</span><input id="uploadInformationFileM" name="uploadInformationFile" type="file" /> <input type="button" id="informationuploadMbtn" value="上传"/>
					 	<span style="color:red;font-weight: bold;">*注：上传大小不得超过 500M</span>
					 	<input type="hidden" id="infoIdModify" name="id"/>
					 	<input type="hidden" id="uploadfilepathhideM" name="filePath" />
					 	<input type="hidden" id="uploadfilenamehideM" name="fileName" />
					 	<input type="hidden" id="typeNamehideM" name="typeName" />
					 	<input type="hidden" id="fileSizehideM" name="fileSize" />
					 </li>
					 <li id="fileareaM">
					 	
					 </li>
				</ul>
				<ul class="downul">
					<li id="modifyinformationli">
					 </li>
				</ul>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn modifyinfocancel" data-dismiss="modal">取消</a>
				<input type="submit"  class="btn btn-primary" value="保存" />
			</div>
		</form>
	 </div>
	 
	 <div class="modal hide fade" id="viewInfoDiv">
			<div class="modal-header">
				<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
				<h3>查看资讯信息</h3>
			</div>
			
			
			<div class="view_information_modal-body">
				<ul class="viewinformationul" id="viewContent">
				</ul>
				<div class="clear"></div>
			</div>
			
			
			
			
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">关闭</a>
			</div>
	 </div>
	 
	 
	 
<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript">
	var dicJson =	[<c:forEach  items="${dicList}" var="dic"> 
							{"valueId":"${dic.id}","valueName":"${dic.valueName}"},
							</c:forEach>{"valueId":"over","valueName":"over"}];
</script>
<script type="text/javascript" src="/statics/localjs/information.js"></script> 
