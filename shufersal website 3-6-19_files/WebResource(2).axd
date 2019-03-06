﻿/*Telerik RadInput Common CSS*/

/*global*/

.RadInput,
.RadInputMgr
{
	vertical-align:middle;
}

.RadInput table
{
	border:0;
	vertical-align:bottom;
}

.RadInput table.riTable td
{
	border:0;
	padding:0;
	vertical-align:middle;
	overflow:visible;/*RadGrid*/
}

.RadInput table td.riCell
{
	padding-right:4px;
}

.RadInput textarea
{
	vertical-align:bottom;
	overflow:auto;
}

/*textbox states*/

html body .RadInput .riTextBox,
html body .RadInputMgr
{
	border-width:1px;
	border-style:solid;
	padding:2px 1px 3px;
}

textarea.RadInputMgr
{
	overflow:auto;
}

/*buttons*/

.RadInput a
{
	display:block;
	overflow:hidden;
	position:relative;/*FF*/
	outline:none;/*FF*/
	z-index:2;/*Opera*/
	text-indent:-2222px;
	text-align:center;
	text-decoration:none;
}
* html .RadInput a{position:static}/*IE6*/
*+html .RadInput a{position:static}/*IE7*/

.RadInput .riSpin a
{
	margin:0 1px;
}

.RadInput a.riDown
{
	margin-top:3px;
}

* html .RadInput a.riDown
{
	margin-top /**/:0;
}

/*label*/

.RadInput .riLabel
{
	margin:0 4px 0 0;
	white-space:nowrap;
}

/*rtl*/

.RadInputRTL table td.riCell
{
	padding:0 0 0 4px;
}

.RadInputRTL .riLabel
{
	margin:0 0 0 4px;
}