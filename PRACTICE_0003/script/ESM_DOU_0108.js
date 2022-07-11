/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108.js
*@FileTitle : ESM_DOU_0108
*Open Issues :
*Change history :
*@LastModifyDate : 2022.07.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
var sheetObjects = new Array();
var sheetCnt = 0;

var comboObjects = new Array();
var comboCnt = 0;

var tabObjects = new Array();
var tabCnt = 0;

var beforetab = 1;

var laneCodes = "";
var tradeCodes = "";

var cfmGtDtOver3M = false;

var searchDetail = "";
var searchSummary = "";
var firstLoad = true;
var isDbClick = false;
var searchForDbl = "";


document.onclick = processButtonClick;

/**
 * handle the click event from JSP button 
 */
function processButtonClick() {
    var sheetObject1 = sheetObjects[0];
    var sheetObject2 = sheetObjects[1];
    var formObject = document.form;
    try {
        var srcName = ComGetEvent("name");
        switch (srcName) {
            case "btn_Retrieve":
                if (!cfmGtDtOver3M && checkOverThreeMonth()) {
                    if (confirm(msgs["ESM0002"])) {//"Year Month over 3 months, do you really want to get data?"
                        cfmGtDtOver3M = true;
                    } else {
                        break;
                    }
                }
                doActionIBSheet(getCurrentSheet(), formObject, IBSEARCH);
                break;
            case "btn_date_fr_up":
                if (!isValidDate()) {
                    ComShowCodeMessage("ESM0001");//Start date must be earlier than end date
                    break;
                }
                addMonth(formObject.date_fr, 1);
                break;
            case "btn_date_fr_down":
                addMonth(formObject.date_fr, -1);
                break;
            case "btn_date_to_up":
                addMonth(formObject.date_to, 1);
                break;
            case "btn_date_to_down":
                if (!isValidDate()) {
                    ComShowCodeMessage("ESM0001");//Start date must be earlier than end date
                    break;
                }
                addMonth(formObject.date_to, -1);
                break;
            case "btn_New":
                doActionIBSheet(sheetObject1, formObject, IBRESET);
                break;
            case "btn_DownExcel":
                doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL);
                break;
            case "btn_DownExcel2":
                doActionIBSheet(sheetObject1, formObject, IBDOWNEXCEL2);
                break;
        }
    }
    catch (e) {
        if (e == "[object Error]") {
            ComShowMessage(OBJECT_ERROR);
        }
        else {
            ComShowMessage(e);
        }
    }
}

/**
 *  This function calls a common function that sets the default settings of the sheet,
 *  It is the first called area when file *jsp onload event.
 */
function loadPage() {
    initCalender();
    for (var i = 0; i < comboObjects.length; i++) {
        initCombo(comboObjects[i], i);
    }

    for (i = 0; i < tabObjects.length; i++) {
        initTab(tabObjects[i], i + 1);
        tabObjects[i].SetSelectedIndex(0);
    }

    for (i = 0; i < sheetObjects.length; i++) {
        ComConfigSheet(sheetObjects[i]);
        initSheet(sheetObjects[i], i + 1);
        ComEndConfigSheet(sheetObjects[i]);
    }
    doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
}

/**
 * Registering IBSheet Object as list adding process for list in case of needing
 * batch processing with other items defining list on the top of source.
 * 
 * @param sheet_obj: Object - sheet object.
 */
function setSheetObject(sheet_obj) {
    sheetObjects[sheetCnt++] = sheet_obj;
}

/**
 * Registering IBCombo Object as list parameter : combo_obj adding process for list
 * in case of needing batch processing with other items defining list on the top of source.
 * 
 * @param combo_obj: Object - combo object.
 */
function setComboObject(combo_obj) {
    comboObjects[comboCnt++] = combo_obj;
}

/**
 * set tab object
 * @param tab_obj : tab object
 */
function setTabObject(tab_obj) {
    tabObjects[tabCnt++] = tab_obj;
}

