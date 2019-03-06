.RadButton,
.rbDecorated
{
	font-size: 12px;
	font-family: "Segoe UI" , Arial, Helvetica, sans-serif;
}

.RadButton img
{
	border: 0;
}

.rbSkinnedButton
{
	display: inline-block;
	position: relative;
	background-color: transparent;
	background-repeat: no-repeat;
	border: 0 none;
	cursor: default;
	height: 22px;
	text-align: center;
	text-decoration: none;
	white-space: nowrap;
	background-position: right 0;
	padding-right: 4px; /* this value is hacked for webkit */
}

/* Fixing RadbUtton position in Firefox when it is next to textbox */
x:-moz-any-link, .rbSkinnedButton 
{
	vertical-align: middle;
}

.rbDecorated
{
	display: block;
	height: 22px;
	padding-left: 10px;
	border: 0;
	text-align: center;
	background-position: left -22px;
	overflow: visible;
	background-color: transparent;
}

*html .rbDecorated
{
	display: inline;
	padding-right: 8px;
}

*+html .rbDecorated
{
	padding-right: 8px;
}

.rbSkinnedButton:hover,
.rbSkinnedButton:focus, 
.rbSkinnedButton:active
{
	background-position: right -44px;
}

.rbSkinnedButton:hover .rbDecorated,
.rbSkinnedButton:focus .rbDecorated, 
.rbSkinnedButton:active .rbDecorated
{
	background-position: left -66px;
}

.rbSkinnedButtonChecked,
.rbSkinnedButtonChecked:hover
{
	background-position: right -88px;
}

.rbSkinnedButtonChecked .rbDecorated,
.rbSkinnedButtonChecked:hover .rbDecorated
{
	background-position: left -110px;
}

.RadButton input.rbDecorated:focus, 
.RadButton input.rbDecorated::-moz-focus-inner,
.RadButton.rbToggleButton,
.RadButton.rbLinkButton,
.RadButton.rbImageButton,
.RadButton:active,
.RadButton:focus
{
	border: 0 none;
	outline: 0 none;
}

.RadButton .rbPrimary
{
	padding-left: 25px;
}

.rbPrimaryIconOnly
{
	padding: 0 9px;
}

.RadButton .rbSecondary
{
	padding-right: 25px;
}

.rbPrimarySecondaryIcon
{
	padding: 0 15px;
}

.rbPrimaryIcon, 
.rbSecondaryIcon
{
	position: absolute;
	display: block;
	width: 16px;
	height: 16px;
	overflow: hidden;
	background-repeat: no-repeat;
	cursor: default;
}

.rbPrimaryIcon
{
	top: 3px;
	left: 4px;
}

.rbSecondaryIcon
{
	top: 3px;
	right: 4px;
}

.rbText
{
	display: inline-block;
}

.rbImageButton
{
	position: relative;
	display: inline-block;
	cursor: default;
	text-decoration: none;
	text-align: center;
}

.rbLinkButton
{
	display: inline-block;
	height: 22px;
	line-height: 22px;
	position: relative;
	padding: 0 4px;
	cursor: default;
}

/* Vertical Buttons */
.rbVerticalSkinnedButton
{
	display: inline-block;
	position: relative;
	background-color: transparent;
	background-repeat: no-repeat;
	border: 0 none;
	cursor: default;
	height: 65px;
	text-align: center;
	text-decoration: none;
	white-space: nowrap;
	background-position: right -155px;
	padding-right: 4px;
}

.rbVerticalDecorated
{
	display: block;
	height: 65px;
	padding-left: 10px;
	border: 0;
	text-align: center;
	vertical-align: bottom;
	background-position: left -220px;
}

.rbVerticalSkinnedButton:hover
{
	background-position: right -285px;
}

.rbVerticalSkinnedButton:hover .rbVerticalDecorated
{
	background-position: left -350px;
}

.rbVerticalSkinnedButton:focus,
.rbVerticalSkinnedButton:active
{
	background-position: right -415px;
}

.rbVerticalSkinnedButton:focus .rbVerticalDecorated,
.rbVerticalSkinnedButton:active .rbVerticalDecorated
{
	background-position: left -480px;
}

.rbVerticalPrimary,
.rbVerticalSecondary
{
	display: block;
	position: absolute;
}

.rbVerticalPrimary
{
	width: 24px;
	height: 24px;
	top: 0;
	left: 0;
}

.rbVerticalSecondary
{
	width: 12px;
	height: 12px;
	bottom: 0;
	right: 0;
}

.rbVerticalText
{
	display: block;
	width: 100%;
	position: absolute;
	bottom: 0;
	left: 0;
	color: #000;
}

/* Toggle Buttons Style */
.rbToggleButton
{
	position: relative;
	display: inline-block;
	cursor: default;
	height: 20px;
	line-height: 20px;
	text-decoration: none;
	padding-left: 20px;
}

.rbToggleButtonIcon
{
	display: block;
	position: absolute;
	top: 5px;
	left: 3px;
	width: 15px;
	height: 15px;
}

.rbToggleCheckbox
{
	background-position: -4px 0;
}

.rbToggleCheckbox:hover,
.rbToggleButton:hover .rbToggleCheckbox
{
	background-position: -4px -20px;
}

.rbToggleCheckboxChecked
{
	background-position: -4px -40px;
}

.rbToggleCheckboxChecked:hover,
.rbToggleButton:hover .rbToggleCheckboxChecked
{
	background-position: -4px -60px;
}

.rbToggleCheckboxFilled
{
	background-position: -4px -80px;
}

