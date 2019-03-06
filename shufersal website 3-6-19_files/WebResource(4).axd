﻿/* RadComboBox base skin */

/*global*/

.RadComboBox *
{
	margin: 0;
	padding: 0;
}

.RadComboBox,
.RadComboBox .rcbInput,
.RadComboBoxDropDown
{
	text-align: left;
}

.RadComboBox_rtl,
.RadComboBox_rtl .rcbInput,
.RadComboBoxDropDown_rtl
{
	text-align: right;
}

/* combobox */

.RadComboBox
{
	vertical-align: middle;
    display: -moz-inline-stack;
    display: inline-block;
}

*+html .RadComboBox  
{
    display: inline;
    zoom: 1;
}

* html .RadComboBox  
{
    display: inline;
    zoom: 1;
	vertical-align: top; 
}

.RadComboBox table
{
	border: 0;
	border-collapse: collapse;
}

.RadComboBox table td.rcbInputCell
{
	width: 100%;
	height: 20px;
	line-height: 20px;
	vertical-align: middle;
	padding: 0;
	border: 0;
}

* html .RadComboBox td.rcbInputCell
{
	height /**/: 22px;
	line-height /**/: 22px;
}

/* combobox */

.RadComboBox .rcbInputCellLeft,
.RadComboBox .rcbInputCellRight,
.RadComboBox .rcbArrowCellLeft,
.RadComboBox .rcbArrowCellRight
{
	background-color: transparent;
	background-repeat: no-repeat;
}

.RadComboBox .rcbInputCell .rcbInput
{
	width: 100%;
	background: transparent;
	border: 0;
	vertical-align: middle;
	padding: 2px 0 1px;
	outline: 0;
}

* html .RadComboBox .rcbInputCell .rcbInput
{
	height /**/: 18px;
	padding /**/: 2px 0 0; /* This should fix the ajax introduced height in IE6 */
}

.RadComboBox .rcbInputCell .rcbEmptyMessage
{
	font-style: italic;
}

.RadComboBox .rcbReadOnly .rcbInput
{
	cursor: default;
}

.RadComboBox table td.rcbInputCell,
.RadComboBox .rcbInputCell .rcbInput
{
	padding-left: 2px;
}

.RadComboBox_rtl table td.rcbInputCell,
.RadComboBox_rtl .rcbInputCell .rcbInput
{
	padding-right: 2px;
	padding-left: 0;
}

.RadComboBox table td.rcbArrowCell
{
	width: 18px;
	padding: 0;
	border: 0;
}

.RadComboBox .rcbArrowCell a
{
	position: relative;
	outline: 0;
	overflow: hidden;
	display: block;
	width: 18px;
	height: 22px;
    cursor: default;
	text-decoration: none;
	text-indent: 9999px;
	font-size: 0;
	line-height: 1px;
}

div.RadComboBox td.rcbArrowCellHidden,
div.RadComboBox .rcbArrowCellHidden a
{
	width: 3px;
}

/* Read-only styles */

.RadComboBox .rcbReadOnly td.rcbArrowCell { width: 16px; }
.RadComboBox .rcbReadOnly td.rcbArrowCell a { width: 16px; }

.RadComboBox .rcbReadOnly td.rcbArrowCellHidden,
.RadComboBox .rcbReadOnly td.rcbArrowCellHidden a { width: 3px; } 

/* dropdown */

.rcbSlide
{
	position: absolute;
	overflow: hidden;
	display: none;
	_height: 1px;
	float: left;
}

.RadComboBoxDropDown .rcbHeader,
.RadComboBoxDropDown .rcbFooter,
.RadComboBoxDropDown .rcbMoreResults,
.RadComboBoxDropDown .rcbMoreResults a
{
	background-repeat: no-repeat;
}

.RadComboBoxDropDown
{
	position: absolute;
	cursor: default;
	font-size: 11px;
	border-width: 1px;
	border-style: solid;
}

.RadComboBoxDropDown_rtl
{
	text-align: right;
	direction: rtl;
}

.RadComboBoxDropDown .rcbScroll
{
	overflow: auto;
	position: relative;
}

.RadComboBoxDropDown .rcbList
{
	list-style: none outside;
	position: relative;
	margin: 0;
	padding: 0;
}

.RadComboBoxDropDown .rcbHeader,
.RadComboBoxDropDown .rcbFooter
{
	background-repeat: repeat-x;
	padding: 5px 7px 4px;
}

.RadComboBoxDropDown .rcbHeader
{
	border-bottom-width: 1px;
	border-bottom-style: solid;
	margin-bottom: 1px;
}

.RadComboBoxDropDown .rcbFooter
{
	border-top-width: 1px;
	border-top-style: solid;
	margin-top: 1px;
}

.RadComboBoxDropDown .rcbNoWrap .rcbItem,
.RadComboBoxDropDown .rcbNoWrap .rcbHovered,
.RadComboBoxDropDown .rcbNoWrap .rcbDisabled,
.RadComboBoxDropDown .rcbNoWrap .rcbLoading
{
	white-space: nowrap;
}

