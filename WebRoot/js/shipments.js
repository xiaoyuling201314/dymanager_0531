function sel(all){
			var peakhours = document.getElementsByName("selectId");
			for(var i=0;i<peakhours.length;i++){
				peakhours[i].checked = all.checked;
			}
		}
function changeallsel(){
			if(getallselect()){
				document.getElementById("selectAll").checked=true;
			}else{
				document.getElementById("selectAll").checked=false;
			}
		}
function getallselect(){
			var peakhours = document.getElementsByName("selectId");
			var flag = true;
			for(var i=0;i<peakhours.length;i++){
				flag = flag&peakhours[i].checked;
			}
			
			return flag;
}