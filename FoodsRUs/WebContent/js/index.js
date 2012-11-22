//checks whether the quantity to add in the form is zero
function checkQty(form)
{
	var qtyToAdd = form.childNodes[1].value;
	var ok = true;
	if (qtyToAdd == 0)
	{
		alert("The quantity for the item you're trying to add is 0!");
		ok = false;
	}	
	return ok;
}

function clearSearchBox(box)
{
	box.value = "";
}