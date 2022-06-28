//array of sheetObject
var sheetObjects = new Array();
//number of sheets
var sheetCnt = 0;

var comboObjects = new Array();
var comboCnt = 0;

//var carriersComboValues=new Array();

//set function processButtonClick for event onclick on browser
document.onclick = processButtonClick;
document.onkeydown=logKey;

function logKey(key){
	var sheetObject1 = sheetObjects[0];
	var comboObject1 = comboObjects[0];
	var formObj = document.form;
	if(key.code == 'Enter'||key.code=='NumpadEnter'){
		doActionIBSheet(sheetObject1,comboObject1, formObj, IBSEARCH);
	}
}

function initControl(){
	document.getElementById('s_vndr_seq').addEventListener('keypress', function() {ComKeyOnlyNumber(this);});
}


function processButtonClick() {
	//get sheet1
	var sheetObject1 = sheetObjects[0];
	var comboObject1 = comboObjects[0];
    //get form object
    var formObj = document.form;
    //get name 
    var srcName = ComGetEvent("name");
    //do nothing if srcName is null
    if (srcName == null) {
        return;
    }
    //base on name to handle event onclick
    switch (srcName) {
    	//button Retrieve
        case "btn_Retrieve":
        	//call doActionIBSheet with action is IBSEARCH
            doActionIBSheet(sheetObject1,comboObject1, formObj, IBSEARCH);
            break;
        case "btn_calendar_dt_fr":
			 var calendar = new ComCalendar();
			 calendar.select(formObj.s_cre_dt_fm, "yyyy-MM-dd");
			 break;
        case "btn_calendar_dt_to":
			 var calendar = new ComCalendar();
			 calendar.select(formObj.s_cre_dt_to, "yyyy-MM-dd");
			 break;
        case "btn_Add":
	        sheetObject1.DataInsert();
	        break;
        case "btn_New":
        	doActionIBSheet(sheetObject1, comboObject1, formObj, IBRESET);
	        break;	
        case "btn_Delete":
			doActionIBSheet(sheetObject1, comboObject1, formObj, IBDELETE);
            break;
        case "btn_DownExcel":
        	doActionIBSheet(sheetObject1,comboObject1, formObj, IBDOWNEXCEL);
        	break;
        case "btn_Save":
            doActionIBSheet(sheetObject1, comboObject1, formObj, IBSAVE);
            break;
        default:
            break;
    }
}

/**
 * Function that is called after the JSP file is loaded
 */
function loadPage() {
    //generate Grid Layout
	for (i = 0; i < sheetObjects.length; i++) {
        ComConfigSheet(sheetObjects[i]);
        initSheet(sheetObjects[i], i + 1);
        ComEndConfigSheet(sheetObjects[i]);
    }
	
	for (i = 0; i < comboObjects.length; i++) {
        initCombo(comboObjects[i], i + 1);
    }
	initControl();
    //auto search data after loading finish.
    doActionIBSheet(sheetObjects[0], comboObjects[0], document.form, IBSEARCH);
    
    
}

/**
 * Function that configure sheet
 */
