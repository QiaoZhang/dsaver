/*
 * hand out:
 * 1. how to find the first td which has "rowspan" attribute under a div with id = "client-1"
 *     $("#client-1>tr").find("td:first").attr("rowspan");
 * 2. how to display json data
 *     $("#resp").html(syntaxHighlight(json));
 */
$(document).ready(function(){
	$(":text").keyup(function(){
		textClearError();
		updateQuery($(this).attr("id"));
	});

	$("#get").click(function(){
		getClients();
	});
	
	$("#post").click(function(){
		postTask();		
	});

	getClients();//init
	setInterval(function() {
	  getClients();
	}, 1000 * 20); // where X is your every X minutes
});

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
	if(obj.paraLanguage != "" && obj.paraLanguage != null)
		query += "language:" + obj.paraLanguage + "+";
	if(obj.paraSize != "" && obj.paraSize != null)
		query += "size:" + obj.paraSize + "+";
	if(obj.paraForks != "" && obj.paraForks != null)
		query += "forks:" + obj.paraForks + "+";
	if(obj.paraStars != "" && obj.paraStars != null)
		query += "stars:" + obj.paraStars + "+";
	if(obj.paraUser != "" && obj.paraUser != null)
		query += "user:" + obj.paraUser + "+";
	if(query.substring(query.length - 1) == "+")
		query = query.substring(0,query.length - 1);
	return query;
}

/*
 * progress bar style:
 * success: task on-going
 * info: task closed
 * warning: task assigned and waiting
 * danger: haven't been updated for 20 mins
 */
