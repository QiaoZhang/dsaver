/*
 * hand out:
 * 1. how to find the first td which has "rowspan" attribute under a div with id = "client-1"
 *     $("#client-1,tr").find("td:first").attr("rowspan");
 */
$(document).ready(function(){
	$(":text").keyup(function(){
		textClearError();
		updateQuery($(this).attr("id"));
	});

	$("#post").click(function(){
		//makeProgress();	
		//alert();
		$("#client-1,tr").find("div.progress-bar").slice(0,1).attr("style","width: 65%;");
	});
});

var w= 60;
function makeProgress(){
	$("#task-1-progress").attr("style","width: 65%;");
}

//globle task object
var taskObj = new Object();
//init
taskObj.paraLanguage = "";
taskObj.paraSize = "";
taskObj.paraUser = "";
taskObj.paraStars = "";
taskObj.paraForks = "";
var searchquery = "";
var baseurl = "https://api.github.com/search/repositories?";
function updateQuery(id){	
	/* grab new value*/
	if(id == "language"){
		taskObj.paraLanguage = $("#"+id).val();
	}else if(id == "size"){
		taskObj.paraSize = $("#"+id).val();
	}else if(id == "forks"){
		taskObj.paraForks = $("#"+id).val();
	}else if(id == "stars"){
		taskObj.paraStars = $("#"+id).val();
	}else if(id == "user"){
		taskObj.paraUser = $("#"+id).val();
	}
	/* generate url*/
	searchquery = generateQuery(taskObj);

	/* update url*/
	$("#taskquery").attr("href",baseurl + "q=" + searchquery);
	$("#taskquery").text(searchquery);
}

function generateQuery(obj){
	var query = "";
	if(obj.paraLanguage != "")
		query += "language:" + obj.paraLanguage + "+";
	if(obj.paraSize != "")
		query += "size:" + obj.paraSize + "+";
	if(obj.paraForks != "")
		query += "forks:" + obj.paraForks + "+";
	if(obj.paraStars != "")
		query += "stars:" + obj.paraStars + "+";
	if(obj.paraUser != "")
		query += "user:" + obj.paraUser + "+";
	if(query.substring(query.length - 1) == "+")
		query = query.substring(0,query.length - 1);
	return query;
}

function getClients (){
	$.ajaxSetup ({cache:false});
	$.ajax ({
		url: 'ClientInfo',
		type: 'Get',
		dataType: 'json',
		success: function (json){
			appendClients(json);
			
		}
	});
}

function appendClients(json){
	if(json.success){
		for(var i=0;i<json.data.length;i++) {
			if($("#client-"+json.data[i].clientID).length>0){
				//update the rowspan
				$("#client-"+json.data[i].clientID+",tr").find("td:first").attr("rowspan",json.data[i].clientID.length);
				//client already displayed, update tasks and insert new tasks
				for(var j=0;j<json.data[i].tasks.length;j++){
					if(j>$("#client-"+json.data[i].clientID+",tr").find("td:first").attr("rowspan")-1){
						//new tasks that has not been displayed
						var content = "<tr><td>"
										+json.data[i].tasks[j].total+"</td><td>"
										+json.data[i].tasks[j].success+"</td><td>"
										+json.data[i].tasks[j].failed+"</td><td>"
										+generateQuery(json.data[i].tasks[j])+"</td><td><div class=\"progress progress-striped active\"><div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: "
										+progress+"%;\"><span class=\"sr-only\">"
										+progress+"%</span></div></div></td></tr>";
						$("#client-"+json.data[i].clientID).append(content);
					}else{
						//existing tasks
						$("#client-1,tr").find("div.progress-bar").slice(j,j+1).attr("style","width: 65%;");
					}
				}
			}else{
				//client is not displayed yet, append html code
				var progress = (json.data[i].tasks[0].success+json.data[i].tasks[0].failed/json.data[i].tasks[0].total).toFixed(2);
				var content = "<div id=\"client-"+json.data[i].clientID+"\">";//div wrapper
				content += "<tr><td rowspan=\""+json.data[i].tasks.length+"\">"
							+json.data[i].clientID+"</td><td rowspan=\""+json.data[i].tasks.length+"\">"
							+json.data[i].fingerPrint+"</td><td>"
							+json.data[i].tasks[0].total+"</td><td>"
							+json.data[i].tasks[0].success+"</td><td>"
							+json.data[i].tasks[0].failed+"</td><td>"
							+generateQuery(json.data[i].tasks[0])+"</td><td><div class=\"progress progress-striped active\"><div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: "
							+progress+"%;\"><span class=\"sr-only\">"
							+progress+"%</span></div></div></td></tr>";
				for(var j=1;j<json.data[i].tasks.length;j++){
					progress = (json.data[i].tasks[j].success+json.data[i].tasks[j].failed/json.data[i].tasks[j].total).toFixed(2);
					content += "<tr><td>"
								+json.data[i].tasks[j].total+"</td><td>"
								+json.data[i].tasks[j].success+"</td><td>"
								+json.data[i].tasks[j].failed+"</td><td>"
								+generateQuery(json.data[i].tasks[j])+"</td><td><div class=\"progress progress-striped active\"><div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"60\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: "
								+progress+"%;\"><span class=\"sr-only\">"
								+progress+"%</span></div></div></td></tr>";
				}
				content += "</div>";
			}
		}				
	}else
		alert(json.error_msg);
}

function postTask (){
	var error = false;
	//search query is empty
	if(searchquery == ""){
		error = true;
		textHasError("language");
		textHasError("size");
		textHasError("forks");
		textHasError("stars");
		textHasError("user");		
	}
	//client id is empty
	if($("#client_id").val() == ""){
		error = true;
		textHasError("client_id");
	}
	//total task is empty
	if($("#total").val() == ""){
		error = true;
		textHasError("total");
	}
	if(error) return;
	//post task with ajax
	$.ajaxSetup ({cache:false});
	$.ajax ({
		url: 'TaskPoster',
		type: 'Post',
		data: {
			"clientID":$("#client_id").val(),
			"total":$("#total").val(),
			"paraLanguage":$("#language").val(),
			"paraUser":$("#user").val(),
			"paraSize":$("#size").val(),
			"paraStars":$("#stars").val(),
			"paraForks":$("#forks").val()
		},
		dataType: 'json',
		success: function (json){
			alert(json);
		}
	});
}

function textHasError(id){
	$("#"+id).parent().addClass("has-error");
}

function textClearError(){
	$(":text").parent().removeClass("has-error");
}
