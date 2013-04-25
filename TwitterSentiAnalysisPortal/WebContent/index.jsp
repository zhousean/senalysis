<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
 
<head profile="http://gmpg.org/xfn/11"> 
<title>Twitter Sentimental Analysis</title> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<!-- <link rel="stylesheet" href="demo.css" type="text/css" media="all" />  -->
<style type="text/css">

body { padding:50px 100px; font:13px/150% Verdana, Tahoma, sans-serif; }

/* tutorial */

input, textarea { 
	padding: 9px;
	border: solid 1px #E5E5E5;
	outline: 0;
	font: normal 13px/100% Verdana, Tahoma, sans-serif;
	width: 200px;
	background: #FFFFFF url('bg_form.png') left top repeat-x;
	background: -webkit-gradient(linear, left top, left 25, from(#FFFFFF), color-stop(4%, #EEEEEE), to(#FFFFFF));
	background: -moz-linear-gradient(top, #FFFFFF, #EEEEEE 1px, #FFFFFF 25px);
	box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px;
	-moz-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px;
	-webkit-box-shadow: rgba(0,0,0, 0.1) 0px 0px 8px;
	}

textarea { 
	width: 400px;
	max-width: 400px;
	height: 150px;
	line-height: 150%;
	}

input:hover, textarea:hover,
input:focus, textarea:focus { 
	border-color: #C9C9C9; 
	-webkit-box-shadow: rgba(0, 0, 0, 0.15) 0px 0px 8px;
	}

.form label { 
	margin-left: 10px; 
	color: #999999; 
	}

.submit input {
	width: auto;
	padding: 9px 15px;
	background: #617798;
	border: 0;
	font-size: 14px;
	color: #FFFFFF;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	}
	
.button input {
	width: auto;
	padding: 9px 15px;
	background: #617798;
	border: 0;
	font-size: 14px;
	color: #FFFFFF;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	}
	
</style>

<style type="text/css">
 #map_canvas {
 			
            margin: 2;
            width: 425px;
            left: 115px;
            height: 385px;
        }
</style>

<script src="http://maps.google.com/maps/api/js?v=3.5&amp;sensor=false"></script>
<script type="text/javascript">
var geocoder;
var map;
function initialize() {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(43.652527,  -79.381961);
	var myOptions = {
	  zoom: 12,
	  center: latlng,
	  mapTypeId: google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	
	var elementType = document.createElement("input");
    
    //Assign different attributes to the element.
    elementType.setAttribute("type", "text");
    elementType.setAttribute("value", "typical");
    elementType.setAttribute("name", "searchType");   
    elementType.setAttribute("id", "searchTypeID"); 
    elementType.setAttribute("style","display:none");
 	
    var frm = document.getElementById("inputs");
    frm.appendChild(elementType);  
}


function codeAddress() {
var address = document.getElementById("address").value;
geocoder.geocode( { 'address': address}, function(results, status) {
  if (status == google.maps.GeocoderStatus.OK) {
    map.setCenter(results[0].geometry.location);
    var marker = new google.maps.Marker({
        map: map,
        draggable: true,
        position: results[0].geometry.location
    });
    
    var lat = results[0].geometry.location.lat();
    var lng = results[0].geometry.location.lng();
    
    var elementLat = document.createElement("input");
    
    //Assign different attributes to the element.
    elementLat.setAttribute("type", "text");
    elementLat.setAttribute("value", lat);
    elementLat.setAttribute("name", "latitude");
 	elementLat.setAttribute("style","display:none");
 	
	 var elementLng = document.createElement("input");
    
    //Assign different attributes to the element.
    elementLng.setAttribute("type", "text");
    elementLng.setAttribute("value", lng);
    elementLng.setAttribute("name", "longtitude");
    elementLng.setAttribute("style","display:none");
    
    var frm = document.getElementById("inputs");
 
    //Append the element in page (in span).
    frm.appendChild(elementLat);
    frm.appendChild(elementLng);
    
  } else {
    alert("Geocode was not successful for the following reason: " + status);
  }
});
	
	
}

function DisplayAdvanceOptions()
{
	if(document.getElementById("advanceOptions").style.display=='none'){
		document.getElementById("advanceOptions").style.display='inline';
		document.getElementById("switch").value="Typical";
	}
	else if(document.getElementById("advanceOptions").style.display=='inline'){
		document.getElementById("advanceOptions").style.display='none';
		document.getElementById("switch").value="Advanced";
	}
	
	if(document.getElementById("mapOptions").style.display=='none'){
		document.getElementById("mapOptions").style.display='inline';
	}
	else if(document.getElementById("mapOptions").style.display=='inline'){
		document.getElementById("mapOptions").style.display='none';
	}
	
	if(document.getElementById("map_canvas").style.display=='none'){
		document.getElementById("map_canvas").style.display='block';
	}
	else if(document.getElementById("map_canvas").style.display=='block'){
		document.getElementById("map_canvas").style.display='none';
	}
	
	if(document.getElementById("searchTypeID").getAttribute("value")== 'typical'){
		document.getElementById("searchTypeID").setAttribute("value", "advanced");		
	}
	else if(document.getElementById("searchTypeID").getAttribute("value")== 'advanced'){
		document.getElementById("searchTypeID").setAttribute("value", "typical");
	}
}

function validate()
{  
   var keyword =  document.getElementsByName("searchkey")[0].value;
   
   if (keyword.length <= 0)
   {
   		alert("key word(s) for search can't be empty !");
   		return false;
   }
  
   
   return true;
   	
}  
</script>
</head> 
<body onload="setTimeout('initialize()', 2000);">


<form id="inputs" class="form" method="post" action="TwitterSentiAnalysis.do" >
	<p class="name" >
		<label for="name"> <h1> Twitter Sentiment Analysis </h1></label>
	</p>

	<p class="email">
		<label for="email"><h3>Search a word and analyze the sentiment from the collected data on Twitter.</h3> </label>
	</p>

	<p class="web" >
		<input type="text" name="searchkey" id="keyword" />
	</p>
	
	<p class="submit" >
		<input type="submit" id="SearchButton" value="Search" onclick ="return validate(this.form)" />
		<input type="button" id="switch" value="Advanced" onclick="DisplayAdvanceOptions()"/>
	</p>
	
	<!--  <p class="lang">	-->
		<br>
		<table >
			<tr>
				<p class="web" >
					<table id="advanceOptions" style="display:none;"  >
						<tr>
							<td><label for="datetitle"><h4>Start Since  &nbsp;&nbsp;&nbsp;  </h4> </label> </td>
							<td>
								<select id="mySelectDate" name="startdate" size="1" >
									<option selected  value="1"> One Week Ago</option>
									<option value="2">Two Weeks Ago</option>
									<option value="3">Three Weeks Ago</option>
									<option value="4">One month Ago</option>									
								</select>
							</td>
							
						</tr>
						<tr>
							<td> <label for="lang"> <h4> Language &nbsp;&nbsp;&nbsp;  </h4></label> </td>
							<td>
								<select id="mySelect" name="lang" size="1" >
									<option selected>English</option>
									<option>France</option>
								</select>
							</td>
						</tr>
						<tr>
							<td> <label for="maxtweet"> <h4> Max. Tweets   &nbsp;&nbsp;&nbsp;  </h4></label> </td>
							<td>
								<select id="mySelectMax" name="selectMax" size="1" >
										<option selected value="15"> 15 </option>
										<option value="50">50</option>
										<option value="100">100</option>																	
								</select>
							</td>
						</tr>
					</table>
					<br>
					<table id="mapOptions" style="display:none;">
						<!--  <tr>	
							<td>
							<div id="map_canvas" width="425" height="325" frameborder="2" scrolling="no" marginheight="0" marginwidth="0" src="http://maps.google.com/maps?client=Chrome&q=toronto&oe=UTF-8&ie=UTF8&hq=&hnear=Toronto&z=12&output=embed&iwloc=""></div>
									 <small><a href="http://maps.google.com/maps?client=Chrome&q=toronto+on&oe=UTF-8&ie=UTF8&hq=&hnear=Toronto,+Ontario&z=14&source=embed" style="color:#0000FF;text-align:left">View Larger Map</a></small>
															<br>
								<div id="map_canvas"></div>
							</td>
						</tr>-->
						
						<div id="map_canvas" style="display:none;"></div>    
      						
      					
						<tr>
							<td> <label for="Location"> <h4> Location   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </h4></label> </td>
							<td>
								<input id="address" type="textbox" value="toronto, ontario">
							</td>
							<td>
							<p class="submit" >
								<input type="button" value="Geocode" onclick="codeAddress()">
							</p>								
							</td>
						</tr>
      										   
      					
      					       						
      					<tr>
      						<td> <label for="radius"> <h4> Radius &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </h4></label> </td>
							<td>
								<select id="myRadius" name="radius" size="1" >
									<option selected value="15000"> Unlimited </option>
									<option value="50">50 KM</option>
									<option value="100">100 KM</option>
									<option	value="500">500 KM</option>
								</select>
							</td>
						
						</tr>
					</table>
				</p>
			</tr>
		</table>
	<!--  </p> -->
	
	
	<p class="subtitle" >
		<label for="subtitle"><h4>Copyright@2013, Twitter Sentimental Analysis Expert.</h4> </label>
	</p>

</form>


</body>
</html>