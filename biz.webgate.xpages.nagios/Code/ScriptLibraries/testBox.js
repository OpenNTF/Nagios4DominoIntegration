dojo.require("dijit.Dialog");
var loadingDialog; //global dialog object

function displayWait() {
	txtContent = "Please wait..";
	txtContent = "<div style=\"text-align:center\"><img src=\"ajax-loader.gif\" alt=\"\" />&nbsp;&nbsp;&nbsp;<br/><br/>" + txtContent + "</div>";
	loadingDialog = new dijit.Dialog({title : "",content : txtContent});
	dojo.body().appendChild(loadingDialog.domNode);
	loadingDialog.titleBar.style.display = 'none';
	loadingDialog.startup();
	loadingDialog.show();
}

function hideWait() {
	loadingDialog.hide()
}