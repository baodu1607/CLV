/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_0002.js
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  Event identification code: [Initialization]INIT=0; [input]ADD=1; [Query]SEARCH=2; [List inquiry]SEARCHLIST=3;
					[Edit] MODIFY=4; [Delete]REMOVE=5; [Remove list]REMOVELIST=6 [Multiprocessing]MULTI=7
					Other extra character constants COMMAND01=11; ~COMMAND20=30;
 ***************************************************************************************/

/*------------------The following code is added to make JSDoc well. ------------------*/
   /**
     * @fileoverview This is a JavaScript file commonly used in work, and calendar-related functions are defined.
     * @author 한진해운
     */

// common global variable
var sheetObjects = new Array();
var sheetCnt = 0;
//Define an event handler that receives and handles button click events
document.onclick = processButtonClick;
document.onkeydown=logKey;

function logKey(key){
	var sheetObject1 = sheetObjects[0];
	var formObj = document.form;
	if(key.code == 'Enter'||key.code=='NumpadEnter'){
		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
	}
}

//{processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
function processButtonClick() {
	var sheetObject1 = sheetObjects[0];
	var sheetObject2 = sheetObjects[1];
	var formObj = document.form;
	var srcName = ComGetEvent("name");
	if (srcName == null) {
		return;
	}
	switch (srcName) {
		case "btn_Retrieve":
			doActionIBSheet(sheetObject1, formObj, IBSEARCH);
			break;
			
		case "btn_Save":
			var sheet1RowCount=sheetObject1.IsDataModified();
			var sheet2RowCount=sheetObject2.IsDataModified();
			if(sheet1RowCount+sheet2RowCount==0){
				ComShowCodeMessage("COM130503");
				break;
			}
			if(!confirm("Do you want to save")){
				break;
			}
			if((sheet1RowCount&&sheet2RowCount)||sheet1RowCount){
				doActionIBSheet(sheetObject1, formObj, IBSAVE);
			}else{
				doActionIBSheet(sheetObject2, formObj, IBSAVE);
			}
			
            break;
            
		case "btn_Delete":
			if(sheetObject1.RowCount("I")!=0||sheetObject1.RowCount("U")!=0){
				ComShowMessage("Please save your data before deleting");
				break;
			}
			doActionIBSheet(sheetObject1, formObj, IBDELETE);
            break;
		case "btn_Add":
	        sheetObject1.DataInsert();
	        break;
		case "btn_dtl_Add":
	        var cdID=getCellValue(sheetObject1, "intg_cd_id");
	        console.log(cdID);
	        if(cdID==-1||cdID==""){
	        	ComShowCodeMessage("COM12189");
	        	break;
	        }
	        sheetObject2.DataInsert();
	        sheetObject2.SetCellValue(sheetObject2.GetSelectRow(), "intg_cd_id", cdID);
	        console.log(cdID);
	        break;  
		case "btn_dtl_Delete":
			if(sheetObject2.RowCount()==0){
				ComShowCodeMessage("COM12189");
				break;
			}
			if(sheetObject2.RowCount("U")!=0||sheetObject2.RowCount("I")!=0){
				ComShowMessage("Please save your data before deleting");
				break;
			}
			doActionIBSheet(sheetObject2, formObj, IBDELETE);
	        break;  
	    
		default:
			break;
	}
}

//{loadPage} functions that calls a common function that sets the default settings of the sheet
//It is the first called area when fire jsp onload event
function loadPage() {
	// generate Grid Layout
	for (i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}

	// auto search data after loading finish.
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
}