.rbToggleCheckbox:hover,
.rbToggleButton:hover .rbToggleCheckboxFilled
{
	background-position: -4px -100px;
}

.rbToggleRadio
{
	background-position: -24px 0;
}

.rbToggleRadio:hover,
.rbToggleButton:hover .rbToggleRadio
{
	background-position: -24px -20px;
}

.rbToggleRadioChecked
{
	background-position: -24px -40px;
}

.rbToggleRadioChecked:hover,
.rbToggleButton:hover .rbToggleRadioChecked
{
	background-position: -24px -60px;
}

.rbTextButton
{
	padding: 0;
}

/* Split Button Styles */
.rbSplitRight,
.rbSplitLeft
{
	display: block;
	position: absolute;
	width: 22px;
	height: 100%;
	top: 0;
}

.rbSplitRight
{
	right: 0;
	background-position: -48px -135px;
}

.rbSplitRight:hover
{
	width: 25px;
	background-position: -45px -135px;
}

.rbSplitLeft
{
	width: 19px;
	background-position: -29px -157px;
}

.rbSplitLeft:hover
{
	width: 23px;
	background-position: -29px -157px;
}

/* Disabled states */
.rbDisabled
{
	
}

/* Normal buttons disabled */
a.rbDisabled:hover,
a.rbDisabled:focus, 
a.rbDisabled:active
{
	background-position: right 0;
}

a.rbDisabled:hover .rbDecorated,
a.rbDisabled:focus .rbDecorated, 
a.rbDisabled:active .rbDecorated
{
	background-position: left -22px;
}

/* Toggle checkboxes disabled */
.rbDisabled .rbToggleCheckbox:hover,
.rbDisabled:hover .rbToggleCheckbox
{
	background-position: -4px -4px;
}

.rbDisabled .rbToggleCheckboxChecked:hover,
.rbDisabled:hover .rbToggleCheckboxChecked
{
	background-position: -4px -424px;
}

*html a.rbDisabled,
*html a.rbDisabled:hover 
{
	border: 0;
	background-color: transparent;
}

/* Toggle radio disabled */
.rbDisabled .rbToggleRadio:hover,
.rbDisabled:hover .rbToggleRadio
{
	background-position: -24px -4px;
}

.rbDisabled .rbToggleRadioChecked:hover,
.rbDisabled:hover .rbToggleRadioChecked
{
	background-position: -24px -424px;
}

/* Native Button */
/* should use !important to overrdie skin specific selecotr */
.rbNativeButton
{
	background-image: none !important;
	padding-right: 0 !important;
}

/* Hide element styles the !important is necessary to prevent overriding */
.rbHideElement
{
	display: none;
	width: 0 !important;
	height: 0 !important;
	overflow: hidden !important;
}

.RadButton .rbHiddenImages
{
	position:absolute;
	top:-9999px;
	visibility:hidden;
}

/* -web kit hacks */

@media screen and (-webkit-min-device-pixel-ratio:0) 
{
	.rbSkinnedButton
	{
		padding-right: 2px;
	}
	.rbPrimaryIcon
	{
		left: 5px;
	}

}

/* Predefined Embedded Icons */

.rbAdd,
.rbRemove,
.rbOk,
.rbCancel,
.rbUpload,
.rbDownload,
.rbPrevious,
.rbNext,
.rbOpen,
.rbAttach,
.rbSave,
.rbConfig,
.rbPrint,
.rbRefresh,
.rbSearch,
.rbHelp,
.rbCart,
.rbEdit,
.rbRSS,
.rbMail
{
	background-image:url('WebResource.axd?d=39gg9oXOjXYipxPixIjeVHDgN2hu0brz3r5jV7Prxj_NApTAhmkr_edrQ5gm_7hqwRfKMTtHSg08Z2ykk-tO7nEj6CMSttusdZLRNSHlECjogka56DWGGGqAXyUrJDwkWITZJegozdy1nIYV7khqaFg96LcqgLp5ijO7WvL7YBxiJW0wQvp3M0bdbQ2EV34vswYH2Q2&t=636237880220090295') !important;
}

/* 16x16 predefined icon positions */
.rbAdd
{
	background-position: 0 0 !important;
}

.rbRemove
{
	background-position: -24px 0 !important;
}

.rbOk
{
	background-position: -48px 0 !important;
}

.rbCancel
{
	background-position: -72px 0 !important;
}

.rbUpload
{
	background-position: -94px 0 !important;
}

.rbDownload
{
	background-position: -120px 0 !important;
}

.rbPrevious
{
	background-position: -144px 0 !important;
}

.rbNext
{
	background-position: -168px 0 !important;
}

.rbOpen
{
	background-position: -192px 0 !important;
}

.rbAttach
{
	background-position: -210px 0 !important;
}

.rbSave
{
	background-position: -232px 0 !important;
}

.rbConfig
{
	background-position: -256px 0 !important;
}

.rbPrint
{
	background-position: -280px 0 !important;
}

.rbRefresh
{
	background-position: -306px 0 !important;
}

.rbSearch
{
	background-position: -332px 0 !important;
}

.rbHelp
{
	background-position: -356px 0 !important;
}

.rbCart
{
	background-position: -380px 0 !important;
}

.rbEdit
{
	background-position: -406px 0 !important;
}

.rbRSS
{
	background-position: -432px 0 !important;
}

.rbMail
{
	background-position: -456px 0 !important;
}


/* -web kit hacks end */

/* -Opera hacks 
._Telerik_Opera105 { fix_opera } 
*/