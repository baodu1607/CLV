//array of sheetObject
var sheetObjects = new Array();
//number of sheets
var sheetCnt = 0;
//set function processButtonClick for event onclick on browser
document.onclick = processButtonClick;

//handle event onclick
function processButtonClick() {
	//get sheet1
    var sheetObject1 = sheetObjects[0];
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
            doActionIBSheet(sheetObject1, formObj, IBSEARCH);
            break;
            
        //button Save
        case "btn_Save":
        	// form need to be validated
            if (validateForm(sheetObject1)) {
            	//call doActionIBSheet with action is IBSAVE
                doActionIBSheet(sheetObject1, formObj, IBSAVE);
            }
            break;
            
        //button Add Row   
        case "btn_Add":
        	//new row will be displayed below the selected row
            sheetObject1.DataInsert();
            break;
            
        //button Down Excel     
        case "btn_DownExcel":
        	//if sheet don't have data
            if (sheetObject1.RowCount() < 1) {
            	//show message base on code message
                ComShowCodeMessage("COM132501");
            } else {
            	//download sheet
            		//DownCols: DownCols
            		//makeHiddenSkipCol: ignore hidden column
                sheetObject1.Down2Excel({DownCols: makeHiddenSkipCol(sheetObject1)});
            }
            break;
        //do nothing
        default:
            break;
    }
}

//Function that is called after the JSP file is loaded
function loadPage() {
    //generate Grid Layout
    for (i = 0; i < sheetObjects.length; i++) {
        ComConfigSheet(sheetObjects[i]);
        initSheet(sheetObjects[i], i + 1);
        ComEndConfigSheet(sheetObjects[i]);
    }

    //auto search data after loading finish.
    doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
}

//Function that configure sheet
function initSheet(sheetObj, sheetNo) {
	//get sheet id
    var sheetID = sheetObj.id;
    //configure sheet base on sheet id
    switch (sheetID) {
    	//if sheet1
        case "sheet1":
            with (sheetObj) {
        		//Define header title
        		//String of texts to display in header,adjoined by "|"
                var HeadTitle = "STS|Del|Msg Cd|Msg Type|Msg Level|Message|Description";
                
                //SearchMode: Configure search mode (Default: 2)
                //Page: Number of rows to display in one page (Default=20)
                //Lazy load mode, only display 20 rows in one page
                SetConfig({ SearchMode: 2, Page: 20 });

                //Sort: Whether to allow sorting by clicking on the header (Default=1)
                //ColMove: Whether to allow column movement in header (Default=1)
                //HeaderCheck: Whether the CheckAll in the header is checked (Default=1)
                //ColResize: Whether to allow resizing of column width (Default=1)
                var info = { Sort: 1, ColMove: 1, HeaderCheck: 0, ColResize: 1 };
                
                //Align: Data alignment
                	//Left
                	//Center
                	//Right
                var headers = [{ Text: HeadTitle, Align: "Center" }];
                
                //initialize headers base on above configure
                InitHeaders(headers, info);
                
                //configure columns
                //Type: type of a column
                	//Status: Data that display and contain transaction status
                	//DelCheck: Checkbox type data that only checks deletion
                	//Text Basic: string data
                	//Combo: Uneditable combo data
                //Hidden: Hidden or not 
                	//1: Hidden
                	//0: Not
                //Width: Width of the column
                //Align: Data alignment
                	//Left
                	//Center
                	//Right
                //SaveName: SaveName of the column
                //KeyField: Required fields
                //UpdateEdit: Whether to allow data editing when transaction is in "Search" state 
                //InsertEdit Whether to allow data editing when transaction is in "Insert" state
                //ComboText: Combo list text string group
                //ComboCode: Combo list code group
                var cols = [{ Type: "Status", Hidden: 1, Width: 30, Align: "Center", SaveName: "ibflag" },
                { Type: "DelCheck", Hidden: 0, Width: 45, Align: "Center", SaveName: "DEL", KeyField: 0, UpdateEdit: 1, InsertEdit: 1 },
                { Type: "Text", Hidden: 0, Width: 80, Align: "Center", SaveName: "err_msg_cd", KeyField: 1, UpdateEdit: 0, InsertEdit: 1 },
                { Type: "Combo", Hidden: 0, Width: 80, Align: "Center", SaveName: "err_tp_cd", KeyField: 1, UpdateEdit: 1, InsertEdit: 1, ComboText: "Server|UI|Both", ComboCode: "S|U|B" },
                { Type: "Combo", Hidden: 0, Width: 80, Align: "Center", SaveName: "err_lvl_cd", KeyField: 1, UpdateEdit: 1, InsertEdit: 1, ComboText: "ERR|WARNING|INFO", ComboCode: "E|W|I" },
                { Type: "Text", Hidden: 0, Width: 400, Align: "Left", SaveName: "err_msg", KeyField: 1, UpdateEdit: 1, InsertEdit: 1, MultiLineText: 1 },
                { Type: "Text", Hidden: 0, Width: 250, Align: "Left", SaveName: "err_desc", UpdateEdit: 1, InsertEdit: 1 }];

                //initialize columns base on above configure
                InitColumns(cols);
                
                //Check or configure whether to display waiting image during processing.
                SetWaitImageVisible(0);
                
                //Resize sheet to fit window
                ComResizeSheet(sheetObjects[0]);
            }
            break;
    }

}

