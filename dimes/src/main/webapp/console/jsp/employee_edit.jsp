<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-toggle="topjui-layout" data-options="fit:true">
	<div
		data-options="region:'center',title:'',fit:true,border:false,bodyCls:'border_right_bottom'">
		<form id="ff" method="post" >
		<div title="员工信息" data-options="iconCls:'fa fa-th'">
			<div class="topjui-fluid">
				<div class="topjui-row">
					<div class="topjui-col-sm12">
						<label class="topjui-form-label">员工代码</label>
						<div class="topjui-input-block">
							<input type="text" name="code" readonly="readonly" data-toggle="topjui-textbox"
								data-options="required:true" id="employeeCode_edit">
						</div>
					</div>
				</div>
				<div class="topjui-row">
					<div class="topjui-col-sm12">
						<label class="topjui-form-label">员工名称</label>
						<div class="topjui-input-block">
							<input type="text" name="name" data-toggle="topjui-textbox"
								data-options="required:true" id="employeeName_edit">
						</div>
					</div>
				</div>
				<div class="topjui-row">
					<div class="topjui-col-sm8">
						<label class="topjui-form-label">岗位</label>
						<div class="topjui-input-block">
							<input type="text"  id="positionId_edit" data-toggle="topjui-combobox"
								data-options="required:false,
                       valueField:'id',
                       textField:'name',
                       url:'position/queryPositions.do?deptid={departmentId}'" name="positionName">
                       
                       <input type="hidden" name="positionId" />
						</div>
					</div>
				</div>
				<div class="topjui-row">
						<div class="topjui-col-sm8">
							<label class="topjui-form-label">生产单元</label>
							<div class="topjui-input-block">
								<input type="text" id="productionUnitName"  name="productionUnitName" data-toggle="topjui-combobox"
								data-options="required:false,
                       valueField:'id',
                       textField:'name',
                       url:'productionUnit/queryAllProductionUnits.do',
                       onSelect: function(rec){
                       	$('#productionUnitId').val(rec.id);
                       }
                       ">
                       <input type="hidden" name="productionUnitId" id="productionUnitId" />
							</div>
						</div>
					</div>
				<div class="topjui-row">
					<div class="topjui-col-sm12">
						<label class="topjui-form-label">描述</label>
						<div class="topjui-input-block">
							<input type="hidden" id="departmentId" name="departmentId" />
							<input type="text" name="note"
								data-toggle="topjui-textarea" id="employeeNote_edit">
						</div>
					</div>
				</div>
			</div>
		</div>
		
		</form>
	</div>
</div>