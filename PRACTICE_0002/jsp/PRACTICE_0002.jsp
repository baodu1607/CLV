<%
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_0001.jsp
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
%>

<script language="javascript">
	function setupPage(){
		loadPage();
	}
</script>
<form name="form">
    <input type="hidden" name="f_cmd">
    <div class="page_title_area clear">
        <div class="opus_design_btn">
            <button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--
		   --><button type="button" class="btn_normal" name="btn_Save" id="btn_Save">Save</button>
        </div>
    </div>

    <div class="wrap_search">
        <div class="opus_design_inquiry">
            <table>
                <tbody>
                    <tr>
                        <th width="40">SubSystem</th>
                        <td width="120"><input type="text" style="width:100px;" class="input" value=""
                                name="s_ownr_sub_sys_cd" id="s_ownr_sub_sys_cd"></td>
                        <th width="45">Cd ID</th>
                        <td><input type="text" style="width:100px;" class="input" value="" name="s_intg_cd_id"
                                id="s_intg_cd_id"></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="wrap_result">
        <div class="opus_design_grid">
            <h3 class="title_design">Master</h3>
             <div class="opus_design_btn">
            <button type="button" class="btn_accent" name="btn_Add" id="btn_Add">Row Add</button> <!--
		   --><button type="button" class="btn_accent" name="btn_Delete" id="btn_Delete">Delete Row</button>
        </div>
        </div>
        <script language="javascript">ComSheetObject('sheet1');</script>
    </div>
   <div class="wrap_result">
        <div class="opus_design_grid">
            <h3 class="title_design">Detail</h3>
             <div class="opus_design_btn">
            <button type="button" class="btn_accent" name="btn_dtl_Add" id="btn_dtl_Add">Row Add</button> <!--
		   --><button type="button" class="btn_accent" name="btn_dtl_Delete" id="btn_dtl_Delete">Delete Row</button>
        </div>
        </div>
        <script language="javascript">ComSheetObject('sheet2');</script>
    </div>
</form>
