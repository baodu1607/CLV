var sheetObjects=new Array();
var sheetCnt=0;

var comboObjects = new Array();
var comboCnt = 0;

var tabObjects=new Array();
var tabCnt=0 ;

var beforetab=1;

var laneCodes="";
var tradeCodes="";

var IBDOWNEXCEL2=14;

var cfmGtDtOver3M=false;

var searchDetail="";
var searchSummary="";
var firstLoad=true;

document.onclick=processButtonClick;

function processButtonClick() {
	var sheetObject1=sheetObjects[0];
	var sheetObject2=sheetObjects[1];
    var formObject=document.form;
    try {
    	var srcName=ComGetEvent("name");
        switch(srcName) {
	        case "btn_Retrieve":
	        	if (!cfmGtDtOver3M&&checkOverThreeMonth()){
	        		if (confirm("Year Month over 3 months, do you really want to get data?")){
	        			cfmGtDtOver3M = true;
    	    		}else{
    	    			break;
    	    		}
	        	}
	    		doActionIBSheet(getCurrentSheet(), formObject, IBSEARCH);
//	    		doActionIBSheet(sheetObject2, formObject, IBSEARCH);
	        	break;
	        case "btn_date_fr_up":
	        	if(!isValidDate()){
	        		ComShowMessage("Start date must be earlier than end date");
	            	break;
	        	}
	        	addMonth(formObject.date_fr, 1);
//	        	yearmonth_onchange();
	        	break;
	        case "btn_date_fr_down":
	        	addMonth(formObject.date_fr, -1);
//	        	yearmonth_onchange();
//	        	excuteCheck();
	        	break;
	        case "btn_date_to_up":
	        	addMonth(formObject.date_to, 1);
//	        	yearmonth_onchange();
//	        	excuteCheck();
	        	break;
	        case "btn_date_to_down":
	        	if(!isValidDate()){
	        		ComShowMessage("Start date must be earlier than end date");
	            	break;
	        	}
	        	addMonth(formObject.date_to, -1);
//	        	yearmonth_onchange();
	        	break;
	        case "btn_New":
	        	doActionIBSheet(sheetObject1,formObject,IBRESET);
	        	break;
	        case "btn_DownExcel":
				doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
				break;
	        case "btn_DownExcel2":
				doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL2);
				break;
        }
    }
    catch(e) {
    	if( e == "[object Error]") {
    		ComShowMessage(OBJECT_ERROR);
    	} 
    	else {
    		ComShowMessage(e);
    	}
    }
}

function loadPage(){
	initCalender();
//	initControl();
//	document.getElementById("s_partner_code_text").onchange = function(e) {console.log(e.target.value)};
//	document.getElementById("s_partner_code_text").addEventListener('mouseenter', e => {
//		  console.log(e);
//		});
	for (var i = 0; i < comboObjects.length; i++) {
		initCombo(comboObjects[i], i);
	}
	
	for(i = 0; i < tabObjects.length; i++){
		initTab(tabObjects[i], i + 1);
		tabObjects[i].SetSelectedIndex(0);
	}
	
	for(i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);	
}

function setSheetObject(sheet_obj){
	sheetObjects[sheetCnt++] = sheet_obj;
}

function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}

function setTabObject(tab_obj){
	tabObjects[tabCnt++]=tab_obj;
}

function initSheet(sheetObj,sheetNo) {
	switch (sheetNo) {
		case 1:
			with(sheetObj){    
				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name"
	
	            SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
	
	            var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	            var headers = [ { Text: HeadTitle1, Align: "Center"},
	                            { Text: HeadTitle2, Align: "Center"}];
	            InitHeaders(headers, info);
	            
	            var cols = [ 
		       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center",   ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: ""},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: ""},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: ""},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: ""},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: ""},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: ""}
		       	             ];
	            InitColumns(cols);
				SetEditable(0);
				SetWaitImageVisible(0);
				SetSheetHeight(500);
			}
			break;
		case 2:
			with(sheetObj){
				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
				
				SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
				
	            var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	            var headers = [ { Text: HeadTitle1, Align: "Center"},
	                            { Text: HeadTitle2, Align: "Center"}];
	            InitHeaders(headers, info);
	            
	            var cols = [ 
		       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: ""}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: ""},
		       	             { Type: "Combo",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rev_exp",         KeyField: 1, Format: "", ComboText: "Rev|Exp", ComboCode: "R|E"},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "item",        	 KeyField: 1, Format: ""},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: ""},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: ""},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: ""},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: ""},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: ""}
		       	             ];
		            InitColumns(cols);
					SetEditable(0);
					SetWaitImageVisible(0);
					SetSheetHeight(500);
			}
			break;
	}
	resizeSheet();
}

function initTab(tabObj , tabNo) {
	switch(tabNo) {
	case 1:
		with (tabObj) {
			var cnt=0 ;
				InsertItem( "Summary" , "");
				InsertItem( "Detail" , "");
		}
		break;
	}
}

