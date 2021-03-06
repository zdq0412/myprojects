<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/jsp/head.jsp"%>
<script type="text/javascript" src="common/js/jQuery.print.js">
</script>
<script type="text/javascript">
		$(function(){
			var ids = window.opener.document.getElementById("ids").value;
			 $.get("ngReason/printQr.do",{ids:ids},function(data){
				 var $tbody = $("#tbody");
				 var i = 0;
				 while(i<data.length){
					 var $row = $("<tr>");
					 var $col1 = $("<td>");
					 var $col2 = $("<td>");
					 var $col3 = $("<td style='border-bottom-style:none;border-top-style:none;'>");
					 var $col4 = $("<td>");
					 var $col5 = $("<td>");
					 
					 $row.append($col1);
					 $row.append($col2);
					 $row.append($col3);
					 $row.append($col4);
					 $row.append($col5);
					 
					 $tbody.append($row);
					 
					 var pressLight1 = data[i++];
					 if(pressLight1!=undefined){
						 $col1.append(pressLight1.ngCode + "<br />" + pressLight1.ngReason);
						 var $qrImg = $("<img src='<%=basePath%>/" + pressLight1.qrPath  +"'>");
						 $col2.append($qrImg);
					 }else{
						 $col1.append("");
						 $col2.append("");
					 }
					 $col3.append("");
					 var pressLight2 = data[i++];
					 if(pressLight2!=undefined){
						 $col4.append(pressLight2.ngCode + "<br />" + pressLight2.ngReason);
						 var $qrImg = $("<img src='<%=basePath%>/" + pressLight2.qrPath  +"'>");
						 $col5.append($qrImg);
					 }else{
						 $col4.append("");
						 $col5.append("");
					 }
				 }
			 });
		});

	function print() {
		$("#ngTable").print();
	}
</script>
<style>
@media print {
}

@media screen {
}
table{
	margin:0 auto;
	width:90%;
	border:solid 1px black;
}


th,td{
	text-align: center;
	border: solid 1px black;
}

.blank{
	width:50pt;
}
.content{
	height:50pt;
}
img{
	border:solid 1px black;
	height:100pt;
	width:100pt;
	margin:5pt auto;
}
</style>
<div data-toggle="topjui-layout" data-options="fit:true">
	<div data-options="region:'north',title:'',split:true"
		style="height: 50pt;">
		<div>
			<input type='button' value='打印' onclick='javascript:print()' />
		</div>
	</div>
	<div
		data-options="region:'center',title:'',fit:true,border:false,bodyCls:'border_right_bottom'">
		<table id="ngTable">
			<caption>故障原因</caption>
			<thead>
				<tr>
					<th class="content"> 故障代码</th>
					<th class="content"> 条码</th>
					<th class="blank"> </th>
					<th class="content"> 故障代码</th>
					<th class="content"> 条码</th>
				</tr>
			</thead>
			<tbody id="tbody"></tbody>
		</table>
	</div>
</div>