//{initSheet} functions that define the basic properties of the sheet on the screen
function initSheet(sheetObj, sheetNo) {
	// get sheet id
	var sheetID = sheetObj.id;
	// configure sheet base on sheet id
	switch (sheetID) {
	// if sheet1
	case "sheet1":
		with (sheetObj) {
			var HeadTitle="STS|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date" ;
	        var headCount=ComCountHeadTitle(HeadTitle);
	
	        SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
	
	        var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	        var headers = [ { Text:HeadTitle, Align:"Center"} ];
	        InitHeaders(headers, info);
	        
	        var cols = [ 
	            {Type:"Status", Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"ibflag",          KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1 },
	            {Type:"Text",   Hidden:0, Width:70,  Align:"Center", ColMerge:0, SaveName:"ownr_sub_sys_cd", KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
	            {Type:"Text",   Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_id",      KeyField:1, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
	            {Type:"Text",   Hidden:0, Width:200, Align:"Left",   ColMerge:0, SaveName:"intg_cd_nm",      KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1 },
	            {Type:"Text",   Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"intg_cd_len",     KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, AcceptKeys : "N" },
	            {Type:"Combo",  Hidden:0, Width:100, Align:"Center", ColMerge:0, SaveName:"intg_cd_tp_cd",   KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, ComboCode:"G|T", ComboText:"General|TY"},
	            {Type:"Text",   Hidden:0, Width:150, Align:"Left",   ColMerge:0, SaveName:"mng_tbl_nm",      KeyField:1, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:1 },
	            {Type:"Text",   Hidden:0, Width:350, Align:"Left",   ColMerge:0, SaveName:"intg_cd_desc",    KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1 },
	            {Type:"Combo",  Hidden:0, Width:40,  Align:"Center", ColMerge:0, SaveName:"intg_cd_use_flg", KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y", ComboText:"N|Y"},
	            {Type:"Text",   Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"cre_usr_id",      KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:0 },
	            {Type:"Date",   Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"cre_dt",          KeyField:0, CalcLogic:"", Format:"Ymd", PointCount:0, UpdateEdit:0, InsertEdit:0 },
	            {Type:"Text",   Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"upd_usr_id",      KeyField:0, CalcLogic:"", Format:"",    PointCount:0, UpdateEdit:0, InsertEdit:0 },
	            {Type:"Date",   Hidden:0, Width:80,  Align:"Center", ColMerge:0, SaveName:"upd_dt",          KeyField:0, CalcLogic:"", Format:"Ymd", PointCount:0, UpdateEdit:0, InsertEdit:0 } 
	        ]; 
	        InitColumns(cols);7
	        SetWaitImageVisible(0);
	        SetSheetHeight(300);
		}
		break;

	case "sheet2":
		with (sheetObj) {
			var HeadTitle="STS|Cd ID|Cd Val|Display Name|Description Remark|Order" ;
	        var headCount=ComCountHeadTitle(HeadTitle);
	        
	        SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:0 } );
	
	        var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	        var headers = [ { Text:HeadTitle, Align:"Center"} ];
	        InitHeaders(headers, info);
	
	        var cols = [ 
	            {Type:"Status", Hidden:1, Width:60,  Align:"Center", ColMerge:0, SaveName:"ibflag",              KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_id",          KeyField:1, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:0, InsertEdit:0 },
			    {Type:"Text",   Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_ctnt",    KeyField:1, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:0, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:200, Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_desc", KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:600, Align:"Left",   ColMerge:0, SaveName:"intg_cd_val_desc",    KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_seq",  KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 } 
			];
	             
	        InitColumns(cols);
	        SetWaitImageVisible(0);
	        SetSheetHeight(300);
		}
		break;
	}

}

//{setSheetObject} to put sheet objects in global variable "sheetObjects"
function setSheetObject(sheet_obj) {
	sheetObjects[sheetCnt++] = sheet_obj;
}

//{doActionIBSheet} functions that define transaction logic between UI and server
function doActionIBSheet(sheetObj, formObj, sAction) {
	sheetObj.ShowDebugMsg(0);

	switch (sAction) {
	case IBSEARCH: // retrieve
		ComOpenWait(true);

		if (sheetObj.id == "sheet1") {
			formObj.f_cmd.value = SEARCH;
			sheetObj.DoSearch("PRACTICE_0002GS.do", FormQueryString(formObj));
			clearSheetDetail();
		} else if (sheetObj.id == "sheet2") {
			formObj.f_cmd.value = SEARCH01;
//			console.log(FormQueryString(formObj));
			sheetObj.DoSearch("PRACTICE_0002GS.do", FormQueryString(formObj));
			formObj.s_intg_cd_id.value = "";
		}
		// disable waiting image on sheet
		ComOpenWait(false);
	break;
	
	case IBSAVE: 
		if (sheetObj.id == "sheet1"){
			if(isDuplicate(sheetObj,"intg_cd_id")){
				break;
			}
			formObj.f_cmd.value = MULTI;
		}else{
			if(isDuplicate(sheetObj,"intg_cd_val_ctnt")){
				break;
			}
			formObj.f_cmd.value = MULTI01;
			 
		}
		var saveString = sheetObj.GetSaveString();
		 var rtnData = sheetObj.GetSaveData("PRACTICE_0002GS.do", saveString, FormQueryString(formObj));
	     sheetObj.LoadSaveData(rtnData);
    break;
	case IBDELETE: // save
		if(getCellValue(sheetObj,"ibflag")=="I"){//Add Row(add data or not) then Delete Row
			sheetObj.RowDelete();
			break;
		}
		if (sheetObj.id == "sheet1"){
			document.form.s_intg_cd_id.value = getCellValue(sheetObj,"intg_cd_id");
			formObj.f_cmd.value=SEARCH01;
			var sxml=sheetObj.GetSearchData("PRACTICE_0002GS.do", FormQueryString(formObj));
			formObj.s_intg_cd_id.value = "";
			if(ComGetEtcData(sxml,"hasValue")=="true"){
				if(!confirm("Do you want to delete " + getCellValue(sheetObj,"intg_cd_id")+". This one has detail record(s)")){
					break;
				}
			}else{
				if(!confirm("Do you want to delete " + getCellValue(sheetObj,"intg_cd_id"))){
					break;
				}
			}
			formObj.f_cmd.value = MULTI;
		}else{
			if(!confirm("Do you want to delete " + getCellValue(sheetObj,"intg_cd_val_ctnt"))){
				break;
			}
			formObj.f_cmd.value = MULTI01;
		}
        sheetObj.SetCellValue(sheetObj.GetSelectRow(), "ibflag", "D");
        var saveString = sheetObj.GetSaveString();
        var rtnData = sheetObj.GetSaveData("PRACTICE_0002GS.do", saveString, FormQueryString(formObj));
        sheetObj.LoadSaveData(rtnData);
        clearSheetDetail();
        break;
       
	default:
		break;
	}
}

function getCellValue(sheetObj,colName){
	return sheetObj.GetCellValue(sheetObj.GetSelectRow(), colName);
}

function clearSheetDetail(){
	sheetObjects[1].RemoveAll();
}

//handling event double click when double click one row in sheet1
function sheet1_OnDblClick(sheetObj, Row) {
	if(getCellValue(sheetObj,"ibflag")=='I'){
		return;
	}
	document.form.s_intg_cd_id.value = getCellValue(sheetObj,"intg_cd_id");
	doActionIBSheet(sheetObjects[1],document.form,IBSEARCH);
}

//Handling event after searching sheet1
function sheet1_OnSearchEnd(){
	ComOpenWait(false);
}

function isDuplicate(sheetObj,colName) {  
	var searchText = getCellValue(sheetObj,colName);
	if(searchText==""){
		ComShowCodeMessage("COM12114",colName);
		return true;
	}
    //Check duplicate on Client side
    var findText=sheetObj.FindText(colName,searchText);
    if(findText!=-1&&findText!=sheetObj.GetSelectRow()){
    	 ComShowCodeMessage("COM131302",searchText);
         return true;
    }
    
    return false;
}

function sheet1_OnChange(sheetObj,Row,Col) {
	if(sheetObj.ColSaveName(Col) == "intg_cd_id"){
		isDuplicate(sheetObj,"intg_cd_id");
	}
}

function sheet2_OnChange(sheetObj,Row,Col) {
	if(sheetObj.ColSaveName(Col) == "intg_cd_val_ctnt"){
		isDuplicate(sheetObj,"intg_cd_val_ctnt");
	}
}

//Handling event after save sheet1
function sheet1_OnSaveEnd(sheetObj, Code, Msg){ 
	//if success reload page by calling action IBSEARCH
	if(Code>=0){
		if(sheetObjects[1].IsDataModified()){
			doActionIBSheet(sheetObjects[1], document.form, IBSAVE);
		}
		doActionIBSheet(sheetObj, document.form, IBSEARCH);
	}
	//otherwise we get all row have status 'I' (Insert)
	var invalidData=sheetObj.FindStatusRow('I');
	
	//slipt by ';'
	var rows=invalidData.split(';');
	
	//loop through rows
	for(var i=0;i<rows.length;i++){
		//get value of error message code at current cell
		var msgCd=sheetObj.GetCellValue(rows[i],"intg_cd_id");
		//if it is invalid
		if(Msg.includes(msgCd)){
			//change the row color to red 
			sheetObj.SetRowBackColor(rows[i],"#f58167");
		}else{
			//otherwise we change the row color to white 
			sheetObj.SetRowBackColor(rows[i],"#ffffff");
		}
	}

}

//Handling event after save sheet2
function sheet2_OnSaveEnd(sheetObj, Code, Msg){ 
	//if success reload page by calling action IBSEARCH
	if(Code>=0){
		document.form.s_intg_cd_id.value = getCellValue(sheetObjects[0],"intg_cd_id");
		doActionIBSheet(sheetObjects[1],document.form,IBSEARCH);
	}
	//otherwise we get all row have status 'I' (Insert)
	var invalidData=sheetObj.FindStatusRow('I');
	
	//slipt by ';'
	var rows=invalidData.split(';');
	
	//loop through rows
	for(var i=0;i<rows.length;i++){
		//get value of error message code at current cell
		var msgCd= getCellValue(sheetObjects[1],"intg_cd_val_ctnt");
		//if it is invalid
		if(Msg.includes(msgCd)){
			//change the row color to red 
			sheetObj.SetRowBackColor(rows[i],"#f58167");
		}else{
			//otherwise we change the row color to white 
			sheetObj.SetRowBackColor(rows[i],"#ffffff");
		}
	}

}
