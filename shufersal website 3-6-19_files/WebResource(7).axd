﻿/* RadToolTip Base Stylesheet */
/*set width:auto because if it is 100% the right border is not visible - should be 100% minus the width of the side border TDs*/
.RadToolTip.rtLoading
{	
	width: auto; 
	height: 100%;
}

div.RadToolTip
{
	position: absolute;
	cursor: default;
}

div.RadToolTip div.rtCallout
{
	display: none;
}

div.RadToolTip.rtVisibleCallout div.rtCallout
{
	display: block;
}

div.RadToolTip table.rtWrapper
{
	padding: 0; 
	margin: 0;
	border-collapse: collapse;
}

.RadToolTip table.rtWrapper td.rtWrapperTopLeft,
.RadToolTip table.rtWrapper td.rtWrapperTopRight,
.RadToolTip table.rtWrapper td.rtWrapperBottomLeft,
.RadToolTip table.rtWrapper td.rtWrapperBottomRight
{
	width: 3px; 
	height: 3px; 
	line-height: 1px; 
	font-size: 1px;
	background-color: transparent;
	background-repeat: no-repeat;
}

div.RadToolTip table.rtWrapper td.rtWrapperTopLeft
{
	background-position: 0 -12px;
}

div.RadToolTip table.rtWrapper td.rtWrapperTopRight
{
	background-position: right -12px;
}

div.RadToolTip table.rtWrapper td.rtWrapperBottomLeft
{
	background-position: 0 -63px;
}

div.RadToolTip table.rtWrapper td.rtWrapperBottomRight
{
	background-position: right -63px;
}

div.RadToolTip table.rtWrapper td.rtWrapperTopCenter,
div.RadToolTip table.rtWrapper td.rtWrapperBottomCenter
{
	height: 3px; 
	line-height: 0px; 
	font-size: 1px;
	background-repeat: repeat-x;
}

div.RadToolTip table.rtWrapper td.rtWrapperTopCenter
{
	background-position: 0 -66px;
}

div.RadToolTip table.rtWrapper td.rtWrapperBottomCenter
{
	background-position: 0 -120px;
}

div.RadToolTip table.rtWrapper td.rtWrapperLeftMiddle,
div.RadToolTip table.rtWrapper td.rtWrapperRightMiddle
{
	background-repeat: repeat-y;
	font-size: 1px;
	width: 3px;
}

div.RadToolTip table.rtWrapper td.rtWrapperLeftMiddle
{
	background-position: 0 0;
}

div.RadToolTip table.rtWrapper td.rtWrapperRightMiddle
{
	background-position: -3px 0;
}

div.RadToolTip table.rtWrapper td.rtWrapperContent
{
	font: normal 12px "Segoe UI", Arial, Sans-serif;
	height: 100%;
}

div.RadToolTip div.rtTitlebar
{	
	font: normal 18px "Segoe UI", Arial, Sans-serif;
	float: left;
	line-height: 22px;
}

div.RadToolTip a.rtCloseButton
{
	display: block; 
	float: right;
	position: absolute;
	right: 1px;
	width: 12px; 
	height: 12px;
	font-size: 1px; 
	line-height: 1px;
	margin-top: 3px;
	margin-right: 3px;
	background-position: 0 0;
	background-repeat: no-repeat;
	text-indent: -9999px;
	outline: none;
}

/* RTL support begin */
div.RadToolTip_rtl a.rtCloseButton,
div.RadToolTip_rtl a.rtCloseButton:hover
{
	float: left ;
	text-decoration: none ;
    outline: none;
    left: 2px;
    margin-left: 1px;
    right: auto;
    margin-right: auto;
}

div.RadToolTip_rtl div.rtTitlebar,
div.RadToolTip_rtl table.rtWrapper td.rtWrapperTopCenter div.rtTitlebar div
{
	float: right ;
}

