//checks whether the quantity to add in the form is valid
function checkQty(form)
{
	var qtyToAdd = form.childNodes[1].value;
	var ok = true;
	if (qtyToAdd == 0)
	{
		alert("The quantity for the item you're trying to add is 0!");
		ok = false;
	}else if (qtyToAdd < 0)
	{
		alert("You're trying to add a negative quantity!");
		ok = false;
	}else if (isNaN(qtyToAdd))
	{
		alert("The quantity you specified is not a valid number!");
		ok = false;
	}
	return ok;
}

function checkUpdate()
{
	var q = document.getElementsByClassName("qtyInput");
	for(var i=0; i <= q.length; i++)
	{
		if (q[i].value < 0)
		{
			alert("The quantity for one of the items is negative!");
			return false;
		}else if (isNaN(q[i].value))
		{
			alert("The quantity you specified is not a valid number!");
			return false;
		}
	}
	return true;
}

function checkExpress()
{
	var q = document.getElementsByClassName("qtyInput");
	for(var i=0; i <= q.length; i++)
	{
		if (q[i].value <= 0)
		{
			alert("The quantity for one of the items is not positive!");
			return false;
		}else if (isNaN(q[i].value))
		{
			alert("The quantity you specified is not a valid number!");
			return false;
		}
	}
	return true;
}

function validateQty(qty)
{
	if(qty.value < 0)
	{
		alert("The quantity for one of the items is negative!");
	}
}

function clearSearchBox(box)
{
	box.value = "";
}

function addAnotherItem()
{	
	var br = document.createElement("br");
	
	var label1 = document.createElement("label");
	label1.innerHTML = "ItemNumber:";
	
	var element1 = document.createElement("input");
	element1.setAttribute("type", "text");
	element1.setAttribute("id", "itemNumber");
	element1.setAttribute("name", "itemNumber");
	
	var label2 = document.createElement("label");
	label2.innerHTML = "Quantity:";
	
	var element2 = document.createElement("input");
	element2.setAttribute("type", "text");
	element2.setAttribute("id", "itemQty");
	element2.setAttribute("name", "itemQty");
	element2.setAttribute("class", "qtyInput");
	
	var element3 = document.createElement("input");
	element3.setAttribute("type", "button");
	element3.setAttribute("id", "rmItem");
	element3.setAttribute("name", "rmItem");
	element3.setAttribute("onclick", "rmvItem(this);");
	element3.setAttribute("value", "Remove This Item");
	
	var form = document.getElementById("expressForm");
	var elements = form.childNodes;
	var addBtn = elements[elements.length - 1];
	var addBtnCpy = addBtn.cloneNode(true); 
	form.removeChild(addBtn);
	
	form.appendChild(br);
	form.appendChild(label1);
	form.appendChild(element1);
	form.appendChild(label2);
	form.appendChild(element2);
	form.appendChild(element3);
	form.appendChild(addBtnCpy);
}

function rmvItem(rmBtn)
{
	var form = document.getElementById("expressForm");
	var previousNode = rmBtn.previousSibling;
	var previousNode2 = previousNode.previousSibling;
	var previousNode3 = previousNode2.previousSibling;
	var previousNode4 = previousNode3.previousSibling;
	var previousNode5 = previousNode4.previousSibling;
	form.removeChild(rmBtn);
	form.removeChild(previousNode);
	form.removeChild(previousNode2);
	form.removeChild(previousNode3);
	form.removeChild(previousNode4);
	form.removeChild(previousNode5);
}
