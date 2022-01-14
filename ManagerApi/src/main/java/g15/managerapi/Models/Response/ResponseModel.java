package g15.managerapi.Models.Response;

public class ResponseModel {
    public boolean completed;
    public String message;

    public static ResponseModel fromOption(Option option){
        var response = new ResponseModel();
        response.completed = option.completed;
        response.message = option.message;
        return response;
    }
}