/* position="rtCalloutCenter" */
div.RadToolTip_rtl .rtCalloutCenter
{
	right: 0;
	left: auto;
}

/* position="rtCalloutBottomRight" */
.RadToolTip_rtl .rtCalloutTopLeft
{
	margin-right: -8px;
	left: 0;
}

/* position="rtCalloutBottomCenter" */
.RadToolTip_rtl .rtCalloutTopCenter
{
	margin-right: 10px;
}

/* position="rtCalloutBottomLeft" */
.RadToolTip_rtl .rtCalloutTopRight
{
	margin-right: 20px;
}

/* position="rtCalloutTopCenter" */
.RadToolTip_rtl .rtCalloutBottomCenter
{
	margin-right: 10px ;
}

/* position="rtCalloutTopLeft" */
.RadToolTip_rtl .rtCalloutBottomRight
{
	margin-right: 40px ;
}

/* position="rtCalloutTopRight" */
.RadToolTip_rtl .rtCalloutBottomLeft
{
	margin-right: -20px;
	left: 0;
}

/* position="rtCalloutMiddleRight" */
.RadToolTip_rtl .rtCalloutMiddleLeft
{
	margin-right: 10px;
	left: 0;
}

/* position="rtCalloutMiddleLeft" */
.RadToolTip_rtl .rtCalloutMiddleRight
{
	margin-right: 1px;
}

/* RTL Shadows */
div.RadToolTip_rtl .rtShadow .rtCloseButton,
div.RadToolTip_rtl .rtShadow a.rtCloseButton:hover
{
    left: 10px;
}

*+html div.RadToolTip_rtl .rtShadow .rtCloseButton,
*+html div.RadToolTip_rtl .rtShadow a.rtCloseButton:hover
{
    left: 17px;
}

/* RTL support end */

div.RadToolTip div.rtCallout
{
	position: absolute; 
	height: 11px; 
	width: 11px;
	line-height: 0px; 
	font-size: 1px;
	background-repeat: no-repeat;
}

/* position="rtCalloutCenter" */
div.RadToolTip .rtCalloutCenter
{
	visibility: hidden;
}

/* position="rtCalloutBottomRight" */
.RadToolTip .rtCalloutTopLeft
{
	margin-top: -10px; 
	margin-left: 20px;
	background-position: 0 -22px;
}

/* position="rtCalloutBottomCenter" */
.RadToolTip .rtCalloutTopCenter
{
	left: 50% ;
	margin-top: -9px; 
	margin-left: -10px;
	background-position: 0 0 ;
}

/* position="rtCalloutBottomLeft" */
.RadToolTip .rtCalloutTopRight
{
	left: 100% ;
	margin-top: -10px; 
	margin-left: -20px;
	background-position: 0 -11px ;
}

/* position="rtCalloutTopCenter" */
.RadToolTip .rtCalloutBottomCenter
{
	top: 100% ; 
	left: 50% ;
	margin-left: -10px ; 
	margin-top: -1px ;
	background-position: 0 -55px ;
}

/* position="rtCalloutTopLeft" */
.RadToolTip .rtCalloutBottomRight
{
	top: 100% ; 
	left: 100% ;
	margin-left: -40px ; 
	margin-top: -1px ;
	background-position: 0 -66px ;
}

/* position="rtCalloutTopRight" */
.RadToolTip .rtCalloutBottomLeft
{
	top: 100% ;
	margin-left: 20px ; 
	margin-top: -1px ;
	background-position: 0 -77px ;
}

/* position="rtCalloutMiddleRight" */
.RadToolTip .rtCalloutMiddleLeft
{
	top: 50% ;
	margin-left: -10px; 
	margin-top: -7px;
	background-position: 0 -44px ;
}

/* position="rtCalloutMiddleLeft" */
.RadToolTip .rtCalloutMiddleRight
{
	left: 100% ; 
	top: 50% ;
	margin-left: -1px;
	margin-top: -10px;
	background-position: 0 -33px;
}