function appendClients(json){
	if(json.success){
		for(var i=0;i<json.data.length;i++) {
			if(json.data[i].tasks.length==0){
				if($("#client-"+json.data[i].clientID).length==0){
					//div wrapper
					$("table").append('<tbody id="client-'+json.data[i].clientID+'"></tbody>');
					var content = '<tr><td rowspan="'+json.data[i].tasks.length+'">'
							+json.data[i].clientID+'</td><td rowspan="'+json.data[i].tasks.length+'">'
							+json.data[i].fingerPrint+'</td><td class="task-total">'
							+'</td><td class="task-success">'
							+'</td><td class="task-failed">'
							+'</td><td>'
							+'</td><td><div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '
							+0+'%;"><span class="sr-only">'
							+0+'%</span></div></div></td></tr>';
					$("#client-"+json.data[i].clientID).html(content);
				}
			}else if($("#client-"+json.data[i].clientID).length>0){
				//client already displayed, update tasks and insert new tasks
				var rowspan = $("#client-"+json.data[i].clientID+">tr").find("td:first").attr("rowspan")-1;
				for(var j=0;j<json.data[i].tasks.length;j++){
					var progress = (json.data[i].tasks[j].success+json.data[i].tasks[j].failed)*100/json.data[i].tasks[j].total;
					if(rowspan!=-1&&j>rowspan){
						//new tasks that has not been displayed
						var content = '<tr><td class="task-total">'
										+json.data[i].tasks[j].total+'</td><td class="task-success">'
										+json.data[i].tasks[j].success+'</td><td class="task-failed">'
										+json.data[i].tasks[j].failed+'</td><td>'
										+generateQuery(json.data[i].tasks[j])+'</td><td><div class="progress progress-striped active"><div class="progress-bar '
										+progressStyle(json.data[i].tasks[j].state)+'" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '
										+progress+'%;"><span class="sr-only">'
										+progress+'%</span></div></div></td></tr>';
						//update the rowspan
						$("#client-"+json.data[i].clientID).append(content);
					}else{
						//existing tasks
						$("#client-"+json.data[i].clientID+">tr").find("td.task-success").slice(j,j+1).text(json.data[i].tasks[j].success);
						$("#client-"+json.data[i].clientID+">tr").find("td.task-failed").slice(j,j+1).text(json.data[i].tasks[j].failed);
						$("#client-"+json.data[i].clientID+">tr").find("div.progress-bar").slice(j,j+1).attr("style","width: "+progress+"%;");
						$("#client-"+json.data[i].clientID+">tr").find("div.progress-bar").slice(j,j+1).attr("class","progress-bar "+progressStyle(json.data[i].tasks[j].state));//assigned
						$("#client-"+json.data[i].clientID+">tr").find("span.sr-only").slice(j,j+1).text(progress+"%");
					}					
				}
				$("#client-"+json.data[i].clientID+">tr").find("td[rowspan]").attr("rowspan",json.data[i].tasks.length);	
			}else{
				//client is not displayed yet, append html code
				var progress = (json.data[i].tasks[0].success+json.data[i].tasks[0].failed)*100/json.data[i].tasks[0].total;
				//div wrapper
				$("table").append('<tbody id="client-'+json.data[i].clientID+'"></tbody>');				
				var content = '<tr><td rowspan="'+json.data[i].tasks.length+'">'
							+json.data[i].clientID+'</td><td rowspan="'+json.data[i].tasks.length+'">'
							+json.data[i].fingerPrint+'</td><td class="task-total">'
							+json.data[i].tasks[0].total+'</td><td class="task-success">'
							+json.data[i].tasks[0].success+'</td><td class="task-failed">'
							+json.data[i].tasks[0].failed+'</td><td>'
							+generateQuery(json.data[i].tasks[0])+'</td><td><div class="progress progress-striped active"><div class="progress-bar '
							+progressStyle(json.data[i].tasks[0].state)+'" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '
							+progress+'%;"><span class="sr-only">'
							+progress+'%</span></div></div></td></tr>';
				for(var j=1;j<json.data[i].tasks.length;j++){
					progress = (json.data[i].tasks[j].success+json.data[i].tasks[j].failed)*100/json.data[i].tasks[j].total;
					content += '<tr><td class="task-total">'
								+json.data[i].tasks[j].total+'</td><td class="task-success">'
								+json.data[i].tasks[j].success+'</td><td class="task-failed">'
								+json.data[i].tasks[j].failed+'</td><td>'
								+generateQuery(json.data[i].tasks[j])+'</td><td><div class="progress progress-striped active"><div class="progress-bar '
								+progressStyle(json.data[i].tasks[j].state)+'" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '
								+progress+'%;"><span class="sr-only">'
								+progress+'%</span></div></div></td></tr>';
				}
				$("#client-"+json.data[i].clientID).html(content);			
			}
		}				
	}else
		$("#resp").html(syntaxHighlight(json));
}

function progressStyle(state){
	if(state == "error")
		return "progress-bar-danger";//haven't updated for 20 mins or error
	else if(state == "open")
		return "progress-bar-success";//on-going task
	else if(state == "closed")
		return "progress-bar-info";//finished task
	else
		return "progress-bar-warning";//assigned
}

/****** ajax handling *****/
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
	//gather data
	var postData = new Object();
	postData.clientID = parseInt($("#client_id").val());
	postData.total = parseInt($("#total").val());
	postData.paraLanguage = $("#language").val();
	postData.paraUser = $("#user").val();
	postData.paraSize = $("#size").val();
	postData.paraStars = $("#stars").val();
	postData.paraForks = $("#forks").val();
	//post task with ajax
	$.ajaxSetup ({cache:false});
	$.ajax ({
		url: 'TaskPoster',
		type: 'Post',
		data:  JSON.stringify(postData),
		dataType: 'json',
		success: function (json){
			$("#resp").html(syntaxHighlight(json));
			getClients();
		}
	});
}

function textHasError(id){
	$("#"+id).parent().addClass("has-error");
}

function textClearError(){
	$(":text").parent().removeClass("has-error");
}

//
function syntaxHighlight(json) {
	if (typeof json != 'string') {
        json = JSON.stringify(json, null, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}