<%
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.jsp
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.22
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.esm.clv.doutraining.moneymgmt.event.EsmDou0108Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	EsmDou0108Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;			//서버에서 발생한 에러
	String strErrMsg = "";						//에러메세지
	int rowCount	 = 0;						//DB ResultSet 리스트의 건수

	String successFlag = "";
	String codeList  = "";
	String pageRows  = "100";
	String partnerCodes = "";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	Logger log = Logger.getLogger("com.clt.apps.moneymgmt.MoneyMgmt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (EsmDou0108Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);
		
		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");

		partnerCodes = eventResponse.getETCData("partnerCodes");
	}catch(Exception e) {
		out.println(e.toString());
	}
%>
<script language="javascript">
	var partnerCodes = "All|<%=partnerCodes%>";
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
	}
</script>
<script language="javascript" type="text/javascript" src="apps/opus/esm/clv/doutraining/moneymgmt/script/CoMoneyMgmt.js"></script>
<form name="form">
	<input type="hidden" name="f_cmd">
	<input type="hidden" name="pagerows">
	<!-- page_title_area(S) -->
	<div class="page_title_area clear">
		<!-- page_title(S) -->
		<h2 class="page_title"><button type="button"><span id="title">ESM_DOU_0108</span></button></h2>
		<!-- page_title(E) -->
		<!-- opus_design_btn(S) -->
		<div class="opus_design_btn"><!-- 
		--><button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!-- 
		--><button type="button" class="btn_normal" name="btn_New" id="btn_New">New</button><!--  
		--><button type="button" class="btn_normal" name="btn_DownExcel" id="btn_DownExcel">Down Excel</button><!-- 
		--><button type="button" class="btn_normal" name="btn_DownExcel2" id="btn_DownExcel2">DownExcel2</button>
		</div>
		<!-- opus_design_btn(E) -->
		<!-- page_location(S) -->
		<div class="location">
			<span id="navigation"></span>
		</div>
		<!-- page_location(E) -->
	</div>
	<!-- page_title_area(E) -->

	<!-- wrap_search(S) -->
	<div class="wrap_search_tab">
		<!-- opus_design_inquiry(S) -->
		<div class="opus_design_inquiry wFit">
			<table>
				<tbody>
					<colgroup>
						<col width="80px">
						<col width="100px">
						<col width="105px">
						<col width="75px">
						<col width="55px">
						<col width="75px">
						<col width="55px">
						<col width="*" />
					</colgroup>
					<tr class="h23">
						<th>Year Month</th>
						<td><input type="text" style="width: 100px;" class="input1" value="" name="s_date_fr" id="date_fr" readonly><!--
							--><button type="button" class="btn_left" name="btn_date_fr_down" id="btn_date_fr_down"></button><!--
							--><button type="button" class="btn_right" name="btn_date_fr_up" id="btn_date_fr_up"></button><!--
							--><input type="text" style="width: 100px;" class="input1" value="" name="s_date_to" id="date_to" readonly><!--
							--><button type="button" class="btn_left" name="btn_date_to_down" id="btn_date_to_down"></button><!--
							--><button type="button" class="btn_right" name="btn_date_to_up" id="btn_date_to_up"></button>
						</td>
						<th>Partner</th>
						<td>
							<script type="text/javascript">ComComboObject('s_partner_code', 1, 100, 0, 0);</script>
						</td>
						<th>Lane</th>
						<td>
							<script type="text/javascript">ComComboObject('s_lane_code', 1, 100, 0, 0);</script>
						</td>
						<th>Trade</th>
						<td>
							<script type="text/javascript">ComComboObject('s_trade_code', 1, 100, 0, 0);</script>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- opus_design_inquiry(E) -->
	</div>
	<!-- wrap_search(E) -->

	<!-- wrap_result(S) -->
        <div class="wrap_result">
            <div class="opus_design_tab sm">
                <script type="text/javascript">ComTabObject('tab1')</script>
            </div>
               <!-- opus_design_grid(S) -->
               <div class="opus_design_grid  clear" name="tabLayer" id="tabLayer">
               	<script language="javascript">ComSheetObject('sheet1');</script>
               </div>
               <!-- opus_design_grid(E) -->
           
               <!-- opus_design_grid(S) -->
               <div class="opus_design_grid  clear" name="tabLayer" id="tabLayer">
               	<script language="javascript">ComSheetObject('sheet2');</script>
               </div>
               <!-- opus_design_grid(E) -->
            </div>
      
      <!-- wrap_result(E) -->
</form>