//array of sheetObject
var sheetObjects = new Array();
//number of sheets
var sheetCnt = 0;
//set function processButtonClick for event onclick on browser
document.onclick = processButtonClick;

/**
 * handle event onclick
 */
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
        //do nothing
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

    //auto search data after loading finish.
    doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
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
                var HeadTitle = "STS|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date";
              
                SetConfig({ SearchMode: 2, Page: 20 });
      
                var headers = [{ Text: HeadTitle, Align: "Center" }];
                var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
                
                InitHeaders(headers, info);
                
//                var cols = [ 
//       	                 {Type:"Status", Hidden:1, Width:50,  Align:"Center",  SaveName:"ibflag",          KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1 },
//       		             {Type:"Text",   Hidden:0, Width:70,  Align:"Center",  SaveName:"ownr_sub_sys_cd", KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
//       		             {Type:"Text",   Hidden:0, Width:60,  Align:"Center",  SaveName:"intg_cd_id",      KeyField:1,  Format:"",     UpdateEdit:0, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1 },
//       		             {Type:"Text",   Hidden:0, Width:200, Align:"Left",    SaveName:"intg_cd_nm",      KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1 },
//       		             {Type:"Text",   Hidden:0, Width:50,  Align:"Center",  SaveName:"intg_cd_len",     KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1, AcceptKeys : "N" },
//       		             {Type:"Combo",  Hidden:0, Width:100, Align:"Center",  SaveName:"intg_cd_tp_cd",   KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1, ComboCode:"G", ComboText:"General"},
//       		             {Type:"Text",   Hidden:0, Width:150, Align:"Left",    SaveName:"mng_tbl_nm",      KeyField:1,  Format:"",     UpdateEdit:0, InsertEdit:1 },
//       		             {Type:"Text",   Hidden:0, Width:350, Align:"Left",    SaveName:"intg_cd_desc",    KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1 },
//       		             {Type:"Combo",  Hidden:0, Width:40,  Align:"Center",  SaveName:"intg_cd_use_flg", KeyField:0,  Format:"",     UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y", ComboText:"N|Y"},
//       		             {Type:"Text",   Hidden:0, Width:80,  Align:"Center",  SaveName:"cre_usr_id",      KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
//       		             {Type:"Date",   Hidden:0, Width:80,  Align:"Center",  SaveName:"cre_dt",          KeyField:0,  Format:"Ymd",  UpdateEdit:0, InsertEdit:0 },
//       		             {Type:"Text",   Hidden:0, Width:80,  Align:"Center",  SaveName:"upd_usr_id",      KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
//       		             {Type:"Date",   Hidden:0, Width:80,  Align:"Center",  SaveName:"upd_dt",          KeyField:0,  Format:"Ymd",  UpdateEdit:0, InsertEdit:0 } 
//       		         ]; 
                
                var cols = [ 
          	                 {Type:"Status", Hidden:1, Width:50,  Align:"Center",  SaveName:"ibflag",          KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Text",   Hidden:0, Width:70,  Align:"Center",  SaveName:"ownr_sub_sys_cd", KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0, AcceptKeys : "E", InputCaseSensitive : 1 },
          		             {Type:"Text",   Hidden:0, Width:60,  Align:"Center",  SaveName:"intg_cd_id",      KeyField:1,  Format:"",     UpdateEdit:0, InsertEdit:0, AcceptKeys : "E", InputCaseSensitive : 1 },
          		             {Type:"Text",   Hidden:0, Width:200, Align:"Left",    SaveName:"intg_cd_nm",      KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Text",   Hidden:0, Width:50,  Align:"Center",  SaveName:"intg_cd_len",     KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0, AcceptKeys : "N" },
          		             {Type:"Combo",  Hidden:0, Width:100, Align:"Center",  SaveName:"intg_cd_tp_cd",   KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0, ComboCode:"G", ComboText:"General"},
          		             {Type:"Text",   Hidden:0, Width:150, Align:"Left",    SaveName:"mng_tbl_nm",      KeyField:1,  Format:"",     UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Text",   Hidden:0, Width:350, Align:"Left",    SaveName:"intg_cd_desc",    KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Combo",  Hidden:0, Width:40,  Align:"Center",  SaveName:"intg_cd_use_flg", KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0, ComboCode:"N|Y", ComboText:"N|Y"},
          		             {Type:"Text",   Hidden:0, Width:80,  Align:"Center",  SaveName:"cre_usr_id",      KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Date",   Hidden:0, Width:80,  Align:"Center",  SaveName:"cre_dt",          KeyField:0,  Format:"Ymd",  UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Text",   Hidden:0, Width:80,  Align:"Center",  SaveName:"upd_usr_id",      KeyField:0,  Format:"",     UpdateEdit:0, InsertEdit:0 },
          		             {Type:"Date",   Hidden:0, Width:80,  Align:"Center",  SaveName:"upd_dt",          KeyField:0,  Format:"Ymd",  UpdateEdit:0, InsertEdit:0 } 
          		         ]; 

                //initialize columns base on above configure
                InitColumns(cols);
                
                //Check or configure whether to display waiting image during processing.
                SetWaitImageVisible(0);
                
                //Resize sheet to fit window
                ComResizeSheet(sheetObjects[0]);
            }
            break;
            
        case "sheet2":
            with (sheetObj) {
                var HeadTitle = "STS|SubSystem|Cd ID|Cd Name|Length|Cd Type|Table Name|Description Remark|Flag|Create User|Create Date|Update User|Update Date";
              
                SetConfig({ SearchMode: 2, Page: 20 });
      
                var headers = [{ Text: HeadTitle, Align: "Center" }];
                var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
                
                InitHeaders(headers, info);
                
                var cols = [ 
                {Type:"Status", Hidden:1, Width:50,  Align:"Center", ColMerge:0, SaveName:"ibflag",              KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
			    {Type:"Text",   Hidden:1, Width:10,  Align:"Center", ColMerge:0, SaveName:"intg_cd_id",          KeyField:1, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:0, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:60,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_ctnt",    KeyField:1, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:0, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:200, Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_desc", KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:600, Align:"Left",   ColMerge:0, SaveName:"intg_cd_val_desc",    KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 },
			    {Type:"Text",   Hidden:0, Width:50,  Align:"Center", ColMerge:0, SaveName:"intg_cd_val_dp_seq",  KeyField:0, CalcLogic:"", Format:"", PointCount:0, UpdateEdit:1, InsertEdit:1 } 
			];
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

function setSheetObject(sheet_obj) {
    sheetObjects[sheetCnt++] = sheet_obj;
}

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
            sheetObj.DoSearch("PRACTICE_0002GS.do", FormQueryString(formObj));
            //disable waiting image on sheet
            ComOpenWait(false);
            break;
        default:
            break;
    }
}