function initSheet(sheetObj, sheetNo) {
	//get sheet id
    var sheetID = sheetObj.id;
    //configure sheet base on sheet id
    switch (sheetID) {
    	//if sheet1
        case "sheet1":
            with (sheetObj) {
        	var HeadTitle="STS|Chk|Carrier|Rev. Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";
			
			SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
			
			var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
			var headers = [ { Text:HeadTitle, Align:"Center"} ];
			InitHeaders(headers, info);
			
			var cols = [ 
			            {Type:"Status",    Hidden:1, Width:50,  Align:"Center",  SaveName:"ibflag"}, 
			            {Type:"DelCheck",  Hidden:0, Width:50,  Align:"Center",  SaveName:"del_chk"},
				        {Type:"PopupEdit",     Hidden:0, Width:100, Align:"Center",  SaveName:"jo_crr_cd",     KeyField:1, UpdateEdit:0, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:3},
				        {Type:"Combo",     Hidden:0, Width:100, Align:"Center",  SaveName:"rlane_cd",      KeyField:1, UpdateEdit:0, InsertEdit:1, ComboCode:lnCds, ComboText: lnCds},
				        {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"vndr_seq",      KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N", EditLen:6},
				        {Type:"Popup",     Hidden:0, Width:50,  Align:"Center",  SaveName:"cust_cnt_cd",   KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:2}, 
					    {Type:"Popup",     Hidden:0, Width:100, Align:"Center",  SaveName:"cust_seq",      KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys:"N", EditLen: 6}, 
					    {Type:"PopupEdit", Hidden:0, Width:100, Align:"Center",  SaveName:"trd_cd",        KeyField:0, UpdateEdit:1, InsertEdit:1, AcceptKeys:"E", InputCaseSensitive:1, EditLen:3},
					    {Type:"Combo",     Hidden:0, Width:70,  Align:"Center",  SaveName:"delt_flg",      KeyField:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y",  ComboText:"N|Y"}, 
					    {Type:"Text",      Hidden:0, Width:200, Align:"Center",  SaveName:"cre_dt",        KeyField:0, UpdateEdit:0, InsertEdit:0}, 
					    {Type:"Text",      Hidden:0, Width:200, Align:"Left",    SaveName:"cre_usr_id",    KeyField:0, UpdateEdit:0, InsertEdit:0}, 
					    {Type:"Text",      Hidden:0, Width:200, Align:"Center",  SaveName:"upd_dt",        KeyField:0, UpdateEdit:0, InsertEdit:0}, 
					    {Type:"Text",      Hidden:0, Width:200, Align:"Left",    SaveName:"upd_usr_id",    KeyField:0, UpdateEdit:0, InsertEdit:0}
				    ];
			 	InitColumns(cols);
		        SetEditable(1);
		        SetWaitImageVisible(0);
                ComResizeSheet(sheetObjects[0]);
            }
            break;
    }

}

function initCombo(comboObj, comboNo) {
	comboObj.SetTitle("All")
	comboObj.SetTitleVisible(true);
	comboObj.SetEnableAllCheckBtn(true);
	comboObj.SetMultiSelect(1);
	addComboItem(comboObj, crrCds);
	checkAllItem(comboObj)
}

function addComboItem(comboObj, comboItems) {
	comboItems=comboItems.split("|");
	for (var i=0 ; i < comboItems.length ; i++) {
		comboObj.InsertItem(i, comboItems[i], comboItems[i]);
	}   		
}

/**
 * Function that add sheet object to array
 */
function setSheetObject(sheet_obj) {
    sheetObjects[sheetCnt++] = sheet_obj;
}

function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}

function getTotalRecord(xml){
	parser = new DOMParser();
    xmlDoc = parser.parseFromString(xml,"text/xml");
    return xmlDoc.getElementsByTagName("DATA")[0].getAttribute('TOTAL');
}

/**
 * Function that define transaction logic between UI and server
 * 
 * @param sheetObj
 * @param formObj
 * @param sAction
 */
function doActionIBSheet(sheetObj, comboObj, formObj, sAction) {
	//Check or configure whether to display debugging message
		//-1: Start system popup debugging
		//0: End all debugging
    sheetObj.ShowDebugMsg(0);
    
    //Handle base on sAction
    switch (sAction) {
        case IBSEARCH: // retrieve
            formObj.f_cmd.value = SEARCH;
            if(!isValidDate(formObj.s_cre_dt_fr.value,formObj.s_cre_dt_to.value)){
            	ComShowMessage("Start date must be earlier than end date");
            	break;
            }
            sheetObj.DoSearch("PRACTICE_0004GS.do", FormQueryString(formObj));
            break;
        case IBSAVE: // save
        	//set value for f_cmd input which is hidden on UI
            formObj.f_cmd.value = MULTI;
            
            //call server to save
            //ObjId.DoSearch(PageUrl, [Param], [Opt])
            sheetObj.DoSave("PRACTICE_0004GS.do", FormQueryString(formObj));
            console.log("SAVE");
            break;
        
        case IBDELETE: // save
        	if(sheetObj.RowCount("I")!=0||sheetObj.RowCount("U")!=0){
				ComShowMessage("Please save your data before deleting");
				break;
			}
        	if(getCellValue(sheetObj,"ibflag")=="I"){//Add Row(add data or not) then Delete Row
    			sheetObj.RowDelete();
    			break;
    		}
        	//set value for f_cmd input which is hidden on UI
            formObj.f_cmd.value = MULTI;
            
            //call server to save
            //ObjId.DoSearch(PageUrl, [Param], [Opt])
            sheetObj.SetCellValue(sheetObj.GetSelectRow(), "ibflag", "D");
            sheetObj.DoSave("PRACTICE_0004GS.do", FormQueryString(formObj));
            break;
            
        case IBRESET: // retrieve
        	formObj.reset();
			sheetObj.RemoveAll();
			checkAllItem(comboObj);
			doActionIBSheet(sheetObj, comboObj, formObj, IBSEARCH);
            break;
            
        case IBDOWNEXCEL:
        	//if sheet don't have data
            if (sheetObj.RowCount() < 1) {
            	//show message base on code message
                ComShowCodeMessage("COM132501");
            } else {
            	//download sheet
            		//DownCols: DownCols
            		//makeHiddenSkipCol: ignore hidden column
            	sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj)});
            }
            break;
        default:
            break;
    }
}

function sheet1_OnBeforeSearch(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(true);
}

//Handling event after searching
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
}

