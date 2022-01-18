package g15.customerapi.Models.Response;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
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