function tab1_OnChange(tabObj, nItem)
{
	var objs=document.all.item("tabLayer");
	objs[nItem].style.display="Inline";		
	//--------------- this is important! --------------------------//
	for(var i = 0; i<objs.length; i++){
		  if(i != nItem){
		   objs[i].style.display="none";
		   objs[beforetab].style.zIndex=objs[nItem].style.zIndex - 1 ;
		  }
		}
	//------------------------------------------------------//
	beforetab=nItem;
	handleOnchangeTab();
    resizeSheet();
} 

function initCombo(comboObj, comboNo) {
	switch(comboNo){
	    case 0:
//	    	comboObj.SetTitle("All")
//	    	comboObj.SetTitleVisible(true);
//	    	comboObj.SetEnableAllCheckBtn(true);
	    	comboObj.SetMultiSelect(1);
	    	addComboItem(comboObj, partnerCodes);
	    	comboObj.SetItemCheck(0,true,0);
//	    	checkAllItem(comboObj, comboNo , i);
	    	comboObjects[1].SetEnable(false);
	    	comboObjects[2].SetEnable(false);
	    	break;
	    case 1:
	    	addComboItem(comboObj, laneCodes);
//	    	setLaneValue("All");
		break;
	    case 2:
	    	addComboItem(comboObj, tradeCodes);
//	    	setTradeValue("All");
		break;
	}
	
}

function checkAllItem(comboObj, status){
	var size = comboObj.GetItemCount();
    for(var i=1;i<size;i++){
    	comboObj.SetItemCheck(i,status,0);
    }	  
}

function getPartnerValue(){
	return document.form.s_partner_code_text.value;
}

function setPartnerValue(value){
	document.form.s_partner_code_text.value = value;
	document.form.s_partner_code.value = value;
}

function getLaneValue(){
	return document.form.s_lane_code_text.value;
}

function setLaneValue(value){
	document.form.s_lane_code_text.value = value;
	document.form.s_lane_code.value = value;
}

function getTradeValue(){
	return document.form.s_trade_code_text.value;
}

function setTradeValue(value){
	document.form.s_trade_code_text.value = value;
	document.form.s_trade_code.value = value;
}

function isCheckAllItem(comboObj){
	var size = comboObj.GetItemCount();
    var count=1;
    for(var i=1;i<size;i++){
    	if(comboObj.GetItemCheck(i)){
    		count++;
    	}
    }
    return count==size;
}

function addComboItem(comboObj, comboItems) {
	comboItems= comboItems?comboItems.split("|"):[];
	for (var i=0 ; i < comboItems.length ; i++) {
		comboObj.InsertItem(i, comboItems[i], comboItems[i]);
	}   		
}

function enableLaneCombo(){
	var partnerValue=getPartnerValue();
	if(partnerValue=='All'){
		comboObjects[1].SetEnable(false);
		setLaneValue("");
	}else{
		comboObjects[1].SetEnable(true);
		generateComboData(1);
	}
}

function enableTradeCombo(){
	var laneValue=getLaneValue();
	if(laneValue==""){
		comboObjects[2].SetEnable(false);
		setTradeValue("");
	}else{
		comboObjects[2].SetEnable(true);
		generateComboData(2);
	}
}

function generateComboData(comboNo){
	
	comboObj = comboObjects[comboNo];
	comboObj.RemoveAll();
	if(comboNo==1){//Lane combo
		document.form.f_cmd.value = SEARCH02;
		var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(document.form));
		laneCodes = ComGetEtcData(xml,"laneCodes");
	}else{//Trade combo
		document.form.f_cmd.value = SEARCH03;
		var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(document.form));
		tradeCodes = ComGetEtcData(xml,"tradeCodes");
	}
	initCombo(comboObj, comboNo);
}

function isValidDate(){
//	var from=document.form.date_fr.value;
//	var to =document.form.date_to.value;
//	fDate = new Date(from);
//	var tDate = new Date(to);
	var from=new Date(document.form.date_fr.value);
	var to = new Date(document.form.date_to.value);
	return from < to;
}

function s_partner_code_OnCheckClick(comboObj,index,value,status){
	if(status){
		if(value=="All"){
			checkAllItem(comboObj,false);
//			setLaneValue("");
		}else if(comboObj.GetItemCheck(0)){
			comboObj.SetItemCheck(0,false,false);
		}else if(isCheckAllItem(comboObj)){
			checkAllItem(comboObj,false);
			comboObj.SetItemCheck(0,true,false);
//			setLaneValue("");
		}
	}else if(getPartnerValue()==""){
		comboObj.SetItemCheck(0,true,false);
//		setLaneValue("");
	}
	enableLaneCombo();
	enableTradeCombo();
}
function s_lane_code_OnChange(){
	enableTradeCombo();
}
function s_trade_code_OnCheckClick(comboObj){
//	renameComboValue(comboObj,3);
}