function customCheckAllItem(comboObj){
	var size = comboObj.GetItemCount();
    var count=0;
    for(var i=0;i<size;i++){
    	if(comboObj.GetItemCheck(i)){
    		count++;
    	}
    }
    if(count==size){
   	 document.form.s_carrier_text.value = "All";
   	 document.form.s_carrier.value = "All";
    }
}

function s_carrier_OnBlur(comboObj){
	customCheckAllItem(comboObj);
}

function s_carrier_OnChange(comboObj){
	customCheckAllItem(comboObj);
}

function removeCheckedItemByValue(value){
	var index = carriersComboValues.indexOf(Value);
	if (index !== -1) {
		carriersComboValues.splice(index, 1);
	}
}

function checkAllItem(comboObj){
	var size = comboObj.GetItemCount();
    for(var i=0;i<size;i++){
    	comboObj.SetItemCheck(i,true);
    }	
    document.form.s_carrier_text.value = "All";
	document.form.s_carrier.value = "All";
}

function isValidDate(from, to){
	if(!from||!to){
		return true;
	}
	var fDate = new Date(from);
	var tDate = new Date(to);
	return from <= to;
}

function sheet1_OnSaveEnd(sheetObj, Code, Msg){ 
	//if success reload page by calling action IBSEARCH
	if(Code>=0){
		doActionIBSheet(sheetObjects[1],comboObjects[1],document.form,IBSEARCH);
	}
	//otherwise we get all row have status 'I' (Insert)
	var invalidData=sheetObj.FindStatusRow('I');
	//slipt by ';'
	var rows=invalidData.split(';');
	
	//loop through rows
	for(var i=0;i<rows.length;i++){
		//get value of error message code at current cell
		var code = getCellValue(sheetObjects[0],"jo_crr_cd")+"-"+getCellValue(sheetObjects[0],"rlane_cd");
		//if it is invalid
		if(Msg.includes(code)){
			//change the row color to red 
			sheetObj.SetRowBackColor(rows[i],"#f58167");
		}else{
			//otherwise we change the row color to white 
			sheetObj.SetRowBackColor(rows[i],"#ffffff");
		}
	}

}

function getCellValue(sheetObj,colName){
	return sheetObj.GetCellValue(sheetObj.GetSelectRow(), colName);
}

function setCrrCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "jo_crr_cd", aryPopupData[0][3],0);
}

function sheet1_OnPopupClick(sheetObj,Row, Col){
	var colName = sheetObj.ColSaveName(Col);
	
	switch(colName){
		case "cust_cnt_cd":
		case "cust_seq":
			ComOpenPopup('/opuscntr/CUS_POPUP.do', 800, 500, 'setCustCd', '1,0,1,1,1,1', true);
			break;
		case "vndr_seq":
			ComOpenPopup('/opuscntr/COM_COM_0007.do', 900, 520, 'setVndrCd', '1,0,1', true, false);
			break;
		case "trd_cd":
			ComOpenPopup('/opuscntr/COM_COM_0012.do', 800, 500, 'setTrdCd', '1,0,0,1,1,1,1,1', true);
			break;
		case "jo_crr_cd":
			// This function open the pop-up
				// url: the url of the popup to be called
				// width: the width of the popup
				// height: the height of the popup
				// func: func return data to parent window
				// display: whether column of the grid in popup is hidden (1: visible, 0: invisible) 
				// bModal: whether the popup is modal (default: false)
			ComOpenPopup('/opuscntr/COM_ENS_0N1.do', 800, 500, 'setCrrCd', '1,0,0,1,1,1,1,1', true);
			break;
	}
}

function setCustCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_cnt_cd", aryPopupData[0][2]);
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_seq",    aryPopupData[0][3]);
}

function setVndrCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "vndr_seq", aryPopupData[0][2],0);
}

function setTrdCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "trd_cd", aryPopupData[0][3]);
}

function setCrrCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "jo_crr_cd", aryPopupData[0][3],0);
}

function sheet1_OnChange(sheetObj,Row,Col){
	var colName=sheetObj.ColSaveName(Col);
	if (colName == 'jo_crr_cd')
		isValid(sheetObj,Row,Col);
}

function isValid(sheetObj,Row,Col){
	document.form.crr_cd.value=sheetObj.GetCellValue(Row,"jo_crr_cd");
	var crrCdRtn=sheetObj.GetSearchData("COM_ENS_0N1GS.do",FormQueryString(document.form));
	if(getTotalRecord(crrCdRtn)==0){ 
		ComShowCodeMessage("COM132201",document.form.crr_cd.value);
		return false;
	}

	document.form.code.value=sheetObj.GetCellValue(Row,"vndr_seq");
	var cdRtn=sheetObj.GetSearchData("COM_COM_0007GS.do",FormQueryString(document.form));
	if(getTotalRecord(cdRtn)==0){
		ComShowCodeMessage("COM132201",document.form.code.value);
		return false;
	}
	return true;
}

