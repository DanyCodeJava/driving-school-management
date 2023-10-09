 export class UserEmailChecker{
     onEmailOk;
     showModalDialog;
     csrfValue;
     url;
    constructor({onEmailOk, showModalDialog,csrfValue,url}){
    console.log(onEmailOk, showModalDialog,csrfValue,url);
    this.onEmailOk = onEmailOk;
    this.showModalDialog = showModalDialog;
    this.csrfValue = csrfValue;
    this.url = url;
    }
    execute({event, userEmail, userId}){
      event?.preventDefault();
            const params= {id: userId, email: userEmail, _csrf: this.csrfValue};
            const _this = this;
            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded",
                url: this.url,
                data: params,
                dataType: "json"
            })
            .done(function(response){

                    if(response.status === "Ok"){
                       _this.onEmailOk();
                    }
                    else if(response.status === "Duplicated"){

                        _this.showModalDialog("Warning", "There is another user having the email: " + userEmail);
                    }else{
                        _this.showModalDialog("Error", "Unknown response from server!");
                    }
            }).fail(function(){
                        _this.showModalDialog("Error", "Could not connect to the server! ");
            });

            return false;
    }
 }