//Function that add sheet object to array
function setSheetObject(sheet_obj) {
    sheetObjects[sheetCnt++] = sheet_obj;
}

//Function that define transaction logic between UI and server
function doActionIBSheet(sheetObj, formObj, sAction) {
	//Check or configure whether to display debugging message
		//-1: Start system popup debugging
		//0: End all debugging
    sheetObj.ShowDebugMsg(0);
    
    //Handle base on sAction
    switch (sAction) {
        case IBSEARCH: // retrieve
        	//set value for f_cmd input which is hidden on UI
            formObj.f_cmd.value = SEARCH;
            
            //enable waiting image on sheet
            ComOpenWait(true);
            
            //call server to search
            //ObjId.DoSearch(PageUrl, [Param], [Opt])
            sheetObj.DoSearch("PRACTICE_0001GS.do", FormQueryString(formObj));
            
            //disable waiting image on sheet
            ComOpenWait(false);
            break;
        case IBSAVE: // save
        	//set value for f_cmd input which is hidden on UI
            formObj.f_cmd.value = MULTI;
            
            //call server to save
            //ObjId.DoSearch(PageUrl, [Param], [Opt])
            sheetObj.DoSave("PRACTICE_0001GS.do", FormQueryString(formObj));
            break;
        default:
            break;
    }
}

//Function that validate form
function validateForm(sheetObj) {
    // Create variables regex with the first 3 characters are upper and the last 5 numbers from 0 to 9
    var msgCd = sheetObj.GetCellValue(sheetObj.GetSelectRow(), "err_msg_cd");
    var regex = new RegExp("^([A-Z]{3})([0-9]{5})$");
    if (!regex.test(msgCd)) {
    	//Show error message base on code
        ComShowCodeMessage("COM132201",msgCd);
        return false;
    }
    return true;
}

//Event fires when the cell editing is completed and the previous value has been updated.
function sheet1_OnChange(sheetObj) {
    validateForm(sheetObj);
}

//Event fires when saving is completed using saving function
//This event can fire when DoSave function is called.
//ObjectID_OnSaveEnd(sheetObj, Code, Msg)
	//sheetObj: sheet object
	//Code: Processing result code (0 or higher is success, others should be processed as error)
	//Msg: HTTP response message
function sheet1_OnSaveEnd(sheetObj, Code, Msg){ 
	//if success reload page by calling action IBSEARCH
	if(Code>=0){
		doActionIBSheet(sheetObj, document.form, IBSEARCH);
	}
	//otherwise we get all row have status 'I' (Insert)
	var invalidData=sheetObj.FindStatusRow('I');
	
	//slipt by ';'
	var rows=invalidData.split(';');
	
	//loop through row
	for(var i=0;i<rows.length;i++){
		//get value of error message code at current cell
		var msgCd=sheetObj.GetCellValue(rows[i],"err_msg_cd");
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