/**
 * This function initSheet define the basic properties of the sheet on the screen.
 * 
 * @param sheetObj: IBSheet Object.
 * @param sheetNo:  Number of IBSheet Object.
 */
function initSheet(sheetObj, sheetNo) {
    switch (sheetNo) {
        case 1:
            with (sheetObj) {
                var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
                var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name"

                SetConfig({ SearchMode: 2, MergeSheet: 5, Page: 20, FrozenCol: 0, DataRowMerge: 1 });

                var info = { Sort: 0, ColMove: 0, HeaderCheck: 0, ColResize: 1 };
                var headers = [{ Text: HeadTitle1, Align: "Center" },
                { Text: HeadTitle2, Align: "Center" }];
                InitHeaders(headers, info);

                var cols = [
                    { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",        KeyField: 0 },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",         KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",           KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",           KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",         KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",     KeyField: 0, Format: "" },
                    { Type: "Float",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt",  KeyField: 0, Format: "" },
                    { Type: "Float",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt",  KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",      KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm", KeyField: 0, Format: "" }
                ];
                InitColumns(cols);
                SetEditable(0);
                SetWaitImageVisible(0);
                ShowSubSum([{ StdCol: "inv_no", SumCols: "7|8", ShowCumulate: 0, CaptionText: " ", CaptionCol: 3 }]);
            }
            break;
        case 2:
            with (sheetObj) {
                var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
                var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";

                SetConfig({ SearchMode: 2, MergeSheet: 5, Page: 20, FrozenCol: 0, DataRowMerge: 1 });

                var info = { Sort: 0, ColMove: 0, HeaderCheck: 0, ColResize: 1 };
                var headers = [{ Text: HeadTitle1, Align: "Center" },
                { Text: HeadTitle2, Align: "Center" }];
                InitHeaders(headers, info);

                var cols = [
                    { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",        KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",         KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",           KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",           KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",         KeyField: 0, Format: "" },
                    { Type: "Combo",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rev_exp",          KeyField: 0, Format: "", ComboText: "Rev|Exp", ComboCode: "R|E" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "item",             KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",     KeyField: 0, Format: "" },
                    { Type: "Float",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt",  KeyField: 0, Format: "" },
                    { Type: "Float",  Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt",  KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",      KeyField: 0, Format: "" },
                    { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm", KeyField: 0, Format: "" }
                ];
                InitColumns(cols);
                SetEditable(0);
                SetWaitImageVisible(0);
                ShowSubSum([{ StdCol: "inv_no", SumCols: "9|10", ShowCumulate: 0, CaptionText: " ", CaptionCol: 3 }]);
                SetSumFontBold(1);
                showTotalSum(sheetObj);
            }
            break;
    }
    resizeSheet();
}

/**
 * Initializing tab object
 * @param tab_obj : tab object 
 * @param tabNo
 */
function initTab(tabObj, tabNo) {
    switch (tabNo) {
        case 1:
            with (tabObj) {
                var cnt = 0;
                InsertItem("Summary", "");
                InsertItem("Detail", "");
            }
            break;
    }
}

/**
 * Event when clicking Tab
 * activating selected tab items
 * nItem --> the number of tab that user click in
 * @param tabObj
 * @param nItem
 */
function tab1_OnChange(tabObj, nItem) {
    var objs = document.all.item("tabLayer");
    objs[nItem].style.display = "Inline";
    for (var i = 0; i < objs.length; i++) {
        if (i != nItem) {
            objs[i].style.display = "none";
            objs[beforetab].style.zIndex = objs[nItem].style.zIndex - 1;
        }
    }
    beforetab = nItem;
    handleOnchangeTab();
    resizeSheet();
}

/**
 * This function that define the basic properties for combo bõ.
 * @param comboObj: combo object
 * @param comNo: index of combo object
 */
function initCombo(comboObj, comboNo) {
    switch (comboNo) {
        case 0:
            comboObj.SetDropHeight(250);
            comboObj.SetMultiSelect(1);
            addComboItem(comboObj, partnerCodes);
            comboObj.SetItemCheck(0, true, 0);
            comboObjects[1].SetEnable(false);
            comboObjects[2].SetEnable(false);
            break;
        case 1:
            addComboItem(comboObj, laneCodes);
            break;
        case 2:
            addComboItem(comboObj, tradeCodes);
            break;
    }

}

/**
 * This function is used for checking all item
 * @param comboObj
 * @param status
 */
function checkAllItem(comboObj, status) {
    var size = comboObj.GetItemCount();
    for (var i = 1; i < size; i++) {
        comboObj.SetItemCheck(i, status, 0);
    }
}

/**
 * Get value of partner combo
 * @returns
 */
function getPartnerValue() {
    return document.form.s_partner_code_text.value;
}

/**
 * Set value for partner combo
 * @param value
 */
function setPartnerValue(value) {
    document.form.s_partner_code_text.value = value;
    document.form.s_partner_code.value = value;
}

/**
 * Get value of lane combo
 * @returns
 */
function getLaneValue() {
    return document.form.s_lane_code_text.value;
}

/**
 * Set value for lane combo
 * @param value
 */
function setLaneValue(value) {
    document.form.s_lane_code_text.value = value;
    document.form.s_lane_code.value = value;
}

/**
 * Get value of trade combo
 * @returns
 */
function getTradeValue() {
    return document.form.s_trade_code_text.value;
}

/**
 * Set value for lane combo
 * @param value
 */
function setTradeValue(value) {
    document.form.s_trade_code_text.value = value;
    document.form.s_trade_code.value = value;
}

/**
 * Check if item in combo object is all checked
 * @param comboObj
 * @returns {Boolean}
 */
function isCheckAllItem(comboObj) {
    var size = comboObj.GetItemCount();
    var count = 1;
    for (var i = 1; i < size; i++) {
        if (comboObj.GetItemCheck(i)) {
            count++;
        }
    }
    return count == size;
}

/**
 * Function that add item to combo box 
 * @param comboObj 
 * @param comboItems
 */
function addComboItem(comboObj, comboItems) {
    comboItems = comboItems ? comboItems.split("|") : [];
    for (var i = 0; i < comboItems.length; i++) {
        comboObj.InsertItem(i, comboItems[i], comboItems[i]);
    }
}

/**
 * Enable lane combo
 * @param generate: generate data or not
 */
function enableLaneCombo(generate) {
    setLaneValue("");
    var partnerValue = getPartnerValue();
    if (partnerValue == 'All') {
        comboObjects[1].SetEnable(false);
    } else {
        comboObjects[1].SetEnable(true);
        if (generate) {
            generateComboData(1);
        }
    }
}

/**
 * Enable trade combo
 * @param generate: generate data or not
 */
function enableTradeCombo(generate) {
    setTradeValue("");
    var laneValue = getLaneValue();
    if (laneValue == "") {
        comboObjects[2].SetEnable(false);
        setTradeValue("");
    } else {
        comboObjects[2].SetEnable(true);
        if (generate) {
            generateComboData(2);
        }
    }
}

/**
 * Function is used for generating data for combo box
 * @param comboNo
 */
function generateComboData(comboNo) {
    comboObj = comboObjects[comboNo];
    comboObj.RemoveAll();
    ComOpenWait(true);
    if (comboNo == 1) {//Lane combo
        document.form.f_cmd.value = SEARCH02;
        var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(document.form));
        laneCodes = ComGetEtcData(xml, "laneCodes");
    } else {//Trade combo
        document.form.f_cmd.value = SEARCH03;
        var xml = sheetObjects[0].GetSearchData("ESM_DOU_0108GS.do", FormQueryString(document.form));
        tradeCodes = ComGetEtcData(xml, "tradeCodes");
    }
    ComOpenWait(false);
    initCombo(comboObj, comboNo);
}

/**
 * Check if date valid
 * @returns {Boolean}
 */
function isValidDate() {
	var formObj = document.form;
	var fromDate = formObj.s_date_fr.value.replaceStr("-", "") + "01";
	var toDate = formObj.s_date_to.value.replaceStr("-", "") + "01";
	return ComGetDaysBetween(fromDate, toDate) > 88;
}

/**
 * Event fires when user checks Partner combo box 
 * @param comboObj
 * @param index
 * @param value
 * @param status
 */
function s_partner_code_OnCheckClick(comboObj, index, value, status) {
    if (status) {
        if (value == "All") {
            checkAllItem(comboObj, false);
        } else if (comboObj.GetItemCheck(0)) {
            comboObj.SetItemCheck(0, false, false);
        } else if (isCheckAllItem(comboObj)) {
            checkAllItem(comboObj, false);
            comboObj.SetItemCheck(0, true, false);
        }
    } else if (getPartnerValue() == "") {
        comboObj.SetItemCheck(0, true, false);
    }
    enableLaneCombo();
    enableTradeCombo();
}

/**
 * Event fires when user out focus Partner combo box 
 */
function s_partner_code_OnBlur() {
    enableLaneCombo(true);
    enableTradeCombo(true);
}

/**
 * Event fires when user change Lane combo box 
 */
function s_lane_code_OnChange() {
    enableTradeCombo(true);
}

/**
 * Functions that define the basic properties of the date on the screen
 */
function initCalender() {
    var formObj = document.form;
    var ymTo = ComGetNowInfo("ym", "-");
    var ymFrom = ComGetDateAdd(ymTo + "-01", "M", -1);
    formObj.s_date_to.value = GetDateFormat(ymTo, "ym");
    formObj.s_date_fr.value = GetDateFormat(ymFrom, "ym");
}

/**
 * Funtion that adds month to date object
 * @param obj
 * @param month
 */
function addMonth(obj, month) {
    if (obj.value != "") {
        obj.value = ComGetDateAdd(obj.value + "-01", "M", month).substr(0, 7);
    }
    for (i = 0; i < sheetObjects.length; i++) {
        sheetObjects[i].RemoveAll();
    }

}

/**
 * Get format date
 */
function GetDateFormat(obj, sFormat) {
    var sVal = String(getArgValue(obj));
    sVal = sVal.replace(/\/|\-|\.|\:|\ /g, "");
    if (ComIsEmpty(sVal)) return "";

    var retValue = "";
    switch (sFormat) {
        case "ym":
            retValue = sVal.substring(0, 6);
            break;
    }
    retValue = ComGetMaskedValue(retValue, sFormat);
    return retValue;
}

/**
 *  This function defines the transaction logic between the user interface and the server of IBSheet.
 *  
 *  @param sheetObj:  IBSSheet Object.
 *  @param formObj :  Form object.
 *  @param sAction :  Action Code (e.g. IBSEARCH, IBSAVE, IBDELETE, IBDOWNEXCEL).
 * */
function doActionIBSheet(sheetObj, formObj, sAction) {
    sheetObj.ShowDebugMsg(0);
    switch (sAction) {
        case IBSEARCH: // retrieve
            ComOpenWait(true);
            if (!isDbClick) {
                if (sheetObj.id == "sheet1") {
                    formObj.f_cmd.value = SEARCH;
                    searchSummary = getCurrentSearchOption();
                    searchForDbl = FormQueryString(formObj);
                }
                else if (sheetObj.id == "sheet2") {
                    searchDetail = getCurrentSearchOption();
                    formObj.f_cmd.value = SEARCH01;
                }
                var xml = sheetObj.GetSearchData("ESM_DOU_0108GS.do", FormQueryString(formObj));
                sheetObj.LoadSearchData(xml, { Sync: 1 });
            } else {
                searchDetail = searchSummary;
                var xml = sheetObj.GetSearchData("ESM_DOU_0108GS.do", searchForDbl);
                sheetObj.LoadSearchData(xml, { Sync: 1 });
            }

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
            ComShowCodeMessage("ESM0003");//This feature is not available now
            break;
    }
}

/**
 * This function resize sheet,
 * If don't call this functions, it will may make UI breakable.
 */
function resizeSheet() {
    for (var i = 0; i < sheetObjects.length; i++) {
        ComResizeSheet(sheetObjects[i]);
    }
}

/**
 * This function will delete the values ​​in the input and the Grid that are displayed in the UI when click new button.
 * reset(): Remove all configurations in IBSheet and reset to OOTB state.
 */
function resetForm(formObj) {
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

/**
 * Event fires after searching
 * @param sheetObj
 * @param Code
 * @param Msg
 * @param StCode
 * @param StMsg
 */
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) {
    ComOpenWait(false);
    if (sheetObj.RowCount() > 0) {
        showTotalSum(sheetObj);
    }
    hightLightSum(sheetObj);
}

/**
 * Event fires after searching
 * @param sheetObj
 * @param Code
 * @param Msg
 * @param StCode
 * @param StMsg
 */
function sheet2_OnSearchEnd(sheetObj) {
    ComOpenWait(false);
    if (sheetObj.RowCount() > 0) {
        showTotalSum(sheetObj);
    }
    hightLightSum(sheetObj);
}

/**
 * Check if the number of month(s) between from_date and to_date over three months or not
 * checkOverThreeMonth
 * @returns {Boolean}
 */
function checkOverThreeMonth() {
    var months;
    var from = new Date(document.form.date_fr.value);
    var to = new Date(document.form.date_to.value);
    months = (to.getFullYear() - from.getFullYear()) * 12;
    months -= from.getMonth();
    months += to.getMonth();
    return months < 3 ? false : true;
}

/**
 * Get current sheet
 * @returns
 */
function getCurrentSheet() {
    return sheetObjects[beforetab];
}

/**
 * Get current search option
 * @returns
 */
function getCurrentSearchOption() {
    var currentSearchOption = "";
    with (document.form) {
        currentSearchOption += date_fr.value;
        currentSearchOption += date_to.value;
        currentSearchOption += s_partner_code.value;
        currentSearchOption += s_lane_code.value;
        currentSearchOption += s_trade_code.value;
    }
    return currentSearchOption;
}

/**
 * Handle on change tab
 */
function handleOnchangeTab() {
    if (firstLoad) {
        firstLoad = false;
        return;
    }
    if (isDbClick) {
        isDbClick = false;
        return;
    }
    var currentSheet = getCurrentSheet();
    var formQuery = getCurrentSearchOption();
    if (searchSummary != formQuery && formQuery != searchDetail) {
        if (confirm(msgs['ESM0004'])) {//Search data was changed. Do you want to retrieve?
            doActionIBSheet(currentSheet, document.form, IBSEARCH);
        } else {
            return;
        }
    }
    if (currentSheet.id == "sheet1") {//Summary Sheet
        if (searchSummary != formQuery) {
            doActionIBSheet(currentSheet, document.form, IBSEARCH);
        }
    } else {
        if (searchDetail != formQuery) {
            doActionIBSheet(currentSheet, document.form, IBSEARCH);
        }
    }
}

/**
 * Event fires when user double click on sheet 1
 * @param sheetObj
 * @param Row
 * @param Col
 */
function sheet1_OnDblClick(sheetObj, Row, Col) {
    formObj = document.form;
    isDbClick = true;
    if (sheetObj.GetCellValue(Row, "jo_crr_cd") != "") {
        if (searchDetail != searchSummary || sheetObjects[1].RowCount() == 0) {
            doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);
        }
        var saveNames = ["jo_crr_cd", "rlane_cd", "inv_no", "csr_no", "locl_curr_cd", "prnr_ref_no"];
        var summaryData = getDataRow(sheetObjects[0], Row, saveNames);
        var size = sheetObjects[1].RowCount();
        for (var i = 2; i <= size; i++) {
            if (summaryData == getDataRow(sheetObjects[1], i, saveNames)) {
                tabObjects[0].SetSelectedIndex(1);
                sheetObjects[1].SetSelectRow(i);
                return;
            }
        }

        ComShowCodeMessage('COM132701');
    }
}

/**
 * This function is used to get data at row and append that data to string
 * @param sheetObj: Sheet Object
 * @param row: selected row
 * @param saveNames: array of save name
 * @returns data
 */
function getDataRow(sheetObj, row, saveNames) {
    var result = "";
    for (var i = 0; i < saveNames.length; i++) {
        result += sheetObj.GetCellValue(row, saveNames[i]);
    }
    return result;
}

/**
 * Function that uses to hight light sum row
 * @param sheetObj
 */
function hightLightSum(sheetObj) {
    sheetObj.SetRangeBackColor(2, 0, sheetObj.LastRow() - 2, 12, "white")
    if (sheetObj.RowCount() > 0) {
        var lastRowIndex = sheetObj.LastRow();
        sheetObj.SetRowBackColor(lastRowIndex, "#f68f8c");
        sheetObj.SetRowBackColor(lastRowIndex - 1, "#f68f8c");
        if (sheetObj.id == "sheet1") {
            for (var i = 6; i <= 8; i++) {
                sheetObj.SetCellFontBold(lastRowIndex, i, 1);
                sheetObj.SetCellFontBold(lastRowIndex - 1, i, 1);
            }
        } else {
            for (var i = 8; i <= 10; i++) {
                sheetObj.SetCellFontBold(lastRowIndex, i, 1);
                sheetObj.SetCellFontBold(lastRowIndex - 1, i, 1);
            }
        }
    }
}

/**
 * Function that uses to show total sum row
 * @param sheetObj
 */
function showTotalSum(sheetObj) {
    var revTotalVND = 0;
    var expTotalVND = 0;
    var revTotalUSD = 0;
    var expTotalUSD = 0;

    var subsum = sheetObj.FindSubSumRow();
    var arrSubsum = subsum.split("|");

    for (var i = 0; i < arrSubsum.length; i++) {
        var locl_curr_cd = sheetObj.GetCellValue(arrSubsum[i] - 1, "locl_curr_cd");
        sheetObj.SetCellValue(arrSubsum[i], "locl_curr_cd", locl_curr_cd);
        sheetObj.SetCellFont("FontBold", arrSubsum[i], "locl_curr_cd", arrSubsum[i], "inv_exp_act_amt", 1);
        if (locl_curr_cd == "VND") {
            revTotalVND += sheetObj.GetCellValue(arrSubsum[i], "inv_rev_act_amt");
            expTotalVND += sheetObj.GetCellValue(arrSubsum[i], "inv_exp_act_amt");
        } else {
            revTotalUSD += sheetObj.GetCellValue(arrSubsum[i], "inv_rev_act_amt");
            expTotalUSD += sheetObj.GetCellValue(arrSubsum[i], "inv_exp_act_amt");
        }
    }
    sheetObj.DataInsert(-1);
    sheetObj.SetCellValue(sheetObj.LastRow(), "locl_curr_cd", "VND");
    sheetObj.SetCellValue(sheetObj.LastRow(), "inv_rev_act_amt", revTotalVND);
    sheetObj.SetCellValue(sheetObj.LastRow(), "inv_exp_act_amt", expTotalVND);
    sheetObj.SetCellValue(sheetObj.LastRow(), "rev_exp", "");

    sheetObj.DataInsert(-1);
    sheetObj.SetCellValue(sheetObj.LastRow(), "locl_curr_cd", "USD");
    sheetObj.SetCellValue(sheetObj.LastRow(), "inv_rev_act_amt", revTotalUSD);
    sheetObj.SetCellValue(sheetObj.LastRow(), "inv_exp_act_amt", expTotalUSD);
    sheetObj.SetCellValue(sheetObj.LastRow(), "rev_exp", "");

    sheetObj.SetSelectRow(-1);
}