.RadToolTip table.rtWrapper td.rtWrapperTopLeft,   
.RadToolTip table.rtWrapper td.rtWrapperTopCenter,   
.RadToolTip table.rtWrapper td.rtWrapperTopRight,   
.RadToolTip table.rtWrapper td.rtWrapperLeftMiddle,   
.RadToolTip table.rtWrapper td.rtWrapperContent,   
.RadToolTip table.rtWrapper td.rtWrapperRightMiddle,   
.RadToolTip table.rtWrapper td.rtWrapperBottomLeft,   
.RadToolTip table.rtWrapper td.rtWrapperBottomCenter,   
.RadToolTip table.rtWrapper td.rtWrapperBottomRight   
{
    padding: 0;
    border: 0;
    border-collapse: collapse;
}

/* ToolTip Shadows */
.RadToolTip table.rtShadow td.rtWrapperTopLeft,
.RadToolTip table.rtShadow td.rtWrapperTopRight,
.RadToolTip table.rtShadow td.rtWrapperBottomLeft,
.RadToolTip table.rtShadow td.rtWrapperBottomRight
{
	width: 7px; 
	height: 7px; 
	line-height: 1px; 
	font-size: 1px;
	background-color: transparent;
	background-repeat: no-repeat;
}

div.RadToolTip table.rtShadow td.rtWrapperTopLeft
{
	background-position: 0 -128px;
}

div.RadToolTip table.rtShadow td.rtWrapperTopRight
{
	background-position: -9px -128px;
}

div.RadToolTip table.rtShadow td.rtWrapperBottomLeft
{
	background-position: 0 -135px;
}

div.RadToolTip table.rtShadow td.rtWrapperBottomRight
{
	background-position: -9px -135px;
}

div.RadToolTip table.rtShadow td.rtWrapperTopCenter,
div.RadToolTip table.rtShadow td.rtWrapperBottomCenter
{
	height: 7px; 
	line-height: 0px; 
	font-size: 1px;
	background-repeat: repeat-x;
}

div.RadToolTip table.rtShadow td.rtWrapperTopCenter
{
	background-position: 0 -145px;
}

div.RadToolTip table.rtShadow td.rtWrapperBottomCenter
{
	background-position: 0 -152px;
}

div.RadToolTip table.rtShadow td.rtWrapperLeftMiddle,
div.RadToolTip table.rtShadow td.rtWrapperRightMiddle
{
	background-repeat: repeat-y;
	font-size: 1px;
	width: 7px;
}

div.RadToolTip table.rtShadow td.rtWrapperLeftMiddle
{
	background-position: -7px 0;
}

div.RadToolTip table.rtShadow td.rtWrapperRightMiddle
{
	background-position: -14px 0;
}

.RadToolTip.rtShadow .rtCalloutTopLeft, 
.RadToolTip.rtShadow .rtCalloutTopRight
{
	margin-top:-4px;
}

.RadToolTip.rtShadow .rtCalloutTopCenter 
{
	margin-top:-3px;
}

.RadToolTip.rtShadow .rtCalloutMiddleRight
{
	margin-left:-7px;
}

.RadToolTip.rtShadow .rtCalloutMiddleLeft
{
	margin-left:-4px;
}

.RadToolTip.rtShadow .rtCalloutBottomRight, 
.RadToolTip.rtShadow .rtCalloutBottomCenter,
.RadToolTip.rtShadow .rtCalloutBottomLeft
{
	margin-top: -7px;
}

div.RadToolTip table.rtShadow a.rtCloseButton
{
	margin-top: 6px;
	margin-right: 8px;
}

*+html div.RadToolTip table.rtShadow a.rtCloseButton
{
	margin-top: 10px;
	margin-right: 8px;
}

*+html div.RadToolTip_rtl.rtShadow .rtCalloutMiddleLeft
{
	margin-right: 4px !important;
}