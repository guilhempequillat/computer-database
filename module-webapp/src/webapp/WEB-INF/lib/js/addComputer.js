$('form').submit(function () {
    var name = $.trim($('#computerName').val());
    var introduced = $.trim($('#introduced').val());
    var discontinued = $.trim($('#discontinued').val());
    var companyId = $.trim($('#companyId').val());
    if (name  === '' || introduced  === '' || discontinued  === '' || companyId === '' || (new Date(introduced).getTime() > new Date(discontinued).getTime())) {
        if((new Date(introduced).getTime() > new Date(discontinued).getTime())){
        	alert('Introduced must be smaller than discontinued');
        }else{
        	alert('All fields are not completed');
        }
        return false;
    }
});