.RadComboBoxDropDown .rcbItem,
.RadComboBoxDropDown .rcbHovered,
.RadComboBoxDropDown .rcbDisabled,
.RadComboBoxDropDown .rcbLoading
{
	padding: 2px 6px;
	margin: 0 1px;
}

html>/**/body .RadComboBoxDropDown .rcbItem,
html>/**/body .RadComboBoxDropDown .rcbHovered,
html>/**/body .RadComboBoxDropDown .rcbDisabled,
html>/**/body .RadComboBoxDropDown .rcbLoading
{
	min-height: 13px;
}

*+html .RadComboBoxDropDown .rcbItem,
*+html .RadComboBoxDropDown .rcbHovered,
*+html .RadComboBoxDropDown .rcbDisabled,
*+html .RadComboBoxDropDown .rcbLoading
{
	height: auto;
}

.RadComboBoxDropDown_rtl .rcbItem,
.RadComboBoxDropDown_rtl .rcbHovered,
.RadComboBoxDropDown_rtl .rcbDisabled,
.RadComboBoxDropDown_rtl .rcbLoading
{
	padding: 2px 6px;
}

.RadComboBoxDropDown .rcbImage
{
	vertical-align: middle;
	margin: 0 6px 2px 0;
}

.RadComboBoxDropDown_rtl .rcbImage
{
	margin: 0 0 2px 6px;
}

.RadComboBoxDropDown .rcbHovered
{
	background-repeat: repeat-x;
}

.RadComboBoxDropDown em
{
	font-style: normal;
	font-weight: bold;
}

*+html div.RadComboBoxDropDown .rcbList { zoom: 1; } /* IE7 item width fix */
* html div.RadComboBoxDropDown .rcbItem,
* html div.RadComboBoxDropDown .rcbHovered { zoom: 1; } /* IE6 item width fix */

.RadComboBox .rcbDisabled .rcbInputCell .rcbInput,
.RadComboBoxDropDown .rcbDisabled
{
	cursor: default;
}

.RadComboBoxDropDown .rcbLoading
{
	text-align: center;
}

.RadComboBoxDropDown .rcbMoreResults
{
	clear: both;
	border-top-width: 1px;
	border-top-style: solid;
	background-repeat: repeat-x;
	position: relative;
	padding: 0 6px 0;
	text-align: center;
	margin-top: 1px;
}

.RadComboBoxDropDown .rcbMoreResults a
{
	display: inline-block;
	width: 15px;
	height: 9px;
	text-indent: -9999px;
	overflow: hidden;
	text-decoration: none;
	cursor: pointer;
	vertical-align: middle;
}

* html .RadComboBoxDropDown .rcbMoreResults a
{
	font-size: 0;
	line-height: 0;
	text-indent: 0;
}

*+html .RadComboBoxDropDown .rcbMoreResults a
{
	font-size: 0;
	line-height: 0;
	text-indent: 0;
}

.RadComboBoxDropDown .rcbMoreResults span
{
	vertical-align: middle;
	height: 19px;
	line-height: 19px;
	display: inline-block;
}

.RadComboBoxDropDown .rcbSeparatedList .rcbItem,
.RadComboBoxDropDown .rcbSeparatedList .rcbHovered,
.RadComboBoxDropDown .rcbSeparatedList .rcbDisabled,
.RadComboBoxDropDown .rcbSeparatedList .rcbLoading
{
	padding-left: 12px;
}

.RadComboBoxDropDown .rcbSeparatedList .rcbSeparator
{
	padding-left: 6px;
}

/*<ComboBox with Label>*/

.RadComboBoxWithLabel .rcbLabel
{
	vertical-align: top;
	padding-right: 10px;
	line-height: 22px;
	zoom: 1;
}

.RadComboBox_rtl .rcbLabel
{
	text-align: right;
	padding: 0 0 0 10px;
}
*+html .RadComboBox_rtl .rcbLabel { margin-right: 5px; }
* html .RadComboBox_rtl .rcbLabel { margin-right: 5px; }

.RadComboBoxWithLabel table
{
	vertical-align: top;
	float: none;
	display: inline-block;
	zoom: 1;
}
*+html .RadComboBoxWithLabel table { display: inline; margin-right: 5px; }
* html .RadComboBoxWithLabel table { display: inline; margin-right: 5px; }

.RadComboBoxWithLabel
{
	white-space: nowrap;
	zoom: normal;
}

/*</ComboBox with Label>*/

/*hacks*/

/*Opera start*/
@media screen and (min-width: 550px)
{
	.RadComboBoxDropDown_rtl .rcbItem,
	.RadComboBoxDropDown_rtl .rcbHovered,
	.RadComboBoxDropDown_rtl .rcbDisabled,
	.RadComboBoxDropDown_rtl .rcbLoading
	{
		padding: 2px 6px 2px 19px;
	}
} /*Opera end*/