//{initCalender} functions that define the basic properties of the date on the screen
function initCalender(){
	var formObj = document.form;
	var ymTo = ComGetNowInfo("ym","-");
	var ymFrom = ComGetDateAdd(ymTo + "-01","M",-1);
	formObj.s_date_to.value = GetDateFormat(ymTo,"ym");
	formObj.s_date_fr.value = GetDateFormat(ymFrom,"ym");
}
function addMonth(obj, month){
	if (obj.value != ""){
			obj.value = ComGetDateAdd(obj.value + "-01", "M", month).substr(0,7);
	}
}
 //Get format date
function GetDateFormat(obj, sFormat){
	var sVal = String(getArgValue(obj));
	sVal = sVal.replace(/\/|\-|\.|\:|\ /g,"");
	if (ComIsEmpty(sVal)) return "";
	
	var retValue = "";
	switch(sFormat){
		case "ym":
			retValue = sVal.substring(0,6);
			break;
	}
	retValue = ComGetMaskedValue(retValue,sFormat);
	return retValue;
}
function doActionIBSheet(sheetObj,formObj,sAction) {
	sheetObj.ShowDebugMsg(0);
	switch (sAction) {
		case IBSEARCH: // retrieve
 			if (sheetObj.id == "sheet1" ) {
 				searchSummary = getCurrentSearchOption();
 				formObj.f_cmd.value = SEARCH;
			}
			else if (sheetObj.id == "sheet2"){
				searchDetail = getCurrentSearchOption();
				formObj.f_cmd.value = SEARCH01;
			}
 			sheetObj.DoSearch("ESM_DOU_0108GS.do", FormQueryString(formObj));
			break;
		case IBRESET:
			resetForm(formObj);
			break;
		case IBDOWNEXCEL:	
			if (sheetObj.RowCount() < 1) {
				ComShowCodeMessage("COM132501");
			} else {
				sheetObj.Down2ExcelBuffer(true);
				sheetObj.Down2Excel({ FileName: 'Report', SheetName: ' sheet1', DownCols: makeHiddenSkipCol(sheetObj), SheetDesign: 1, Merge: 1 });
				sheetObjects[1].Down2Excel({ SheetName: ' sheet2', DownCols: makeHiddenSkipCol(sheetObjects[1]), Merge: 1 });
				sheetObj.Down2ExcelBuffer(false);
				
			}
			break;
		case IBDOWNEXCEL2:	
			formObj.f_cmd.value = COMMAND01;
			formObj.action="ESM_DOU_0108GS.do";
			formObj.submit();
			break;
	}
}


function resizeSheet() {
	for (var i = 0; i < sheetObjects.length; i++) {
		ComResizeSheet(sheetObjects[i]);
	}
}

function resetForm(formObj){
	ComOpenWait(true);
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	formObj.reset();
	s_partner_code.SetSelectIndex(0);
	s_lane_code.SetEnable(false);
	s_trade_code.SetEnable(false);
	initCalender();
	ComOpenWait(false);
}

function sheet1_OnBeforeSearch(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(true);
}

//Handling event after searching
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
}

function checkOverThreeMonth() {
	var months;
	var from=new Date(document.form.date_fr.value);
	var to = new Date(document.form.date_to.value);
    months = (to.getFullYear() - from.getFullYear()) * 12;
    months -= from.getMonth();
    months += to.getMonth();
//    return months <= 0 ? 0 : months;
    return months < 3 ? false : true;
}

function getCurrentSheet() {
	return sheetObjects[beforetab];
}

function getCurrentSearchOption(){
	var currentSearchOption="";
	with(document.form){
		currentSearchOption+=date_fr.value;
		currentSearchOption+=date_to.value;
		currentSearchOption+=s_partner_code.value;
		currentSearchOption+=s_lane_code.value;
		currentSearchOption+=s_trade_code.value;
	}
	return currentSearchOption;
}

function handleOnchangeTab(){
	if(firstLoad) {
		firstLoad=false;
		return;
	}
	
	var currentSheet=getCurrentSheet();
	var formQuery = getCurrentSearchOption();

	if(searchSummary!=formQuery&&searchSummary!=searchDetail){
		if (confirm("Search data was changed. Do you want to retrieve?")) {
			doActionIBSheet(currentSheet,document.form,IBSEARCH);
		} else {
			return;
		}
	}
	if(searchSummary==searchDetail&&searchSummary!=formQuery){
		if (confirm("Search data was changed. Do you want to retrieve?")) {
			doActionIBSheet(currentSheet,document.form,IBSEARCH);
		} else {
			return;
		}
	}
	if(currentSheet.id=="sheet1"){//Summary Sheet
		if(searchSummary!=formQuery){
//			searchSummary=formQuery;
			doActionIBSheet(currentSheet, document.form, IBSEARCH);
		}
	}else{
		if(searchDetail!=formQuery){
//			searchDetail=formQuery;
			doActionIBSheet(currentSheet, document.form, IBSEARCH);
		}
	}
}
